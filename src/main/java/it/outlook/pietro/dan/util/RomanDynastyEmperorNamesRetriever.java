/**
 * 
 */
package it.outlook.pietro.dan.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import it.outlook.pietro.dan.retrieval.Retriever;

/**
 * The {@code RomanDynastyEmperorNamesRetriever} class represents an action-oriented bot that
 * retrieves the names of the emperors of a specific dynasty from a Wikipedia page.
 * 
 * @author Pietro Danieli
 *
 */
public class RomanDynastyEmperorNamesRetriever implements Retriever<List<String>> {
	/**
	 * The url of the Wikipedia page containing Roman emperors names information.
	 */
	public static final String WIKIPEDIA_EMPERORS_URL = "https://it.wikipedia.org/wiki/Imperatori_romani";
	
	/**
	 * The name of the dynasty of the emperors to retrieve
	 */
	private String dynastyName;
	
	/**
	 * Creates a new {@code RomanDynastyEmperorNamesRetriever} that retrieves
	 * the names of the emperors of the {@code dynastyName} dynasty
	 * @param the name of dynasty of the emperors to retrieve
	 */
	public RomanDynastyEmperorNamesRetriever(String dynastyName) {
		this.dynastyName = dynastyName;
	}
	
	/**
	 * Retrieves the list of Roman emperor names of a particular dynasty. Each emperor name 
	 * is identified as it is contained in a table under a "mw-headline" title 
	 * containing the name of the dynasty.
	 * @param url the url from which to retrieve the Roman emperor names from
	 * @param driver the driver used to retrieve the Roman emperor names
	 * @return the list of Roman emeperor names
	 */
	@Override
	public List<String> retrieve(String url, WebDriver driver) {
		ArrayList<String> emperorNames = new ArrayList<String>();
		
		driver.get(url);
		List<WebElement> headlines = driver.findElements(By.className("mw-headline"));
		for(WebElement headline : headlines) {
			List<WebElement> anchors = headline.findElements(By.tagName("a"));
			if(anchors.size() > 0) {
				String dynastyName = anchors.get(0).getText();
				
				if(dynastyName.equals(this.dynastyName)) {
					List<WebElement> tables = driver
							.findElements(RelativeLocator.with(By.className("wikitable")).below(headline));
					if(tables.size() > 0) {
						List<WebElement> emperorRows = tables
							.get(0)
							.findElement(By.tagName("tbody"))
							.findElements(By.tagName("tr"));
						
						// The first two rows are not relevant
						for(int i = 2; i < emperorRows.size(); i++) {
							List<WebElement> emperorCells = emperorRows
									.get(i)
									.findElements(By.tagName("td"));
							
							if(emperorCells.size() > 1) {
								emperorNames.add(emperorCells
													.get(1)
													.findElement(By.tagName("b"))
													.findElement(By.tagName("a"))
													.getText());
							}
						}
					}
					
					break;
				}
			}
		}
		
		return emperorNames;
	}
	
	/**
	 * Returns no links.
	 */
	@Override
	public List<String> findLinks(List<String> data) {
		return new ArrayList<>();
	}

}
