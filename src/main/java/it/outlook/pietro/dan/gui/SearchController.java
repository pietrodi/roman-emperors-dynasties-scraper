package it.outlook.pietro.dan.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import it.outlook.pietro.dan.dynasty.Dynasty;
import it.outlook.pietro.dan.dynasty.DynastyMember;
import it.outlook.pietro.dan.util.Search;

/**
 * The controller of the view {@code SearchView}. 
 * 
 * @author Pietro Danieli
 *
 */
public class SearchController {
	private static final String WIKIPEDIA_PAGE_URL = "https://it.wikipedia.org/wiki/";
	
	private SearchView view;
	private SharedModel model;
	
	public SearchController(SearchView searchView, SharedModel sharedModel) {
		this.view = searchView;
		this.model = sharedModel;
		
		// Set the action listeners for the view
		this.view.addSearchButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCardPanels();
			}
			
		});
	}
	
	/**
	 * Adds the card panels to the search view
	 */
	public void addCardPanels() {
		view.removeAllCards();
		Collection<Dynasty> dynasties = model.getRomanDynasties();
		
		int cardNumber = 0;
		for(Dynasty dynasty : dynasties) {
			for(DynastyMember member : Search.containsString(dynasty.getDynastyMembers(), new Search.StringRetriever<DynastyMember>() {
				@Override
				public String retrieveString(DynastyMember object) {
					return object.getName();
				}
				
			}, view.getSearchBarText())) {
				view.addCard(view.createCard(member.getName(), dynasty.getName(), new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URI(WIKIPEDIA_PAGE_URL + member.getName().replace(' ', '_')));
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						
					}
						
				}, new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						view.setVisible(false);
						view.dispose();
						
						GraphView graphView = new GraphView();
						model.setActiveDynasty(dynasty);
						model.setActiveDynastyMember(member);
						new GraphController(graphView, model);
						graphView.setVisible(true);
					}

					@Override
					public void mousePressed(MouseEvent e) {
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						
					}
					
				}, cardNumber));
				
				cardNumber++;
			}
		}
		
		
		view.setCardPanelSize(new Dimension(SearchView.CARD_WIDTH, SearchView.CARD_HEIGHT * cardNumber));
		
		view.repaint();
		view.revalidate();
	}
}
