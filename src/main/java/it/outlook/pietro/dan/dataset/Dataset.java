/**
 * 
 */
package it.outlook.pietro.dan.dataset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * The {@code Dataset} class represents a dataset.
 * 
 * <p> A dataset is used to store and retrieve data in a JSON format.
 * 
 * @author Pietro Danieli
 *
 */
public class Dataset {
	/**
	 * The name of the dataset.
	 */
	private String name;
	
	/**
	 * The path where the dataset is saved.
	 */
	private String path;
	
	/**
	 * Stores the data
	 */
	private JSONObject data;
	
	/**
	 * Stores the representation of the dataset on disk.
	 */
	private File file;
	
	/**
	 * Creates a new {@code Dataset} object representing a dataset with name 
	 * {@code name} stored in path {@code path}.
	 * @param name the name of the dataset
	 * @param path the path where the dataset is saved
	 */
	public Dataset(String name, String path) {
		this.name = name;
		this.path = path;
		this.file = new File(this.path + "/" + this.name + ".json");
		this.data = null;
	}
	
	/**
	 * Checks if the dataset is already present on disk
	 * @return {@code true} if the dataset is present on disk, {@code false} otherwise
	 */
	public boolean isDetectedOnDisk() {
		return this.file.exists();
	}
	
	/**
	 * Returns the data from the dataset
	 * @return the dataset data
	 * @throws IOException if the attempt to read from disk fails
	 */
	public JSONObject getData() throws IOException {
		// Data is not loaded from disk yet
		if(this.data == null) {
			this.loadData();
		}
		
		return this.data;
	}
	
	/**
	 * Stores data to the dataset
	 * @param data the data to store
	 * @throws IOException if the attempt to store to disk fails
	 */
	public void setData(JSONObject data) throws IOException {
		this.data = data;
		this.file.createNewFile();
		try(PrintWriter printer = new PrintWriter(this.file)) {
			printer.print(data.toString());
		}	
	}
	
	/**
	 * Loads the data from disk
	 * @throws FileNotFoundException if the attempt to read from disk fails
	 */
	private void loadData() throws FileNotFoundException {
		try(Scanner input = new Scanner(this.file).useDelimiter("\\Z")) {
			this.data = new JSONObject(input.next());
		}
	}
	
	/**
	 * Returns the name of the dataset.
	 * @return the name of the dataset
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the path of the dataset.
	 * @return the path of the dataset
	 */
	public String getPath() {
		return path;
	}
}
