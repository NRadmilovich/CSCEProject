package com.bc;

import java.util.ArrayList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class XMLPrint {

	public static <P> void printXML(String filepath, ArrayList<P> list, String className) {
		XStream xstream = new XStream(new DomDriver());
		String classN = "com.bc." + className;
		Class<?> classPath = null;
		try {
			classPath = Class.forName(classN);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		String header = "<" + className + ">";
		String closer = "</" + className + ">";
		String classLower = className.toLowerCase();
		xstream.alias(classLower, classPath);
		try {
			PrintWriter out= new PrintWriter(new File(filepath));
			out.print("<?xml version=\"1.0\"?>\n");
			out.write(header);
			out.write("\n");
			for(P entry: list) {
				out.write(xstream.toXML(entry));
				out.write("\n");
			}
			out.write(closer);
			out.close();		
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		}

}
