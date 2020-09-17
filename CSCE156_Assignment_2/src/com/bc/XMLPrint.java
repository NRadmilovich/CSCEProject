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
	
		
		try {
			PrintWriter out= new PrintWriter(new File(filepath));
			String header = "<" + className + ">";
			String closer = "</" + className + ">";
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
