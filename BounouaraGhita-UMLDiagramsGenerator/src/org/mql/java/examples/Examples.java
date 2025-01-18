package org.mql.java.examples;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.mql.java.annotations.Relation;
import org.mql.java.explorer.DataMemory;
import org.mql.java.explorer.PackagesExplorer;
import org.mql.java.explorer.ProjectExplorer;

import org.mql.java.xml.XMLParser;

public class Examples extends JFrame{
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
	
	 void exp03(){
		 
		 DataMemory memory = new DataMemory("C:\\\\Users\\\\pc\\\\UML-Diagrams-Generator\\\\BounouaraGhita-UMLDiagramsGenerator");
		 String classpath = "C:\\\\Users\\\\pc\\\\UML-Diagrams-Generator\\\\BounouaraGhita-UMLDiagramsGenerator\\\\bin";
		 Set<String> packages = memory.getPackages();

		 for (String packageName : packages) {
		     System.out.println("Package: " + packageName);
		     for (Class<?> clazz : memory.getClasses(packageName, classpath)) {
		         System.out.println("  Classe: " + clazz.getSimpleName());

		         System.out.println("    Fields:");
		         for (Field field : clazz.getDeclaredFields()) {
		             System.out.println("      - " + field.getName() + ": " + field.getType().getSimpleName());

		             if (field.isAnnotationPresent(Relation.class)) {
		                 Relation relation = field.getAnnotation(Relation.class);
		                 String relationType = relation.type();
		                 String targetClass = getGenericType(field); 
		                 System.out.println("        Relation: " + relationType + " avec " + targetClass);
		             }
		         }

		         System.out.println("    Méthodes:");
		         for (Method method : clazz.getDeclaredMethods()) {
		             System.out.println("      + " + method.getName() + "(): " + method.getReturnType().getSimpleName());
		         }
		     }
		 }
	 }

	 private String getGenericType(Field field) {
		    if (field.getGenericType() instanceof ParameterizedType) {
		        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		        java.lang.reflect.Type[] typeArguments = genericType.getActualTypeArguments();
		        if (typeArguments.length > 0) {
		            return ((Class<?>) typeArguments[0]).getSimpleName(); 
		        }
		    }
		    return field.getType().getSimpleName(); 
		}
	
	
	public static void main(String[] args) {
		new Examples();
	}
}
