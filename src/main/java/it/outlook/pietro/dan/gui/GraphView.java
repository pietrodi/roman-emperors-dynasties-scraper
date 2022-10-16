package it.outlook.pietro.dan.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The graph visualizer window.
 * 
 * @author Pietro Danieli
 *
 */
public class GraphView extends JFrame {
	
	// The components of the view
	private JPanel contentPane;
	private JLabel webLinkLabel;
	private JScrollPane scrollPane;
	private JButton goBackButton;
	private JLabel downloadLabel;

	/**
	 * Create the frame.
	 */
	public GraphView() {
		setTitle("RomaniScraper");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel toolbar = new JPanel();
		toolbar.setBounds(10, 0, 966, 45);
		contentPane.add(toolbar);
		toolbar.setLayout(null);
		
		goBackButton = new JButton("\u2B05\uFE0F");
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		webLinkLabel = new JLabel("\uD83C\uDF10");
		webLinkLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		webLinkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		webLinkLabel.setBounds(914, 15, 42, 23);
		
		toolbar.add(webLinkLabel);
		
		goBackButton.setBounds(10, 15, 101, 23);
		toolbar.add(goBackButton);
		
		downloadLabel = new JLabel("\u2B07\uFE0F");
		downloadLabel.setHorizontalAlignment(SwingConstants.CENTER);
		downloadLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		downloadLabel.setBounds(884, 15, 26, 23);
		toolbar.add(downloadLabel);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 966, 496);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		contentPane.add(scrollPane);
	}
	
	/**
	 * Adds a listener {@code listener} to the web icon
	 * @param listener the listener to add to the web icon
	 */
	public void addWebLinkMouseListener(MouseListener listener) {
		webLinkLabel.addMouseListener(listener);
	}
	
	/**
	 * Displays the image {@code image} in the view
	 * @param image the image to display
	 */
	public void displayImage(BufferedImage image) {
		SmoothImageLabel imageLabel = new SmoothImageLabel(new ImageIcon(image));
		imageLabel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		scrollPane.setViewportView(imageLabel);
	}
	
	/**
	 * Adds a listener {@code listener} to the back button
	 * @param listener the listener to add to the back button
	 */
	public void addGoBackButtonListener(ActionListener listener) {
		goBackButton.addActionListener(listener);
	}
	
	/**
	 * Adds a listener {@code listener} to the download icon
	 * @param listener the listener to add to the download icon
	 */
	public void addDownloadLabelMouseListener(MouseListener listener) {
		downloadLabel.addMouseListener(listener);
	}
	
	/**
	 * Shows a fail message for opening a web page
	 */
	public void showOpenWebpageFailPane() {
		JOptionPane.showMessageDialog(null, "Impossibile aprire la pagina web!");
	}
	
	/**
	 * Shows a fail message for downloading an image
	 */
	public void showImageDownloadFailPane() {
		JOptionPane.showMessageDialog(null, "Errore nello scaricamento dell'immagine.");
	}
	
	/**
	 * Shows a success message for downloading an image
	 */
	public void showImageDownloadSuccessPane() {
		JOptionPane.showMessageDialog(null, "Immagine scaricata correttamente!");
	}
}
