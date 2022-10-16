package it.outlook.pietro.dan.familytree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class FamilyTreeTests {
	@Test
	public void getName_returns_correct_name() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		assertEquals(tree.getName(), "test");
	}
	
	@Test
	public void addMember_correctly_adds_member() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		tree.addMember("membro");
		tree.addMember("membro2");
		
		assertEquals(tree.isAMember("membro"), true);
		assertEquals(tree.isAMember("membro2"), true);
	}
	
	@Test
	public void addParentChildRelationship_correcly_adds_parent_child_relationship() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		tree.addMember("membro1");
		tree.addMember("membro2");
		tree.addParentChildRelationship("membro1", "membro2");
		assertEquals(tree.isInAParentChildRelationship("membro1", "membro2"), true);
		assertEquals(tree.isInASpouseRelationship("membro1", "membro2"), false);
	}
	
	@Test
	public void addParentChildRelationship_throws_exception_if_one_of_the_members_does_not_exist() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		tree.addMember("membro1");
		
		assertThrows(IllegalStateException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				tree.addParentChildRelationship("membro1", "membro2");
			}
		});
	}
	
	@Test
	public void addSpouseRelationship_correcly_adds_spouse_relationship() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		tree.addMember("membro1");
		tree.addMember("membro2");
		tree.addSpouseRelationship("membro1", "membro2");
		assertEquals(tree.isInAParentChildRelationship("membro1", "membro2"), false);
		assertEquals(tree.isInASpouseRelationship("membro1", "membro2"), true);
	}
	
	@Test
	public void addSpouseRelationship_throws_exception_if_one_of_the_members_does_not_exist() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		tree.addMember("membro1");
		
		assertThrows(IllegalStateException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				tree.addSpouseRelationship("membro1", "membro2");
			}
		});
	}
	
	@Test
	public void getGraph_returns_correct_graph() {
		FamilyTree<String> tree = new FamilyTree<String>("test");
		tree.addMember("membro1");
		tree.addMember("membro2");
		tree.addMember("membro3");
		tree.addSpouseRelationship("membro1", "membro2");
		tree.addParentChildRelationship("membro1", "membro3");
		tree.getGraph();
		/*TODO: Understanding how this graph library works is impossible:
		 * No kind of documentation whatsoever, I don't know how to test it!
		 * Graphically, it works.
		 */
		
	}
}
