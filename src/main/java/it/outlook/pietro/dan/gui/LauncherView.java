package it.outlook.pietro.dan.gui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The launcher window.
 * 
 * @author Pietro Danieli
 *
 */
public class LauncherView extends JFrame {
	
	// The components of the window
	private JPanel contentPane;
	private JLabel mainTitle;
	JButton launchButton;

	/**
	 * Create the frame.
	 */
	public LauncherView() {
		setTitle("RomaniScraper");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		launchButton = new JButton("AVVIA");
		launchButton.setBounds(146, 182, 150, 30);
		contentPane.add(launchButton);
		
		mainTitle = new JLabel("RomaniScraper");
		mainTitle.setBounds(15, 15, 416, 155);
		mainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainTitle.setFont(new Font("Tahoma", Font.PLAIN, 36));
		contentPane.add(mainTitle);
	}
	
	/**
	 * Adds the action listener {@code listener} to the launch button
	 * @param listener the action listener
	 */
	public void addLaunchButtonListener(ActionListener listener) {
		this.launchButton.addActionListener(listener);
	}
	
	/**
	 * Sets the launcher button to a loading state
	 */
	public void setLaunchButtonToLoading() {
		this.disableLaunchButton();
		this.launchButton.setText("Caricamento...");
	}
	
	/**
	 * Disables the launch button.
	 */
	public void disableLaunchButton() {
		this.launchButton.setEnabled(false);
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Shows pane to signal that the dataset was not found.
	 */
	public void showDatabaseNotFoundPane() {
		JOptionPane.showMessageDialog(null, "Dataset non trovato! Si procederà al download...");
	}
}
