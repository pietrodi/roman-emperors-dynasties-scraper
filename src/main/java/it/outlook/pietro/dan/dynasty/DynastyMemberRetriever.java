/**
 * 
 */
package it.outlook.pietro.dan.dynasty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import it.outlook.pietro.dan.retrieval.Retriever;


/**
 * The {@code DynastyMemberRetriever} class represents an action-oriented bot that
 * retrieves information about a dynasty member from a Wikipedia page.
 * 
 * <p> The information is scraped directly from the synoptic table of a specific
 * member. A synoptic table contains named (e.g. for children) and unnamed (e.g. for the image)
 * rows that contain information on the member.
 * 
 * @author Pietro Danieli
 *
 */
public class DynastyMemberRetriever implements Retriever<DynastyMember> {
	/**
	 * the url of a Wikipedia page
	 */
	public static final String WIKIPEDIA_URL = "https://it.wikipedia.org/wiki/";
	
	/**
	 * Used for cleaning names
	 */
	public static final String DEAD_LINK = " (la pagina non esiste)";
	
	/**
	 * The name of the row containing the dynasty name in the synoptic table
	 */
	public static final String DYNASTY_ROW_NAME = "Dinastia";
	
	/**
	 * The possible names of the row containing spouse names in the synoptic table
	 */
	public static final String[] SPOUSE_ROW_NAMES = {"Coniuge", "Coniugi", "Consorte"};
	
	
	/**
	 * The name of the row containing children names in the synoptic table
	 */
	public static final String CHILDREN_ROW_NAME = "Figli";
	
	/**
	 * The name of the row containing fathers names in the synoptic table
	 */
	public static final String FATHERS_ROW_NAME = "Padre";
	
	/**
	 * The name of the row containing mothers names in the synoptic table
	 */
	public static final String MOTHERS_ROW_NAME = "Madre";
	
	/**
	 * Retrieves the names contained in the row named {@code rowName} of the synoptic table
	 * {@code synopticTable}.
	 * @param synopticTable the synoptic table from which to retrieve the names
	 * @param rowName the name of the row from which to retrieve the names
	 * @return the names contained in the row {@code rowName} of the synoptic table {@code synopticTable}
	 */
	private List<String> retrieveNamesFromCells(List<WebElement> cells) {
		class NameChecker {
			public static final String BAD_KEYWORD = "Imperatore romano";
			
			public boolean isValidName(String name) {
				boolean valid = false;
				
				for(int i = 0; i < name.length(); i++) {
					if(Character.isLetter(name.charAt(i))) {
						valid = true;
					}
				}
				
				for(int i = 0; i < name.length() && valid; i++) {
					if(name.charAt(i) == '[' || name.charAt(i) == ']'
							|| name.charAt(i) == '\'' || name.charAt(i) == '.') {
						valid = false;
					}
				}
				
				return valid && (!name.contains(BAD_KEYWORD));
			}
		};
		
		List<String> names = new ArrayList<String>();
		if(!cells.isEmpty()) {
			List<WebElement> namesAnchors = cells.get(0).findElements(By.xpath("./a"));
					
			for(WebElement nameAnchor : namesAnchors) {
				String name = nameAnchor.getAttribute("title");
						
				if(new NameChecker().isValidName(name)) {
					if(name.endsWith(DEAD_LINK)) {
						name = name.substring(0, name.length() - DEAD_LINK.length());
					}
							
					names.add(name);
				}
			}
		}
		
		return names;
	}
	
	/**
	 * Retrieves information about a dynasty member.
	 * @param url the url of the web page to retrieve the information from
	 * @param driver the driver used to retrieve the information
	 * @return a {@code DynastyMember} object containing retrieved information
	 */
	@Override
	public DynastyMember retrieve(String url, WebDriver driver) {
		driver.get(url);
		
		DynastyMember member = new DynastyMember(url.substring(url.lastIndexOf('/') + 1).replace('_', ' '));
			
		// Retrieving the name of the spouses
		for(int i = 0; i < 3; i++) {
			List<String> spousesNames = retrieveNamesFromCells(
					driver.findElements(By.xpath("//table[@class='sinottico']/tbody/tr[contains(., '" + SPOUSE_ROW_NAMES[i] + "')]/td")));
			
			if(!spousesNames.isEmpty()) {
				for(String spouseName : spousesNames) {
					member.addSpouseName(spouseName);
				}
				
				break;
			}
		}
		
		// Retrieving the name of the fathers
		List<String> fathersNames = retrieveNamesFromCells(
				driver.findElements(By.xpath("//table[@class='sinottico']/tbody/tr[contains(., '" + FATHERS_ROW_NAME + "')]/td")));
		for(String fatherName : fathersNames) {
			member.addFatherName(fatherName);
		}
				
		// Retrieving the name of the mothers
		List<String> mothersNames = retrieveNamesFromCells(
				driver.findElements(By.xpath("//table[@class='sinottico']/tbody/tr[contains(., '" + MOTHERS_ROW_NAME + "')]/td")));
		for(String motherName : mothersNames) {
			member.addMotherName(motherName);
		}
		
		// Retrieving the name of the children
		List<String> childrenNames = retrieveNamesFromCells(
				driver.findElements(By.xpath("//table[@class='sinottico']/tbody/tr[contains(., '" + CHILDREN_ROW_NAME + "')]/td")));
		for(String childName : childrenNames) {
			member.addChildName(childName);
		}
		
		return member;
	}
	
	/**
	 * Retrieves the links to the web page of the fathers, mothers, children, spouses of the 
	 * dynasty member from scraped data.
	 * @param data the scraped data
	 * @return the links to the web page of the fathers, mothers, children, spouses of the 
	 * dynasty member
	 */
	@Override
	public List<String> findLinks(DynastyMember data) {	
		ArrayList<String> links = new ArrayList<String>();
		
		for(String name : data.getChildrenNames()) {
			links.add(nameToUrl(name));
		}
		
		for(String name : data.getFathersNames()) {
			links.add(nameToUrl(name));
		}
		
		for(String name : data.getMothersNames()) {
			links.add(nameToUrl(name));
		}
		
		for(String name : data.getSpousesNames()) {
			links.add(nameToUrl(name));
		}
		
		return links;
	}
	
	/**
	 * @return the url of a wikipedia page based on name
	 */
	private String nameToUrl(String name) {
		return WIKIPEDIA_URL + name.replace(' ', '_');
	} 
}
