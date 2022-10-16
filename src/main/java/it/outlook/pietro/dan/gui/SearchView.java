package it.outlook.pietro.dan.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 * The seach window.
 * 
 * @author Pietro Danieli
 *
 */
public class SearchView extends JFrame {
	
	// The components of the window
	private JPanel contentPane;
	private JTextField searchBar;
	private JButton searchButton;
	private JPanel cardPanel;
	
	public static final int CARD_HEIGHT = 100;
	
	public static final int CARD_WIDTH = 852;

	/**
	 * Create the frame.
	 */
	public SearchView() {
		setTitle("RomaniScraper");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel toolbar = new JPanel();
		toolbar.setBounds(15, 5, 818, 45);
		contentPane.add(toolbar);
		toolbar.setLayout(null);
		
		JLabel titleLabel = new JLabel("RomaniScraper");
		titleLabel.setBounds(10, 11, 120, 23);
		toolbar.add(titleLabel);
		
		searchBar = new JTextField();
		searchBar.setBounds(472, 11, 276, 23);
		searchBar.setToolTipText("Cerca qui...");
		toolbar.add(searchBar);
		searchBar.setColumns(10);
		
		searchButton = new JButton("\uD83D\uDD0D");
		searchButton.setBounds(758, 11, 50, 23);
		toolbar.add(searchButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 61, 818, 376);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		
		cardPanel = new JPanel();
		cardPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setViewportView(cardPanel);
		cardPanel.setLayout(null);
		
	}
	
	/**
	 * Returns the text of the search bar
	 * @return the text of the search bar
	 */
	public String getSearchBarText() {
		return searchBar.getText();
	}
	
	/**
	 * Adds the action listener {@code listener} to the search button
	 * @param listener the listener to add to the search button
	 */
	public void addSearchButtonListener(ActionListener listener) {
		searchButton.addActionListener(listener);
	}
	
	/**
	 * Creates a clickable card graphic element for the {@code cardPanel} panel
	 * with a title {@code title}, a subtitle {@code subtitle} and a web icon button that has a mouse 
	 * listener {@code clickOnWebLinkListener}. The card itself has a mouse listener
	 * {@code clickOnCardListener} and is the i-th card in the card panel.
	 * @param title the title of the card
	 * @param subtitle the subtitle of the card
	 * @param clickOnWebLinkListener the listener for the web icon
	 * @param clickOnCardListener the listener for the card
	 * @param i the position of the card in the card panel
	 * @return a clickable card graphic element
	 */
	public JPanel createCard(String title, String subtitle, MouseListener clickOnWebLinkListener, MouseListener clickOnCardListener, int i) {
		JPanel card = new JPanel();
		card.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		card.setBounds(0, CARD_HEIGHT * i, CARD_WIDTH, CARD_HEIGHT);
		card.setLayout(null);
		card.addMouseListener(clickOnCardListener);
		
		JLabel nameLabel = new JLabel(title);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		nameLabel.setBounds(25, 0, 783, 60);
		card.add(nameLabel);
		
		JLabel dynastyLabel = new JLabel(subtitle);
		dynastyLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dynastyLabel.setBounds(25, 49, 730, 40);
		card.add(dynastyLabel);
		
		JLabel webLinkLabel = new JLabel("\uD83C\uDF10");
		webLinkLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		webLinkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		webLinkLabel.setBounds(736, 47, 54, 40);
		card.add(webLinkLabel);
		webLinkLabel.addMouseListener(clickOnWebLinkListener);
		
		return card;
	}
	
	/**
	 * Adds a card to the card panel
	 * @param card the card to add
	 */
	public void addCard(JPanel card) {
		cardPanel.add(card);
	}
	
	/**
	 * Sets the card panel size to the specified dimension
	 */
	public void setCardPanelSize(Dimension dimension) {
		cardPanel.setPreferredSize(dimension);
	}
	
	/**
	 * Removes all cards from the card panel
	 */
	public void removeAllCards() {
		cardPanel.removeAll();
	}
}
