package org.mql.java.explorer;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class PackagesExplorer extends ClassLoader{

	public PackagesExplorer() {
	
	}
	
	public List<Class<?>> scan(String packageName, String classPath) {
	
		List<Class<?>> listClass = new Vector<Class<?>>(); 
		try {
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file://" + classPath + "/")}, currentClassLoader);

				
				String packageFolder = packageName.replace('.', '\\');

		        File dir = new File(classPath + '\\' + packageFolder);
		        File classes[] = dir.listFiles();
		        if (classes != null) {
		            for (File f : classes) {
		                String className = packageName + "." + f.getName().replace(".class", "");
		                listClass.add(classCharger(urlClassLoader, className));
		            }
		        }
		} catch (Exception e) {
			System.out.println("Erreur = " + e.getMessage());
		}
		return listClass;
	}
	
	public Class<?> classCharger(URLClassLoader urlClassLoader, String className) {
		Class<?> classe = null ;
		try {
            Class<?> loadedClass = urlClassLoader.loadClass(className);
            classe = loadedClass;
        } catch (ClassNotFoundException e) {
            System.out.println("Classe non trouvée : " + className);
        }
		
		return classe;
	}
	
}