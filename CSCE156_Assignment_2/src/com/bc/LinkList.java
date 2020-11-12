package com.bc;

import java.util.*;

public class LinkList<T extends Comparable<T>> implements Iterable<T> {
	
	private static final int initial = 1;
	private Node<T> head;
	private int size;
	
	public LinkList() {
		this.head =  null;
		this.size = 0;
	}
	public void frontInsert(T item) {
		Node<T> temp = new Node<T>(item);
		temp.setNext(this.head);
		this.head = temp;
		this.size++;
	}
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
		Node<T> current = this.getNode(index - 1);
		Node<T> newNode = new Node<T>(item);
		newNode.setNext(current.getNext());
		current.setNext(newNode);
		this.size++;
	}
	// Adds and automatically sorts
	public void add(T item) {
		if(this.size == 0) {
			this.frontInsert(item);
			return;
		}
		Node<T> current = this.head;
		Node<T> newNode = new Node<T>(item);
		if(item.compareTo(current.getItem()) != -1) {
			this.frontInsert(item);
			return;
		}
		while(current.hasNext()) {
			System.out.print("We are here");
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
	public void set(int index, T object) {
		if(index > this.size - 1) {
			throw new ArrayIndexOutOfBoundsException("Index out of array bounds!");
		}
		Node<T> current = this.getNode(index);
		current.setItem(object);
	}
	public T get(int index) {
		return getNode(index).getItem();
	}
	
	public int getSize() {
		return this.size;
	}
	@Override
	public Iterator<T> iterator() {

		return new LinkListIterator();
	}
	
	public class LinkListIterator implements Iterator<T> {
		Node<T> current = head;
		
		public boolean hasNext() {
			return current != null;
		}
		
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
