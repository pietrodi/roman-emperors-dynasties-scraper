package it.outlook.pietro.dan;
import it.outlook.pietro.dan.gui.LauncherController;
import it.outlook.pietro.dan.gui.LauncherView;
import it.outlook.pietro.dan.gui.SharedModel;

public class Main {
	public static void main(String[] args) {
		SharedModel model = new SharedModel();
		LauncherView launcherView = new LauncherView();
		new LauncherController(launcherView, model);
		launcherView.setVisible(true);
	}
}
