package it.outlook.pietro.dan.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import it.outlook.pietro.dan.dynasty.DynastyMember;
import it.outlook.pietro.dan.util.Search.StringRetriever;

public class SearchTests {
	@Test
	public void filtering_collection_of_strings_returns_correct_filtered_collection() {
		ArrayList<String> collection = new ArrayList<>(Arrays.asList(
																	"Ottaviano Augusto", 
																	"Cesare", 
																	"Claudia Augusta", 
																	"Augusto", 
																	"Traiano"
																	));
		
		ArrayList<String> filteredCollection = (ArrayList<String>) Search.containsString(collection, new StringRetriever<String>() {
			@Override
			public String retrieveString(String object) {
				return object;
			}
		}, "Aug");
		
		assertEquals(filteredCollection.get(0), "Ottaviano Augusto");
		assertEquals(filteredCollection.get(1), "Claudia Augusta");
		assertEquals(filteredCollection.get(2), "Augusto");
		assertEquals(filteredCollection.size(), 3);
	}
	
	@Test
	public void filtering_collection_of_DynastyMembers_returns_correct_filtered_collection() {
		ArrayList<DynastyMember> collection = new ArrayList<>(Arrays.asList(
																	new DynastyMember("Ottaviano Augusto"), 
																	new DynastyMember("Cesare"), 
																	new DynastyMember("Claudia Augusta"), 
																	new DynastyMember("Augusto"), 
																	new DynastyMember("Traiano")
																	));
		
		ArrayList<DynastyMember> filteredCollection = (ArrayList<DynastyMember>) Search.containsString(collection, new StringRetriever<DynastyMember>() {
			@Override
			public String retrieveString(DynastyMember object) {
				return object.getName();
			}
		}, "Aug");
		
		assertEquals(filteredCollection.get(0), collection.get(0));
		assertEquals(filteredCollection.get(1), collection.get(2));
		assertEquals(filteredCollection.get(2), collection.get(3));
		assertEquals(filteredCollection.size(), 3);
	}
}
