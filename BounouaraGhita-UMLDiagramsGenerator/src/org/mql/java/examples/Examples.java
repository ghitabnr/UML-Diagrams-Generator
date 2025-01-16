package org.mql.java.examples;

import java.util.Set;

import org.mql.java.explorer.DataMemory;
import org.mql.java.xml.XMLParser;

public class Examples {
	public Examples() {
		exp01();
	}
	
	 void exp01() {
		  DataMemory memory = new DataMemory("C:\\\\Users\\\\pc\\\\UML-Diagrams-Generator\\\\BounouaraGhita-UMLDiagramsGenerator");
		  String classpath = "C:\\\\Users\\\\pc\\\\UML-Diagrams-Generator\\\\BounouaraGhita-UMLDiagramsGenerator\\\\bin";
		  Set<String> packages = memory.getPackages();
		  for (String s : packages) {
			  System.out.println(s);
			  for (Class<?> c : memory.getClasses(s, classpath)) {
					System.out.println("\t->"+c.getSimpleName());
				}
		}
	  }
	
	
	 void exp02() {
		 
		 XMLParser xmlparser = new XMLParser();
		 xmlparser.parse("resources/project.xml");
		 
	 }
	
	
	 void exp03() {
		 
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		new Examples();
	}
}
