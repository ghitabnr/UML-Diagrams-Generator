package org.mql.java.explorer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.Author;

public class ClassesExplorer {
	private Class<?> clazz;
	String className;
	
	public ClassesExplorer() {
		
	}
	public ClassesExplorer(Class<?> clazz) {
        this.clazz = clazz;
    }
	public ClassesExplorer(String className) {
		this.className = className;
}
	public String getName() {
        return className;
    }

	
	public String[][] getFields(Class<?> c) {
	    Field[] fields = c.getDeclaredFields();
	    int n = fields.length;
	    String data[][] = new String[4][n];
	    for (int i = 0; i < n; i++) {
	        Field f = fields[i];
	        int modifiers = f.getModifiers();
	        data[0][i] = Modifier.toString(modifiers);
	        data[1][i] = f.getType().getSimpleName();
	        data[2][i] = f.getName();
	        if(new RelationExplorer().getRelation(f)!=null) {
	        	data[3][i]=new RelationExplorer().getRelation(f);
	        }
	    }
	return data;
	}
	public String[][] getMethods(Class<?> c) {
	    Method[] methods = c.getDeclaredMethods();
	    int nm = methods.length;
	    String[][] data = new String[3][nm];

	    for (int i = 0; i < nm; i++) {
	        Method m = methods[i];
	        int modifiers = m.getModifiers();
	        data[0][i] = Modifier.toString(modifiers);
	        data[1][i] = m.getReturnType().getSimpleName();
	        data[2][i] = m.getName();
	    }
		return data;
        
	}
	public static void main(String[] args) {
		Class<?> c = Author.class;
		ClassesExplorer e = new ClassesExplorer();
		
		 for (int i = 0; i < e.getFields(c).length; i++) {
		        String modifier = e.getFields(c)[0][i];
		        String type = e.getFields(c)[1][i];
		        String name = e.getFields(c)[2][i];

		        System.out.println(modifier + "\t" + type + "\t" + name);
		    }
		 
		 for (int i = 0; i < e.getMethods(c).length; i++) {
		        String modifier = e.getMethods(c)[0][i];
		        String returnType = e.getMethods(c)[1][i];
		        String methodName = e.getMethods(c)[2][i];

		        System.out.println(modifier + "\t" + returnType + "\t" + methodName);
		    }
	}

}