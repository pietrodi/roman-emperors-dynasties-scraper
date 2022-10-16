package it.outlook.pietro.dan.gui;

import static guru.nidi.graphviz.model.Factory.mutNode;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

/**
 * The controller of view {@code GraphView}.
 * 
 * @author Pietro Danieli
 *
 */
public class GraphController {
	private static final String WIKIPEDIA_PAGE_URL = "https://it.wikipedia.org/wiki/";
	
	private GraphView view;
	private SharedModel model;
	private BufferedImage image;
	
	public GraphController(GraphView graphView, SharedModel sharedModel) {
		this.view = graphView;
		this.model = sharedModel;
		this.image = getActiveDynastyImage();
		
		view.displayImage(resizeImage(image, (int)(image.getWidth() * 0.8), (int)(image.getHeight() * 0.8)));
		
		// Set action listeners for the view
		this.view.addGoBackButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setVisible(false);
				view.dispose();
				
				SearchView searchView = new SearchView();
				SearchController searchController = new SearchController(searchView, sharedModel);
				searchController.addCardPanels();
				searchView.setVisible(true);
			}
		});
		
		this.view.addWebLinkMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(WIKIPEDIA_PAGE_URL + model.getActiveDynasty().getName().replace(' ', '_')));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
					view.showOpenWebpageFailPane();
				}
			}
		});
		
		this.view.addDownloadLabelMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ImageIO.write(image, "png", new File(System.getProperty("user.dir") + "/" + model.getActiveDynasty().getName() + ".png"));
					view.showImageDownloadSuccessPane();
				} catch (IOException e1) {
					e1.printStackTrace();
					view.showImageDownloadFailPane();
				}
			}
		});
	}
	
	/**
	 * Resizes the image {@code image} to the height {@code newHeight} and
	 * width {@code newWidth}.
	 * @param image the image to resize
	 * @param newWidth the new width of the image
	 * @param newHeight the new height of the image
	 * @return the resized image
	 */
	public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) { 
	    Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = resizedImage.createGraphics();
	    g2d.drawImage(scaledImage, 0, 0, null);
	    g2d.dispose();

	    return resizedImage;
	} 
	
	/**
	 * Returns the image of the active dynasty
	 * @return the image of the active dynasty
	 */
	public BufferedImage getActiveDynastyImage() {
		BufferedImage image = null;
		MutableGraph graph = model.getActiveDynasty().getFamilyTree().getGraph();
		graph.add(mutNode(model.getActiveDynastyMember().getName()).add(Style.FILLED,
																		Attributes.attrs(
																		Attributes.attr("fillcolor", "#00FF00"))));
		
		image = Graphviz.fromGraph(graph).render(Format.PNG).toImage();
		return image;
	}
}
