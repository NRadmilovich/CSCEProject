package com.bc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
/**
 * Idea: Have a list that every time someone adds an element, 
 * list will be sorted in decreasing order
 * 
 * @author caden
 *
 * @param <T>
 */
public class SortedList<T> implements Iterable<T>{
	
	private static final int SIZE = 5;
	private T arr[];
	private int size; 

	public SortedList(T[] arr, int size, Comparator<T> desc) {
		super();
		this.arr = (T[])new Object[SIZE];
		this.size = 0;
		Desc = desc;
	}

	// Increases array size by 5
	private void increaseArraySize() {
		
		this.arr = Arrays.copyOf(this.arr, this.arr.length + SIZE);
	}
	
	public T getElementAtIndex(int index) {
		
		if (index < 0 || index>this.size) {
			throw new ArrayIndexOutOfBoundsException("Enter a correct index!!");
		}
		return this.arr[index];
	}

	
	public void addElementToEnd(T item) {
		
		this.addElementAtIndex(item, this.size);
	}
	
	public void addElementToStart(T item) {
		
		this.addElementAtIndex(item, 0);
	}
	
	public void addElementAtIndex(T item, int index) {
		
		if (index < 0 || index>this.size) {
			throw new ArrayIndexOutOfBoundsException("Enter a correct index!!");
		}
		
		if(this.arr.length == this.size) {
			this.increaseArraySize();
		}
		
		for (int i = this.size; i>index; i--) {
			arr[i] = arr[i - 1];
		}
		this.arr[index] = item;
		this.size++;
	}
	
	public void sortList() {
		// TODO use comparator to sort elements
	}
	
	private Comparator<T> Desc = new Comparator<T>() {
		@Override
		public int compare(T x, T y) {
			if (x instanceof Invoice) {
				if(((Invoice) x).getInvoiceTotal() > ((Invoice) y).getInvoiceTotal()) {
					return -1;
				} else if (((Invoice) y).getInvoiceTotal() > ((Invoice) x).getInvoiceTotal()) {
					return 1;
				} else {			
					return 0;
				}		
			}	
			return 2;
		}
	};

	@Override
	public Iterator<T> iterator() {
		return this.iterator();
	}
	
//	public String toString() {
//		
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i<this.size; i++) {
//			sb.append(this.arr[i] + ",");
//		}
//		return sb.toString();
//	}

}
