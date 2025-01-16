package org.mql.java.explorer;

import java.util.List;
import java.util.Set;

public class DataMemory {
	private Set<String> packages;
    private PackagesExplorer Packagesexplorer;
    private ClassesExplorer Classesexplorer;
    
    public DataMemory(String source) {
        packages = new ProjectExplorer().scanProject(source);
        Packagesexplorer = new PackagesExplorer();
        Classesexplorer = new ClassesExplorer();
    }
	
    
    public Set<String> getPackages() {
        return packages;
    }

    public List<Class<?>> getClasses(String packageName, String classPath) {
        return Packagesexplorer.scan(packageName, classPath);
    }

    public String[][] getFields(Class<?> classe) {
        return Classesexplorer.getFields(classe);
    }

    public String[][] getMethods(Class<?> classe) {
        return Classesexplorer.getMethods(classe);
    }
    
    public static void main(String[] args) {
			DataMemory memory  = new DataMemory("C:\\Users\\pc\\UML-Diagrams-Generator\\BounouaraGhita-UMLDiagramsGenerator");
			Set<String> packages = memory.getPackages();
	        System.out.println("Packages: " + packages);
	}
}
