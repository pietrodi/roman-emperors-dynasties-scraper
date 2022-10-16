/**
 * 
 */
package it.outlook.pietro.dan.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import it.outlook.pietro.dan.retrieval.Retriever;

/**
 * The {@code RomanDynastiesNamesRetriever} class represents an action-oriented bot that
 * retrieves the names of Roman dynasties from a Wikipedia page.
 * 
 * @author Pietro Danieli
 *
 */
public class RomanDynastiesNamesRetriever implements Retriever<List<String>> {
	/**
	 * The url of the Wikipedia page containing Roman dynasties information.
	 */
	public static final String WIKIPEDIA_DYNASTY_URL = "https://it.wikipedia.org/wiki/Imperatori_romani";
	
	/**
	 * Keywords to look for in the web page.
	 */
	public static final String[] GOOD_KEYWORDS = {"Dinastia", "adottivi"};
	
	/**
	 * Keywords that invalidate a find in the webpage because part of Western Roman Empire.
	 */
	public static final String[] BAD_KEYWORDS = {"teodosiana", "valentiniana"};
	
	/**
	 * Retrieves the list of Roman dynasties names. Each dynasty name is identified
	 * as it is a "mw-headline" title containing some keywords.
	 * @param url the url from which to retrieve the dynasties names from
	 * @param driver the driver used to retrieve the dynasties names
	 * @return the list of Roman dynasties names
	 */
	@Override
	public List<String> retrieve(String url, WebDriver driver) {
		List<String> romanDynastiesNames = new ArrayList<>();
		
		driver.get(url);
		List<WebElement> headlines = driver.findElements(By.className("mw-headline"));
		for(WebElement headline : headlines) {
			List<WebElement> anchors = headline.findElements(By.tagName("a"));
			if(anchors.size() > 0) {
				String dynastyName = anchors.get(0).getText();
				boolean isValidDynasty = false;
				for(String keyword : RomanDynastiesNamesRetriever.GOOD_KEYWORDS) {
					if(dynastyName.contains(keyword)) {
						isValidDynasty = true;
					}
				}
				
				for(String keyword : RomanDynastiesNamesRetriever.BAD_KEYWORDS) {
					if(dynastyName.contains(keyword)) {
						isValidDynasty = false;
					}
				}
				
				if(isValidDynasty) {
						romanDynastiesNames.add(dynastyName);
					}
				}
			}
		
		return romanDynastiesNames;
	}
	
	/**
	 * Returns no links.
	 */
	@Override
	public List<String> findLinks(List<String> data) {
		return new ArrayList<>();
	}

}
