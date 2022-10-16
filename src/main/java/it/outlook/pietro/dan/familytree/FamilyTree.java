/**
 * 
 */
package it.outlook.pietro.dan.familytree;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static guru.nidi.graphviz.model.Factory.to;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.model.MutableGraph;

/**
 * The {@code FamilyTree} class represents a family tree of elements.
 * 
 * <p> A family tree is a collection of elements that are related by a 
 * parent-child or spouse relationship.
 * 
 * <p> Each element can be labeled, and the label is used to identify
 * the element in the graph representation of the family tree provided
 * by {@code getGraph} method.
 * 
 * @author Pietro Danieli
 *
 */
public class FamilyTree<E> {
	/**
	 * The {@code Relationship} class represents a family relationship 
	 * between two elements.
	 * 
	 * @author Pietro Danieli
	 *
	 */
	private static class Relationship<E> {
		/**
		 * Stores the subject of the relationship.
		 */
		private E subject;
		
		/**
		 * Stores the object of the relationship.
		 */
		private E object;
		
		/**
		 * Stores the type of relationship.
		 */
		private RelationshipType type;
		
		public static enum RelationshipType {
			PARENT_OF, SPOUSE_OF
		}
		
		/**
		 * Creates a new {@code Relationship} object representing a relationship
		 * of type {@code type} and form {@code subject} is the something of {@code object}.
		 * @param subject the subject of the relationship
		 * @param object the object of the relationship
		 * @param type the relationship type
		 */
		public Relationship(E subject, E object, RelationshipType type) {
			this.subject = subject;
			this.object = object;
			this.type = type;
		}
		
		/**
		 * Returns the subject of the relationship.
		 * @return the subject of the relationship
		 */
		public E getSubject() {
			return subject;
		}
		
		/**
		 * Returns the object of the relationship.
		 * @return the object of the relationship
		 */
		public E getObject() {
			return object;
		}
		
		/**
		 * Returns the type of the relationship.
		 * @return the type of the relationship
		 */
		public RelationshipType getType() {
			return type;
		}
	}
	
	/**
	 * The name of the family.
	 */
	private String name;
	
	/**
	 * Stores the labels of the family tree elements.
	 */
	private Map<E, String> labels;
	
	/**
	 * Stores the relationships of a family tree element.
	 */
	private Map<E, LinkedList<Relationship<E>>> relationships;
	
	/**
	 * Creates a new {@code FamilyTree} object representing an empty family tree with
	 * name {@code name}.
	 * @param name the name of the family tree
	 */
	public FamilyTree(String name) {
		this.name = name;
		this.relationships = new HashMap<E, LinkedList<Relationship<E>>>();
		this.labels = new HashMap<E, String>();
	}
	
	/**
	 * Adds a new unlabeled member to the family tree
	 * @param member the member to be added
	 */
	public void addMember(E member) {
		addMember(member, "");
	}
	
	/**
	 * Adds a new labeled member to the family tree.
	 * @param member the member to be added
	 * @param label the label of the member
	 */
	public void addMember(E member, String label) {
		relationships.putIfAbsent(member, new LinkedList<Relationship<E>>());
		labels.put(member, label);
	}
	
	/**
	 * Adds a new parent-child relationship to the family tree.
	 * @param parent the parent
	 * @param child the child
	 * @throws IllegalStateException if either parent or child are not in the family tree
	 */
	public void addParentChildRelationship(E parent, E child) {
		if(this.isAMember(parent) && this.isAMember(child)) {
			this.relationships.get(parent).add(new Relationship<E>(parent, child, Relationship.RelationshipType.PARENT_OF));
		} else {
			throw new IllegalStateException("Both parent and child should be member of the family tree!");
		}
	}
	
	/**
	 * Adds a new spouse relationship to the family tree.
	 * @throws IllegalStateException if either spouse are not in the family tree
	 */
	public void addSpouseRelationship(E spouse1, E spouse2) {
		if(this.isAMember(spouse1) && this.isAMember(spouse2)) {
			this.relationships.get(spouse1).add(new Relationship<E>(spouse1, spouse2, Relationship.RelationshipType.SPOUSE_OF));
		} else {
			throw new IllegalStateException("Both spouses should be member of the family tree!");
		}
	}
	
