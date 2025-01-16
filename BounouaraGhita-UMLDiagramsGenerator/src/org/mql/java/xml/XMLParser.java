package org.mql.java.xml;

import java.util.HashSet;
import java.util.Set;

public class XMLParser {
		
		public XMLParser() {
			
		}
		
		public void parse(String source) {
			XMLNode root = new XMLNode(source);
		    String projectName = root.getAttribute("name");
		    System.out.println("Project : " + projectName);

		    for (int i = 0; i < root.getChildren().length; i++) {
		        System.out.println("\t Package: " + root.getChildren()[i].getAttribute("name"));

		        for (int j = 0; j < root.getChildren()[i].getChildren().length; j++) {
		            System.out.println("\t\t Classe: " + root.getChildren()[i].getChildren()[j].getAttribute("name"));

		            XMLNode methodsNode = root.getChildren()[i].getChildren()[j].getChild("methods");
		            if (methodsNode != null) {
		            	XMLNode[] methods = methodsNode.getChildren();
		                    for (int k = 0; k < methods.length; k++) {
		                    	XMLNode methodNode = methods[k];
		                        if (methodNode != null) {
		                            System.out.println("\t\t\t Method : " + methodNode.getAttribute("name"));
		                        }
		                    }
		               
		            } else {
		                System.out.println("\t\t\t Cette Classe ne contient aucun methode.");
		            }

		            XMLNode fieldsNode = root.getChildren()[i].getChildren()[j].getChild("fields");
		            if (fieldsNode != null) {
		            	XMLNode[] fields = fieldsNode.getChildren();
		                    for (int k = 0; k < fields.length; k++) {
		                    	XMLNode fieldNode = fields[k];
		                        if (fieldNode != null) {
		                            System.out.println("\t\t\t field : " + fieldNode.getAttribute("name"));
		                        }
		                    }
		               
		            } else {
		                System.out.println("\t\t\t Cette Classe ne contient aucun attribut.");
		            }

		        }
		    }

		}


	}
