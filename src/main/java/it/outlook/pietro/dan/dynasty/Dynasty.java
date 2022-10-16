package it.outlook.pietro.dan.dynasty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import it.outlook.pietro.dan.familytree.FamilyTree;
import it.outlook.pietro.dan.util.Jsonable;

/**
 * The {@code Dynasty} class represents a dynasty.
 * 
 * <p> A dynasty is a set of dynasty members. Each
 * dynasty member has a distinct name from every other.
 * 
 * @author Pietro Danieli
 *
 */
public class Dynasty implements Jsonable {
	/**
	 * The name of the dynasty.
	 */
	private String name;
	
	/**
	 * Stores the members of the dynasty.
	 */
	private Map<String, DynastyMember> members;
	
	/**
	 * Creates a new {@code Dynasty} object representing a dynasty with name {@code name}
	 * and no members.
	 * @param name the name of the dynasty
	 */
	public Dynasty(String name) {
		this.name = name;
		this.members = new HashMap<String, DynastyMember>();
	}
	
	/**
	 * Creates a new {@code Dynasty} object representing a dynasty with blank name
	 * and no members.
	 */
	public Dynasty() {
		this("");
	}
	
	/**
	 * Adds a member to the dynasty
	 * @param member the member to add
	 */
	public void add(DynastyMember member) {
		this.members.put(member.getName(), member);
	}
	
	/**
	 * Adds members to the dynasty
	 * @param members the members to add
	 */
	public void add(Collection<DynastyMember> members) {
		for(DynastyMember member : members) {
			this.add(member);
		}
	}
	
	/**
	 * Return the number of members of the dynasty
	 * @return the number of members of the dynasty
	 */
	public int numberOfMembers() {
		return this.members.size();
	}
	
	/**
	 * Returns the member of name {@code name}
	 * @param name the name of the dynasty member
	 * @return the member of name {@code name}
	 */
	public DynastyMember getMember(String name) {
		return members.get(name);
	}
	
	/**
	 * Returns a view of the dynasty members of the dynasty
	 * @return a view of the dynasty members of the dynasty
	 */
	public Collection<DynastyMember> getDynastyMembers() {
		return Collections.unmodifiableCollection(this.members.values());
	}
	
	/**
	 * Returns the name of the dynasty
	 * @return the name of the dynasty
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return the family tree of the dynasty
	 * @return the family tree of the dynasty
	 */
	public FamilyTree<DynastyMember> getFamilyTree() {
		FamilyTree<DynastyMember> tree = new FamilyTree<DynastyMember>(this.name);
		
		Map<String, DynastyMember> nameToMember = new HashMap<String, DynastyMember>();
		for(DynastyMember member : this.members.values()) {
			tree.addMember(member, member.getName());
			nameToMember.put(member.getName(), member);
		}
		
		
		for(DynastyMember member : this.members.values()) {
			for(String fatherName : member.getFathersNames()) {
				tree.addParentChildRelationship(nameToMember.get(fatherName), member);
			}
			
			for(String motherName : member.getMothersNames()) {
				tree.addParentChildRelationship(nameToMember.get(motherName), member);
			}
			
			for(String childName : member.getChildrenNames()) {
				tree.addParentChildRelationship(member, nameToMember.get(childName));
			}
			
			for(String spouseName : member.getSpousesNames()) {
				tree.addSpouseRelationship(nameToMember.get(spouseName), member);
			}
		}
		
		return tree;
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject dynastyObject = new JSONObject();
		dynastyObject.put("name", this.name);
		
		JSONArray membersArray = new JSONArray();
		for(DynastyMember member : this.members.values()) {
			membersArray.put(member.toJson());
		}
		
		dynastyObject.put("members", membersArray);
		
		return dynastyObject;
	}

	@Override
	public void fromJson(JSONObject jsonObject) {
		this.name = (String) jsonObject.get("name");
		JSONArray membersArray = jsonObject.getJSONArray("members");
		for(Object jsonMember : membersArray) {
			DynastyMember member = new DynastyMember();
			member.fromJson((JSONObject)jsonMember);
			this.add(member);
		}
	}
}
