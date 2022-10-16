package it.outlook.pietro.dan.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller of the view {@code GraphView}. 
 * 
 * @author Pietro Danieli
 *
 */
public class LauncherController {
	
	private LauncherView view;
	private SharedModel model;
	
	public LauncherController(LauncherView launcherView, SharedModel sharedModel) {
		this.view = launcherView;
		this.model = sharedModel;
		
		// Set action listeners for the view
		this.view.addLaunchButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.setLaunchButtonToLoading();
				if(!model.isDatasetDetected()) {
					view.showDatabaseNotFoundPane();
				}
				
				view.repaint();
				view.revalidate();
				
				model.loadRomanDynasties();
				
				view.setVisible(false);
				view.dispose();
				
				SearchView searchView = new SearchView();
				SearchController searchController = new SearchController(searchView, model);
				searchController.addCardPanels();
				searchView.setVisible(true);
			}
			
		});
	}
	
}
