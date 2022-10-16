package it.outlook.pietro.dan.dynasty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import it.outlook.pietro.dan.familytree.FamilyTree;

public class DynastyTests {
	@Test
	public void getName_returns_correct_name() {
		Dynasty dynasty = new Dynasty("test");
		assertEquals(dynasty.getName(), "test");
	}
	
	@Test
	public void getName_returns_empty_string_when_empty_constructor() {
		Dynasty dynasty = new Dynasty();
		assertEquals(dynasty.getName(), "");
	}
	
	@Test
	public void addMember_with_DynastyMember_correctly_adds_member() {
		Dynasty dynasty = new Dynasty("test");
		DynastyMember member = new DynastyMember();
		dynasty.add(member);
		assertEquals(dynasty.getDynastyMembers().contains(member), true);
		assertEquals(dynasty.getMember(member.getName()), member);
	}
	
	@Test
	public void addMembers_with_collection_correctly_adds_members() {
		Dynasty dynasty = new Dynasty("test");
		List<DynastyMember> members = new ArrayList<DynastyMember>();
		DynastyMember member1 = new DynastyMember("Augusto");
		DynastyMember member2 = new DynastyMember("Cleopatra");
		members.add(member1);
		members.add(member2);
		dynasty.add(members);
		assertEquals(dynasty.getDynastyMembers().contains(member1), true);
		assertEquals(dynasty.getDynastyMembers().contains(member2), true);
		assertEquals(dynasty.getMember(member1.getName()), member1);
		assertEquals(dynasty.getMember(member2.getName()), member2);
	}
	
	@Test
	public void getDynastyMembers_collection_throws_exception_if_modified() {
		Dynasty dynasty = new Dynasty("test");
		DynastyMember member1 = new DynastyMember("Augusto");
		DynastyMember member2 = new DynastyMember("Cleopatra");
		dynasty.add(member1);
		dynasty.add(member2);
		
		assertThrows(UnsupportedOperationException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				Collection<DynastyMember> members = dynasty.getDynastyMembers();
				members.add(member2);
			}
		});
	}
	
	@Test
	public void getFamilyTree_returns_correct_family_tree() {
		Dynasty dynasty = new Dynasty("test");
		DynastyMember member1 = new DynastyMember("Augusto");
		DynastyMember member2 = new DynastyMember("Gaio Giulio Cesare");
		DynastyMember member3 = new DynastyMember("Gaio Ottavio");
		DynastyMember member4 = new DynastyMember("Azia Maggiore");
		member1.addFatherName("Gaio Giulio Cesare");
		member2.addChildName("Augusto");
		member1.addFatherName("Gaio Ottavio");
		member3.addChildName("Augusto");
		member1.addMotherName("Azia Maggiore");
		member4.addChildName("Augusto");
		member3.addSpouseName("Azia Maggiore");
		member4.addSpouseName("Gaio Ottavio");
		dynasty.add(member1);
		dynasty.add(member2);
		dynasty.add(member3);
		dynasty.add(member4);
		FamilyTree<DynastyMember> tree = dynasty.getFamilyTree();
		assertEquals(tree.getName(), dynasty.getName());
		assertEquals(tree.isAMember(member1), true);
		assertEquals(tree.isAMember(member2), true);
		assertEquals(tree.isAMember(member3), true);
		assertEquals(tree.isAMember(member4), true);
		assertEquals(tree.isInAParentChildRelationship(member2, member1), true);
		assertEquals(tree.isInAParentChildRelationship(member3, member1), true);
		assertEquals(tree.isInAParentChildRelationship(member4, member1), true);
		assertEquals(tree.isInASpouseRelationship(member3, member4), true);
	}
	
	@Test
	public void numberOfMembers_returns_correct_number_of_members() {
		Dynasty dynasty = new Dynasty("test");
		DynastyMember member1 = new DynastyMember("Augusto");
		DynastyMember member2 = new DynastyMember("Cleopatra");
		dynasty.add(member1);
		dynasty.add(member2);
		
		assertEquals(dynasty.numberOfMembers(), 2);
	}
	
	@Test
	public void fromJson_correctly_creates_Dynasty_from_JSONObject() {
		Dynasty dynasty = new Dynasty();
		JSONObject dynastyJson = new JSONObject();
		dynastyJson.put("name", "test");
		JSONArray jsonMembers = new JSONArray();
		DynastyMember member = new DynastyMember("Augusto");
		jsonMembers.put(member.toJson());
		dynastyJson.put("members", jsonMembers);
		dynasty.fromJson(dynastyJson);
		
		assertEquals(dynasty.getName(), "test");
		DynastyMember dynastyMember = dynasty.getDynastyMembers().iterator().next();
		assertEquals(dynastyMember.getName(), "Augusto");
	}
	
	@Test
	public void toJson_correctly_turns_Dynasty_into_JSONObject() {
		Dynasty dynasty = new Dynasty("test");
		dynasty.add(new DynastyMember("Augusto"));
		
		JSONObject dynastyJson = dynasty.toJson();
		
		assertEquals(dynastyJson.get("name"), "test");
		assertEquals(((JSONObject)(((JSONArray)dynastyJson.get("members")).get(0))).get("name"), "Augusto");
	}
}
