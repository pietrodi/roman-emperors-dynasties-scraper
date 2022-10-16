/**
 * 
 */
package it.outlook.pietro.dan.util;

import org.json.JSONObject;

/**
 * A {@code Jsonable} object can have its attributes converted to a 
 * Json object and updated from a Json object.
 * 
 * @author Pietro Danieli
 *
 */
public interface Jsonable {
	
	/**
	 * Returns the JSON of the object attributes
	 * @return the JSON of the object attributes
	 */
	public JSONObject toJson();
	
	/**
	 * Updates the object to the JSON object attributes
	 */
	public void fromJson(JSONObject jsonObject);
}
