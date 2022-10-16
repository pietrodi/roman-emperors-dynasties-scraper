package it.outlook.pietro.dan.dynasty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class DynastyMemberTests {
	@Test
	public void empty_constructor_creates_blank_dynasty_member() {
		DynastyMember member = new DynastyMember();
		assertEquals(member.getName(), "");
		assertEquals(member.getChildrenNames().isEmpty(), true);
		assertEquals(member.getSpousesNames().isEmpty(), true);
		assertEquals(member.getFathersNames().isEmpty(), true);
		assertEquals(member.getMothersNames().isEmpty(), true);
		assertEquals(member.getNumberOfChildren(), 0);
		assertEquals(member.getNumberOfSpouses(), 0);
		assertEquals(member.getNumberOfFathers(), 0);
		assertEquals(member.getNumberOfMothers(), 0);
	}
	
	@Test
	public void name_constructor_creates_dynasty_member_with_correct_name() {
		DynastyMember member = new DynastyMember("Augusto");
		assertEquals(member.getName(), "Augusto");
		assertEquals(member.getChildrenNames().isEmpty(), true);
		assertEquals(member.getSpousesNames().isEmpty(), true);
		assertEquals(member.getFathersNames().isEmpty(), true);
		assertEquals(member.getMothersNames().isEmpty(), true);
		assertEquals(member.getNumberOfChildren(), 0);
		assertEquals(member.getNumberOfSpouses(), 0);
		assertEquals(member.getNumberOfFathers(), 0);
		assertEquals(member.getNumberOfMothers(), 0);
	}
	
	@Test
	public void getName_returns_correct_name() {
		DynastyMember member = new DynastyMember("Augusto");
		assertEquals(member.getName(), "Augusto");
	}
	
	
	@Test
	public void modifying_children_collection_throws_exception() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addChildName("Giulia Maggiore");
		assertThrows(UnsupportedOperationException.class, () -> {member.getChildrenNames().add("Paolo Rossi");});
		assertThrows(UnsupportedOperationException.class, () -> {member.getChildrenNames().remove("Giulia Maggiore");});
	}
	
	@Test
	public void modifying_mothers_collection_throws_exception() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addMotherName("Giulia Maggiore");
		assertThrows(UnsupportedOperationException.class, () -> {member.getMothersNames().add("Paolo Rossi");});
		assertThrows(UnsupportedOperationException.class, () -> {member.getMothersNames().remove("Giulia Maggiore");});
	}
	
	@Test
	public void modifying_fathers_collection_throws_exception() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addFatherName("Giulia Maggiore");
		assertThrows(UnsupportedOperationException.class, () -> {member.getFathersNames().add("Paolo Rossi");});
		assertThrows(UnsupportedOperationException.class, () -> {member.getFathersNames().remove("Giulia Maggiore");});
	}
	
	@Test
	public void modifying_spouses_collection_throws_exception() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addSpouseName("Giulia Maggiore");
		assertThrows(UnsupportedOperationException.class, () -> {member.getSpousesNames().add("Paolo Rossi");});
		assertThrows(UnsupportedOperationException.class, () -> {member.getSpousesNames().remove("Giulia Maggiore");});
	}
	
	@Test
	public void children_collection_stores_unique_names() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addChildName("Giulia Maggiore");
		member.addChildName("Giulia Minore");
		member.addChildName("Giulia Maggiore");
		member.addChildName("Giulia Maggiore");
		member.addChildName("Giulia Minore");
		assertEquals(member.getNumberOfChildren(), 2);
	}
	
	@Test
	public void mothers_collection_stores_unique_names() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addMotherName("Giulia Maggiore");
		member.addMotherName("Giulia Minore");
		member.addMotherName("Giulia Maggiore");
		member.addMotherName("Giulia Maggiore");
		member.addMotherName("Giulia Minore");
		assertEquals(member.getNumberOfMothers(), 2);
	}
	
	@Test
	public void fathers_collection_stores_unique_names() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addFatherName("Giulia Maggiore");
		member.addFatherName("Giulia Minore");
		member.addFatherName("Giulia Maggiore");
		member.addFatherName("Giulia Maggiore");
		member.addFatherName("Giulia Minore");
		assertEquals(member.getNumberOfFathers(), 2);
	}
	
	@Test
	public void spouses_collection_stores_unique_names() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addSpouseName("Giulia Maggiore");
		member.addSpouseName("Giulia Minore");
		member.addSpouseName("Giulia Maggiore");
		member.addSpouseName("Giulia Maggiore");
		member.addSpouseName("Giulia Minore");
		assertEquals(member.getNumberOfSpouses(), 2);
	}
	
	@Test
	public void getNumberOfChildren_returns_correct_number_of_children() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addChildName("Giulia Maggiore");
		member.addChildName("Giulia Minore");
		member.addChildName("Mario Rossi");
		member.addChildName("Giovanni Verdi");
		member.addChildName("Giulia Maggiore");
		assertEquals(member.getNumberOfChildren(), 4);
	}
	
	@Test
	public void getNumberOfFathers_returns_correct_number_of_fathers() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addFatherName("Giulia Maggiore");
		member.addFatherName("Giulia Minore");
		member.addFatherName("Mario Rossi");
		member.addFatherName("Giovanni Verdi");
		member.addFatherName("Giulia Maggiore");
		assertEquals(member.getNumberOfFathers(), 4);
	}
	
	@Test
	public void getNumberOfMothers_returns_correct_number_of_mothers() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addMotherName("Giulia Maggiore");
		member.addMotherName("Giulia Minore");
		member.addMotherName("Mario Rossi");
		member.addMotherName("Giovanni Verdi");
		member.addMotherName("Giulia Maggiore");
		assertEquals(member.getNumberOfMothers(), 4);
	}
	
	@Test
	public void getNumberOfSpouses_returns_correct_number_of_spouses() {
		DynastyMember member = new DynastyMember("Augusto");
		member.addSpouseName("Giulia Maggiore");
		member.addSpouseName("Giulia Minore");
		member.addSpouseName("Mario Rossi");
		member.addSpouseName("Giovanni Verdi");
		member.addSpouseName("Giulia Maggiore");
		assertEquals(member.getNumberOfSpouses(), 4);
	}
}
