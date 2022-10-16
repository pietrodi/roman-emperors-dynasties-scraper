package it.outlook.pietro.dan.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The class {@code Search} contains methods for performing string search operations.
 * 
 * @author Pietro Danieli
 *
 */
public final class Search {
	private Search() {}
	
	/**
	 * A {@code StringRetriever} can retrieve a string from an object of type T
	 * 
	 * @author Pietro Danieli
	 *
	 * @param <T>
	 */
	public interface StringRetriever<T> {
		public String retrieveString(T object);
	}
	
	/**
	 * Returns a collection of objects for which the associated string retrieved with a {@code StringRetriever}
	 * contains {@code stringToSearch} case-insensitively
	 * @param collection the collection of objects to search
	 * @param retriever the retriever of strings from the objects
	 * @param stringToSearch the string to search
	 * @return a collection of objects for which the associated string retrieved with a {@code StringRetriever}
	 * contains {@code stringToSearch}
	 */
	public static <T> Collection<T> containsString(Collection<T> collection, StringRetriever<T> retriever, String stringToSearch) {
		ArrayList<T> searchResult = new ArrayList<T>();
		for(T object : collection) {
			if(retriever.retrieveString(object).toLowerCase().contains(stringToSearch.toLowerCase())) {
				searchResult.add(object);
			}
		}
		
		return searchResult;
	}
}
