package com.bc;

import java.util.ArrayList;

public interface InvoiceADT {
public static ArrayList<Invoice> InvoiceSort(ArrayList<Invoice> values){
	// Initialize Variables
	int i,j,index;
	Invoice temp;
	index = values.size() - 1;
	for( i = 0; i < (index); i++) {
		for(j = 0; j<(index - i - 1); j++) {
			if(values.get(j).totalCalc() < values.get(j+1).totalCalc()) {
				temp = values.get(j);
				//values[j] = values[j+1];
				//values[j+1] = temp;
			}
		}
	}
	return values;
}
public static void swap(ArrayList<Invoice> list, int index1, int index2) {
	
}
}
