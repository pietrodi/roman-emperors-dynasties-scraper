package it.outlook.pietro.dan.retrieval;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ScraperTests {
	@Test
	public void scraping_for_page_title_returns_correct_title() {
		Scraper scraper = null;
		try {
			scraper = new Scraper();
		} catch (NoSupportedBrowserFoundException e) {
			e.printStackTrace();
		}
		
		String title = scraper.scrape("https://www.google.com", new Retriever<String>() {
			@Override
			public String retrieve(String url, WebDriver driver) {
				driver.get(url);
				return driver.getTitle();
			}

			@Override
			public List<String> findLinks(String data) {
				return null;
			}
		});
		
		assertEquals(title, "Google");
	}
	
	@Test
	public void scraping_for_page_title_and_following_links_returns_correct_titles() {
		Scraper scraper = null;
		try {
			scraper = new Scraper();
		} catch (NoSupportedBrowserFoundException e) {
			e.printStackTrace();
		}
		
		List<String> titles = scraper.scrapeAndFollowLinks("https://www.google.com", new Retriever<String>() {
			@Override
			public String retrieve(String url, WebDriver driver) {
				driver.get(url);
				return driver.getTitle();
			}

			@Override
			public List<String> findLinks(String data) {
				ArrayList<String> links = new ArrayList<>();
				links.add("https://www.bing.com");
				links.add("https://www.instagram.com");
				return links;
			}
		});
		
		assertEquals(titles.contains("Google"), true);
		assertEquals(titles.contains("Bing"), true);
		assertEquals(titles.contains("Instagram"), true);
		assertEquals(titles.size(), 3);
	}
}
