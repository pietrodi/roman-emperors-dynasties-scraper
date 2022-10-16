package it.outlook.pietro.dan.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import it.outlook.pietro.dan.retrieval.NoSupportedBrowserFoundException;
import it.outlook.pietro.dan.retrieval.Scraper;

public class RomanDynastiesNamesRetrieverTests {
	@Test
	public void retrieving_returns_correct_names() {
		Scraper scraper = null;
		try {
			scraper = new Scraper();
		} catch (NoSupportedBrowserFoundException e) {
			e.printStackTrace();
		}
		
		List<String> names = scraper.scrape(RomanDynastiesNamesRetriever.WIKIPEDIA_DYNASTY_URL, new RomanDynastiesNamesRetriever());
		assertEquals(names.contains("Dinastia giulio-claudia"), true);
		assertEquals(names.contains("Dinastia dei Flavi"), true);
		assertEquals(names.contains("Imperatori adottivi"), true);
		assertEquals(names.contains("Dinastia dei Severi"), true);
		assertEquals(names.contains("Dinastia valeriana"), true);
		assertEquals(names.contains("Dinastia costantiniana"), true);
		assertEquals(names.size(), 6);
	}
}
