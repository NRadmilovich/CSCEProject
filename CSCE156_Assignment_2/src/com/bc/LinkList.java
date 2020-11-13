package com.bc;

import java.util.*;
/**
 * CSCE 156
 * 
 * Authors:
 * Caden Kirby
 * Nick Radmilovich
 * 
 * 11/13/2020
 * 
 *The LinkList class is a custom sorted list ADT, which will sort items as they are added.
 */

public class LinkList<T extends Comparable<T>> implements Iterable<T> {
	
	private Node<T> head;
	private int size;
	
	// Constructor
	public LinkList() {
		this.head =  null;
		this.size = 0;
	}
	
	// Inserts a node at the front of the list
	public void frontInsert(T item) {
		Node<T> temp = new Node<T>(item);
		temp.setNext(this.head);
		this.head = temp;
		this.size++;
	}
	
	//Returns a node at a specific index
	public Node<T> getNode(int index){
		if (index < 0 || index >= this.size) {
			throw new ArrayIndexOutOfBoundsException("Enter a correct index!");
		}
		Node<T> current = this.head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}
	
	// Adds at a certain index
	public void addAt(T item, int index) {
		if (index < 0 || index >= this.size) {
			throw new ArrayIndexOutOfBoundsException("Enter a correct index!");
		}
		if(index == 0) {
			this.frontInsert(item);
			return;
		}
		// Swaps and assigns nodes.
		Node<T> current = this.getNode(index - 1);
		Node<T> newNode = new Node<T>(item);
		newNode.setNext(current.getNext());
		current.setNext(newNode);
		this.size++;
	}
	// Adds and automatically sorts
	public void add(T item) {
		// Checks for existing list.
		if(this.size == 0) {
			this.frontInsert(item);
			return;
		}
		Node<T> current = this.head;
		Node<T> newNode = new Node<T>(item);
		// Checks first value, because loop excludes first value
		if(item.compareTo(current.getItem()) != -1) {
			this.frontInsert(item);
			return;
		}
		// Compares to any existing items in nodes, for ordering
		while(current.hasNext()) {
			if(item.compareTo(current.getNext().getItem()) <= 0) {
				current = current.getNext();
			}else {
				newNode.setNext(current.getNext());
				current.setNext(newNode);
				this.size++;
				return;
			}
		}
		current.setNext(newNode);
	}
	
	// Sets the item at a specific node.
	public void set(int index, T object) {
		if(index > this.size - 1) {
			throw new ArrayIndexOutOfBoundsException("Index out of array bounds!");
		}
		Node<T> current = this.getNode(index);
		current.setItem(object);
	}
	
	// Returns the item at a specific node
	public T get(int index) {
		return getNode(index).getItem();
	}
	
	// Returns six of link list.
	public int getSize() {
		return this.size;
	}
	// Iterator for LinkList
	@Override
	public Iterator<T> iterator() {

		return new LinkListIterator();
	}
	
	// Iterator class for use in LinkList
	public class LinkListIterator implements Iterator<T> {
		Node<T> current = head;
		
		//Returns a boolean value depending on if node has a next value.
		public boolean hasNext() {
			return current != null;
		}
		
		// Returns next node if one exists.
		public T next() {
			if(hasNext()) {
				T value = current.getItem();
				current = current.getNext();
				return value;
			}
			return null;
		}
	}

}
