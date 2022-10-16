package it.outlook.pietro.dan.dynasty;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.outlook.pietro.dan.retrieval.NoSupportedBrowserFoundException;
import it.outlook.pietro.dan.retrieval.Scraper;

public class DynastyMemeberRetrieverTests {
	@Test
	public void scrape_scrapes_Augusto_page_correctly() {
		Scraper scraper = null;
		try {
			scraper = new Scraper();
		} catch (NoSupportedBrowserFoundException e) {
			e.printStackTrace();
		}
		
		DynastyMember member = scraper.scrape(DynastyMemberRetriever.WIKIPEDIA_URL + "Augusto", new DynastyMemberRetriever());
		
		assertEquals(member.getSpousesNames().contains("Clodia Pulcra"), true);
		assertEquals(member.getSpousesNames().contains("Scribonia"), true);
		assertEquals(member.getSpousesNames().contains("Livia Drusilla"), true);
		assertEquals(member.getNumberOfSpouses(), 3);
		
		assertEquals(member.getChildrenNames().contains("Giulia maggiore (figlia di Augusto)"), true);
		assertEquals(member.getChildrenNames().contains("Lucio Cesare"), true);
		assertEquals(member.getChildrenNames().contains("Gaio Cesare"), true);
		assertEquals(member.getChildrenNames().contains("Marco Vipsanio Agrippa Postumo"), true);
		assertEquals(member.getChildrenNames().contains("Tiberio"), true);
		assertEquals(member.getNumberOfChildren(), 5);
		
		assertEquals(member.getFathersNames().contains("Gaio Ottavio"), true);
		assertEquals(member.getFathersNames().contains("Gaio Giulio Cesare"), true);
		assertEquals(member.getNumberOfFathers(), 2);
		
		assertEquals(member.getMothersNames().contains("Azia maggiore"), true);
		assertEquals(member.getNumberOfMothers(), 1);
	}
}
