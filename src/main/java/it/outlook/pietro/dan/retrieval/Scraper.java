/**
 * 
 */
package it.outlook.pietro.dan.retrieval;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

/**
 * The {@code Scraper} class drives the scraping process of {@code Retriever}
 * objects.
 * 
 * @author Pietro Danieli
 *
 */
public class Scraper {
	/**
	 * The scraper driver options. 
	 */
	private AbstractDriverOptions<?> options;
	
	/**
	 * Browsers that can be used for scraping.
	 */
	private static final DriverManagerType[] supportedBrowsers = {DriverManagerType.CHROME,
																  DriverManagerType.FIREFOX,
																  DriverManagerType.EDGE};
	
	/**
	 * The browser available for scraping.
	 */
	private DriverManagerType foundBrowser;
	
	/**
	 * Initializes a scraper with default driver options.
	 * @throws NoSupportedBrowserFoundException if no supported browser for scraping is found
	 */
	public Scraper() throws NoSupportedBrowserFoundException {
		this.foundBrowser = null;
		
		// Detect which browser is present
		for(DriverManagerType supportedBrowser : Scraper.supportedBrowsers) {
			Optional<Path> browserPath = WebDriverManager.getInstance(supportedBrowser).getBrowserPath();
			if(browserPath.isPresent()) {
				this.foundBrowser = supportedBrowser;
				break;
			}
		}
		
		if(this.foundBrowser == null) {
			throw new NoSupportedBrowserFoundException("No supported browser for scraping was found.");
		}
		
		// Setup found browser
		WebDriverManager.getInstance(this.foundBrowser).setup();
		
		switch(this.foundBrowser) {
			case CHROME:
				options = new ChromeOptions();
				((ChromeOptions) options).setHeadless(true);
				break;
			case FIREFOX:
				options = new FirefoxOptions();
				((FirefoxOptions) options).setHeadless(true);
				break;
			default:
				options = new EdgeOptions();
				((EdgeOptions) options).setHeadless(true);
				break;
		}
	}
	
	/**
	 * Returns a new web driver instance of the detected browser.
	 */
	private WebDriver createNewWebDriverInstance() {
		WebDriver driver;
		
		switch(this.foundBrowser) {
			case CHROME:
				driver = new ChromeDriver((ChromeOptions) options);
				break;
			case FIREFOX:
				driver = new FirefoxDriver((FirefoxOptions)options);
				break;
			default:
				driver = new EdgeDriver((EdgeOptions)options);
				break;
		}
		
		return driver;
	}
	
	/**
	 * Scrapes the web page at [@code url} using the {@code Retriever} object.
	 * @param retriever The {@code Retriever} that has to perform the scraping action
	 * @param url the url of the web page
	 * @return an object containing the result of the scraping
	 */
	public <T> T scrape(String url, Retriever<T> retriever) {
		T scrapedData = null;
		
		WebDriver driver = this.createNewWebDriverInstance();
		
		try {
			scrapedData = retriever.retrieve(url, driver);
		} finally {
			driver.quit();
		}
		
		return scrapedData;
	}
	
	/**
	 * Scrapes web pages starting at [@code url} and uses information from 
	 * the scraped web pages to find new pages to scrape using the {@code Retriever} object.
	 * @param retriever The {@code Retriever} that has to perform the scraping action and
	 * the finding of new links.
	 * @param url the url of the stating web page
	 * @return a list of objects containing the result of the scraping
	 */
	public <T> List<T> scrapeAndFollowLinks(String url, Retriever<T> retriever) {
		List<T> scrapedData = new ArrayList<T>();
		
		WebDriver driver = this.createNewWebDriverInstance();
		
		try {
			Set<String> alreadyVisited = new HashSet<String>();
			Queue<String> nextVisits = new LinkedList<String>();
			
			nextVisits.add(url);
			alreadyVisited.add(url);
			while(!nextVisits.isEmpty()) {
				String nextVisit = nextVisits.element();
				nextVisits.remove();
				T data = retriever.retrieve(nextVisit, driver);
				for(String link : retriever.findLinks(data)) {
					if(!alreadyVisited.contains(link)) {
						nextVisits.add(link);
						alreadyVisited.add(link);
					}
				}
				
				scrapedData.add(data);
				
			}
		} finally {
			driver.quit();
		}
		
		return scrapedData;
	}
}
