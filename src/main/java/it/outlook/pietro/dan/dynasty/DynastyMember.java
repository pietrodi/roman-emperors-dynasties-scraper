/**
 * 
 */
package it.outlook.pietro.dan.dynasty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import it.outlook.pietro.dan.util.Jsonable;

/**
 * The {@code DynastyMember} class represents a dynasty member.
 * 
 * A dynasty member can have multiple mothers, fathers and children.
 * 
 * @author Pietro Danieli
 *
 */
public class DynastyMember implements Jsonable {
	/**
	 * The name of the dynasty member.
	 */
	private String name;
	
	/**
	 * Stores the names of the mothers of the dynasty member
	 */
	private Set<String> mothersNames;
	
	/**
	 * Stores the names of the fathers of the dynasty member
	 */
	private Set<String> fathersNames;
	
	/**
	 * Stores the names of the spouses of the dynasty member
	 */
	private Set<String> spousesNames;
	
	/**
	 * Stores the names of the children of the dynasty member
	 */
	private Set<String> childrenNames;
	
	/**
	 * Creates a new {@code DynastyMember} object representing
	 * a dynasty member with name {@code name} and no 
	 * fathers, children and spouses.
	 * @param name the name of the dynasty member
	 */
	public DynastyMember(String name) {
		this.name = name;
		this.fathersNames = new HashSet<String>();
		this.mothersNames = new HashSet<String>();
		this.childrenNames = new HashSet<String>();
		this.spousesNames = new HashSet<String>();
	}
	
	/**
	 * Creates a new {@code DynastyMember} object representing
	 * a dynasty member with blank name and no 
	 * fathers, children and spouses.
	 */
	public DynastyMember() {
		this("");
	}
	
	/**
	 * Returns the name of the dynasty member
	 * @return the name of the dynasty member
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a view of the names of the mothers names of the dynasty member
	 * @return a view of the names of the mothers names of the dynasty member
	 */
	public Collection<String> getMothersNames() {
		return Collections.unmodifiableCollection(this.mothersNames);
	}
	
	/**
	 * Adds the name of a mother of the dynasty member
	 */
	public void addMotherName(String motherName) {
		this.mothersNames.add(motherName);
	}
	
	/**
	 * Returns a view of the names of the fathers names of the dynasty member
	 * @return a view of the names of the fathers names of the dynasty member
	 */
	public Collection<String> getFathersNames() {
		return Collections.unmodifiableCollection(this.fathersNames);
	}
	
	/**
	 * Adds the name of a father of the dynasty member
	 */
	public void addFatherName(String fatherName) {
		this.fathersNames.add(fatherName);
	}
	
	/**
	 * Returns a view of the names of the spouses names of the dynasty member
	 * @return a view of the names of the spouses names of the dynasty member
	 */
	public Collection<String> getSpousesNames() {
		return Collections.unmodifiableCollection(this.spousesNames);
	}
	
	/**
	 * Adds the name of a spouse of the dynasty member
	 */
	public void addSpouseName(String spouseName) {
		this.spousesNames.add(spouseName);
	}
	
	/**
	 * Returns a view of the names of the children names of the dynasty member
	 * @return a view of the names of the children names of the dynasty member
	 */
	public Collection<String> getChildrenNames() {
		return Collections.unmodifiableCollection(this.childrenNames);
	}
	
	/**
	 * Adds the name of a child of the dynasty member
	 */
	public void addChildName(String childName) {
		this.childrenNames.add(childName);
	}
	
	/**
	 * Returns the number of mothers of the dynasty member.
	 * @return the number of mothers of the dynasty member
	 */
	public int getNumberOfMothers() {
		return this.mothersNames.size();
	}
	
	/**
	 * Returns the number of fathers of the dynasty member.
	 * @return the number of fathers of the dynasty member
	 */
	public int getNumberOfFathers() {
		return this.fathersNames.size();
	}
	
	/**
	 * Returns the number of spouses of the dynasty member.
	 * @return the number of spouses of the dynasty member
	 */
	public int getNumberOfSpouses() {
		return this.spousesNames.size();
	}
	
	/**
	 * Returns the number of children of the dynasty member.
	 * @return the number of children of the dynasty member
	 */
	public int getNumberOfChildren() {
		return this.childrenNames.size();
	}


	@Override
	public JSONObject toJson() {
		JSONObject dynastyMemberObject = new JSONObject();
		dynastyMemberObject.put("name", this.name);
		
		JSONArray mothersNamesArray = new JSONArray();
		for(String motherName : this.mothersNames) {
			mothersNamesArray.put(motherName);
		}
		
		dynastyMemberObject.put("mothersNames", mothersNamesArray);
		
		JSONArray fathersNamesArray = new JSONArray();
		for(String fatherName : this.fathersNames) {
			mothersNamesArray.put(fatherName);
		}
		
		dynastyMemberObject.put("fathersNames", fathersNamesArray);
		
		JSONArray spousesNamesArray = new JSONArray();
		for(String spouseName : this.spousesNames) {
			spousesNamesArray.put(spouseName);
		}
		
		dynastyMemberObject.put("spousesNames", spousesNamesArray);
		
		JSONArray childrenNamesArray = new JSONArray();
		for(String childName : this.childrenNames) {
			childrenNamesArray.put(childName);
		}
		
		dynastyMemberObject.put("childrenNames", childrenNamesArray);
		
		return dynastyMemberObject;
	}

	@Override
	public void fromJson(JSONObject member) {
		this.name = (String) member.get("name");
		
		for(Object motherName : member.getJSONArray("mothersNames")) {
			this.mothersNames.add((String) motherName);
		}
		
		for(Object fatherName : member.getJSONArray("fathersNames")) {
			this.fathersNames.add((String) fatherName);
		}
		
		for(Object spouseName : member.getJSONArray("spousesNames")) {
			this.spousesNames.add((String) spouseName);
		}
		
		for(Object childName : member.getJSONArray("childrenNames")) {
			this.childrenNames.add((String) childName);
		}
	}
}
