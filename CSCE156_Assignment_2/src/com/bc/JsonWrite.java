package com.bc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWrite {

	public static <P> void printJSON(String filePath, ArrayList<P> list) {

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		try {
			PrintWriter out = new PrintWriter(new File(filePath));

			if (list.get(0) instanceof Rental) {

				String header = "Rental";

				out.write("{\n");
				out.write("\"" + header + "\":");
				out.write(gson.toJson(list));
				out.write("}");

			} else if (list.get(0) instanceof Repair) {

				String header = "Repair";

				out.write("{\n");
				out.write("\"" + header + "\":");
				out.write(gson.toJson(list));
				out.write("}");

			} else if (list.get(0) instanceof Concession) {

				String header = "Concession";

				out.write("{\n");
				out.write("\"" + header + "\":");
				out.write(gson.toJson(list));
				out.write("}");

			} else if (list.get(0) instanceof Towing) {

				String header = "Towing";

				out.write("{\n");
				out.write("\"" + header + "\":");
				out.write(gson.toJson(list));
				out.write("}");

			} else if (list.get(0) instanceof Person) {

				String header = "Person";

				out.write("{\n");
				out.write("\"" + header + "\":");
				out.write(gson.toJson(list));
				out.write("}");

			} else if (list.get(0) instanceof Customer) {

				String header = "Customer";

				out.write("{\n");
				out.write("\"" + header + "\":");
				out.write(gson.toJson(list));
				out.write("}");

			}
			out.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

	}
}
