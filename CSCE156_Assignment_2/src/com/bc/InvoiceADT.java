package com.bc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InvoiceADT<T> {
	
	private T array[];
	private int size;
	
	public InvoiceADT() {
		this.size = 0;
		this.array =  (T[])new Object[size];

	}
	public void addAt(T item, int index) {
		if (index < 0 || index > this.size) {
			throw new ArrayIndexOutOfBoundsException("Enter a correct index!");
		}
	}
	public void set(int index, T object) {
		if(index > this.size - 1) {
			throw new ArrayIndexOutOfBoundsException("Index out of array bounds!");
		}
		this.array[index] = object;
	}
	public T get(int index) {
		if(index > this.size - 1) {
			throw new ArrayIndexOutOfBoundsException("Index out of array bounds!");
		}
		return this.array[index];
	}
	public static void swap(ArrayList<Invoice> list, int index1, int index2) {
		Invoice temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
	
	public static ArrayList<Invoice> InvoiceSort(ArrayList<Invoice> values){
		// Initialize Variables
		int i,j,index;
		index = values.size();
		for( i = 0; i < (index-1); i++) {
			for(j = 0; j<(index - i - 1); j++) {
				if(values.get(j).getInvoiceTotal() < values.get(j+1).getInvoiceTotal()) {
					InvoiceADT.swap(values,j,j+1);
				}}}
		return values;
	}
}
