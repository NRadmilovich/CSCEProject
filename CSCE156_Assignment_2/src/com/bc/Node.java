package com.bc;
/**
 * CSCE 156
 * 
 * Authors:
 * Caden Kirby
 * Nick Radmilovich
 * 
 * 11/13/2020
 * 
 * Node class for use with LinkList class.
 */
// Constructor
public class Node<T> {
	private T item;
	private Node<T> next;
	
	// Constructor
	public Node(T item) {
		this.item = item;
		this.next = null;
	}
	
	// Retrieves Item in Node
	public T getItem() {
		return this.item;
	}
	
	//Sets Item in node
	public void setItem(T item) {
		this.item = item;
	}
	
	// Returns next node
	public Node<T> getNext(){
		return this.next;
	}
	
	// Assigns next node
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	// Returns a boolean value depending on if the node has a next node.
	public boolean hasNext() {
		return this.next != null;
	}
}
