package com.bc;
/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: This class takes in an ArrayList and a class name, and writes them into a json file.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWrite {

	public static <P> void printJSON(String filePath, ArrayList<P> list, String className) {

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		try {
			PrintWriter out = new PrintWriter(new File(filePath));

			out.write("{\n");
			out.write("\"" + className + "\":");
			out.write(gson.toJson(list));
			out.write("}");

			out.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

	}
}