	/**
	 * Checks if {@code member} is a member of the family tree.
	 * @param member the member to check for
	 * @return {@code true} if {@code member} is a member of the family tree,
	 * {@code false} otherwise
	 */
	public boolean isAMember(E member) {
		return this.relationships.containsKey(member);
	}
	
	/**
	 * Checks if {@code member} is in a relationship with {@code otherMember}
	 * of type {@code type}
	 * @param type the type of relationship
	 * @return {@code true} if {@code member} is in a relationship with {@code otherMember}
	 * of type {@code type}, {@code false} otherwise
	 */
	private boolean isInARelationship(E member, E otherMember, Relationship.RelationshipType type) {
		for(Relationship<E> relationship : this.relationships.get(member)) {
			if(relationship.getSubject().equals(member) && relationship.getObject().equals(otherMember)
					&& relationship.getType().equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if {@code member} is in a parent-child relationship with {@code otherMember}
	 * of type {@code type}
	 * @return {@code true} if {@code member} is in a parent-child relationship with {@code otherMember}
	 * of type {@code type}, {@code false} otherwise
	 */
	public boolean isInAParentChildRelationship(E member, E otherMember) {
		return this.isInARelationship(member, otherMember, Relationship.RelationshipType.PARENT_OF);
	}
	
	/**
	 * Checks if {@code member} is in a spouse relationship with {@code otherMember}
	 * of type {@code type}
	 * @return {@code true} if {@code member} is in a spouse relationship with {@code otherMember}
	 * of type {@code type}, {@code false} otherwise
	 */
	public boolean isInASpouseRelationship(E member, E otherMember) {
		return this.isInARelationship(member, otherMember, Relationship.RelationshipType.SPOUSE_OF);
	}
	
	/**
	 * Returns a graph representation of the family tree
	 * @return the graph representation of the family tree
	 */
	public MutableGraph getGraph() {
		MutableGraph g = mutGraph(this.name).setDirected(true).graphAttrs()
																.add(Attributes.attr("ranksep", 2))
																.graphAttrs()
																.add(Attributes.attr("nodesep", 2));
		
		Set<String> parentChildEdgesAlreadyDescribed = new HashSet<String>();
		
		// Connect nodes
		for(LinkedList<Relationship<E>> nodeRelationships : this.relationships.values()) {
			for(Relationship<E> relationship : nodeRelationships) {
				String objectLabel = labels.get(relationship.getObject());
				String subjectLabel = labels.get(relationship.getSubject());
				
				String edgeLabel = (objectLabel.compareTo(subjectLabel) < 0 ? objectLabel + subjectLabel :
					subjectLabel + objectLabel);
				
				String reversedEdgeLabel = (objectLabel.compareTo(subjectLabel) < 0 ? objectLabel + subjectLabel :
					subjectLabel + objectLabel);
				
				if(relationship.getType() != Relationship.RelationshipType.PARENT_OF 
						|| (relationship.getType() == Relationship.RelationshipType.PARENT_OF 
							&& !parentChildEdgesAlreadyDescribed.contains(edgeLabel))) {
					parentChildEdgesAlreadyDescribed.add(edgeLabel);
					if(relationship.getType() == Relationship.RelationshipType.SPOUSE_OF) {
						g.add(mutNode(subjectLabel).addLink(to(mutNode(objectLabel)).with(Color.RED)));
						
						parentChildEdgesAlreadyDescribed.add(reversedEdgeLabel);
						g.add(mutNode(objectLabel).addLink(to(mutNode(subjectLabel)).with(Color.RED)));
					} else {
						g.add(mutNode(subjectLabel).addLink(to(mutNode(objectLabel))));
					}
				}
			}
		}
		
		return g;
	}

	/**
	 * Return the name of the family tree
	 * @return the name of the family tree
	 */
	public String getName() {
		return this.name;
	}
}
