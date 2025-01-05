package org.mql.java.explorer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.annotations.isObject;

public class RelationExplorer {

	public RelationExplorer() {
		
	}
	
	public void relation(String qname) {
    	String parent = "";
		try {
			Class<?> c = Class.forName(qname);
			Field field[] = c.getDeclaredFields();
			 for (Field f : field) {
				 isObject ob = f.getDeclaredAnnotation(isObject.class);
				if(ob!=null) {
	                System.out.println("\t"+ob.type()+"=============>"+c.getSimpleName() + " has a/an " + f.getType().getSimpleName() + " field: " + f.getName());
				}
			}
			 parent = c.getSuperclass().getSimpleName();
			 if(parent != null) {
				 System.out.println("\t Héritage=============>"+parent + "  is a parent of : " + c.getSimpleName());
			 }
		} catch (Exception e) {
			System.out.println("Error : "+ e.getMessage());
		}
		 
    }
	public String  getRelation(Field f) {
		String relationName=null;
		 isObject ob = f.getDeclaredAnnotation(isObject.class);
		 if(ob!=null) {
			 relationName = ob.type();
			}
		return relationName;
		 
	}
	public String getSuperClasse(Class<?> c) {
	    Class<?> superClasse = c.getSuperclass();
	    if (superClasse != null) {
	        return superClasse.getSimpleName();
	    } else {
	        return "Object"; 
	    }
	}
	public List<String> getInterfaces(Class<?> c) {
		List<String> li = new ArrayList<String>();
		Class<?>[] interfaces = c.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            li.add(interfaceClass.getSimpleName());
        }
        return li;
	}

}