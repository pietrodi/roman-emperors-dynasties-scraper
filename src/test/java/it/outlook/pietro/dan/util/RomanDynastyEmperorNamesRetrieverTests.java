package it.outlook.pietro.dan.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import it.outlook.pietro.dan.retrieval.NoSupportedBrowserFoundException;
import it.outlook.pietro.dan.retrieval.Scraper;

public class RomanDynastyEmperorNamesRetrieverTests {
	@Test
	public void retrieving_severi_emperors_returns_correct_names() {
		Scraper scraper = null;
		try {
			scraper = new Scraper();
		} catch (NoSupportedBrowserFoundException e) {
			e.printStackTrace();
		}
		
		List<String> names = scraper.scrape(RomanDynastyEmperorNamesRetriever.WIKIPEDIA_EMPERORS_URL, new RomanDynastyEmperorNamesRetriever("Dinastia dei Severi"));
		assertEquals(names.get(0), "Settimio Severo");
		assertEquals(names.get(1), "Caracalla");
		assertEquals(names.get(2), "Geta");
		assertEquals(names.get(3), "Macrino");
		assertEquals(names.get(4), "Diadumeniano");
		assertEquals(names.get(5), "Eliogabalo");
		assertEquals(names.get(6), "Alessandro Severo");
	}
}
