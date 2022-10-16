package it.outlook.pietro.dan.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.outlook.pietro.dan.dataset.Dataset;
import it.outlook.pietro.dan.dynasty.Dynasty;
import it.outlook.pietro.dan.dynasty.DynastyMember;
import it.outlook.pietro.dan.dynasty.DynastyMemberRetriever;
import it.outlook.pietro.dan.retrieval.NoSupportedBrowserFoundException;
import it.outlook.pietro.dan.retrieval.Scraper;
import it.outlook.pietro.dan.util.RomanDynastiesNamesRetriever;
import it.outlook.pietro.dan.util.RomanDynastyEmperorNamesRetriever;

/**
 * The model shared by every controller.
 * 
 * The model manages a collection of roman dynasties and has
 * an active dynasty and an active dynasty member.
 * 
 * @author Pietro Danieli
 *
 */
public class SharedModel {
	public static final String DATASET_NAME = "romanDynasties";
	
	/**
	 * The dataset of the model
	 */
	private Dataset dataset;
	
	/**
	 * The scraper of the model
	 */
	private Scraper scraper;
	
	/**
	 * The dynasties of the model
	 */
	private List<Dynasty> romanDynasties;
	
	/**
	 * The current active dynasty
	 */
	private Dynasty activeDynasty;
	
	/**
	 * The current active dynasty member
	 */
	private DynastyMember activeDynastyMember;
	
	/**
	 * Creates a new empty {@code SharedModel} object using a standard
	 * name and path dataset.
	 */
	public SharedModel() {
		this.dataset = new Dataset(DATASET_NAME, System.getProperty("user.dir"));
		this.romanDynasties = new ArrayList<>();
		this.scraper = null;
		this.activeDynasty = null;
		this.activeDynastyMember = null;
	}
	
	/**
	 * Loads the dynasties to the model by downloading them
	 * with a scraper and saving it to disk in case the data 
	 * file is not detected
	 */
	public void loadRomanDynasties() {
		if(this.dataset.isDetectedOnDisk()) {
			this.romanDynasties = this.loadRomanDynastiesFromDataset();
		} else {
			this.romanDynasties = this.loadRomanDynastiesFromScraper();
			this.saveRomanDynasties(this.romanDynasties);
		}
	}
	
	/**
	 * Returns the collection of roman dynasties of the model
	 * @return the collection of roman dynasties of the model
	 */
	public Collection<Dynasty> getRomanDynasties() {
		return Collections.unmodifiableCollection(this.romanDynasties);
	}
	
	/**
	 * Returns the active dynasty of the model
	 * @return the active dynasty of the model
	 */
	public Dynasty getActiveDynasty() {
		return this.activeDynasty;
	}
	
	/**
	 * Returns the active dynasty member of the model
	 * @return the active dynasty member of the model
	 */
	public DynastyMember getActiveDynastyMember() {
		return this.activeDynastyMember;
	}
	
	/**
	 * Sets the active dynasty of the model to {@code activeDynasty}
	 * @param activeDynasty the dynasty to set as active
	 */
	public void setActiveDynasty(Dynasty activeDynasty) {
		this.activeDynasty = activeDynasty;
	}
	
	/**
	 * Sets the active dynasty member of the model to {@code activeDynasty}
	 * @param activeDynastyMember
	 */
	public void setActiveDynastyMember(DynastyMember activeDynastyMember) {
		this.activeDynastyMember = activeDynastyMember;
	}
	
	/**
	 * Checks if the dataset of the model detects a dataset file on disk
	 * @return {@code true} if the dataset of the model detects a dataset file on disk,
	 * {@code false} otherwise
	 */
	public boolean isDatasetDetected() {
		return this.dataset.isDetectedOnDisk();
	}
	
	/**
	 * Returns the Roman dynasties by loading them from the dataset
	 */
	private List<Dynasty> loadRomanDynastiesFromDataset() {
		ArrayList<Dynasty> romanDynasties = new ArrayList<>();
		
		JSONArray jsonDynastiesArray = new JSONArray();
		try {
			jsonDynastiesArray = this.dataset.getData().getJSONArray(DATASET_NAME);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		for(Object jsonDynasty : jsonDynastiesArray) {
			Dynasty dynasty = new Dynasty();
			dynasty.fromJson((JSONObject) jsonDynasty);
			romanDynasties.add(dynasty);
		}
		
		return romanDynasties;
	}
	
	/**
	 * Returns the Roman dynasties by loading them from the scraper
	 */
	private List<Dynasty> loadRomanDynastiesFromScraper() {
		ArrayList<Dynasty> romanDynasties = new ArrayList<>();
		
		try {
			scraper = new Scraper();
		} catch (NoSupportedBrowserFoundException e) {
			e.printStackTrace();
		}
		
		List<String> dynastiesNames = scraper.scrape(RomanDynastiesNamesRetriever.WIKIPEDIA_DYNASTY_URL,
				new RomanDynastiesNamesRetriever());
		
		for(String dynastyName : dynastiesNames) {
			List<String> emperors = scraper.scrape(RomanDynastyEmperorNamesRetriever.WIKIPEDIA_EMPERORS_URL,
							new RomanDynastyEmperorNamesRetriever(dynastyName));
			Dynasty dynasty = new Dynasty(dynastyName);
			dynasty.add(scraper.scrapeAndFollowLinks(DynastyMemberRetriever.WIKIPEDIA_URL
								+ emperors.get(0).replace(" ", "_"), new DynastyMemberRetriever()));
			
			romanDynasties.add(dynasty);
		}
		
		return romanDynasties;
	}
	
	/**
	 * Saves Roman dynasties to disk
	 */
	private void saveRomanDynasties(List<Dynasty> romanDynasties) {
		JSONArray romanDynastiesJSONArray = new JSONArray();
		
		for(Dynasty dynasty : romanDynasties) {
			romanDynastiesJSONArray.put(dynasty.toJson());
		}
		
		JSONObject romanDynastiesJSON = new JSONObject();
		romanDynastiesJSON.put("romanDynasties", romanDynastiesJSONArray);
		try {
			this.dataset.setData(romanDynastiesJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}







