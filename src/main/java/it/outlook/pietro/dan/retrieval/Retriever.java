/**
 * 
 */
package it.outlook.pietro.dan.retrieval;

import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * A {@code Retriever} class represents an action-oriented bot that
 * retrieves data from a web page.
 * 
 * @author Pietro Danieli
 *
 */
public interface Retriever<T> {
	
	/**
	 * Retrieves data from the given web site using
	 * the given driver.
	 * @param url the web site url
	 * @param the driver used to retrieve data
	 * @return the data retrieved
	 */
	public T retrieve(String url, WebDriver driver);
	
	/**
	 * Retrieves relevant links from data scraped from a web page.
	 * @param data the data scraped
	 * @return relevant links from the scraped data
	 */
	public List<String> findLinks(T data);
}
