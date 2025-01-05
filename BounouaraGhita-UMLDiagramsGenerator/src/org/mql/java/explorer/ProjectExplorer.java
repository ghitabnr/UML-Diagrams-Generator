package org.mql.java.explorer;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ProjectExplorer {

	private Set<String> packageNames;
	
	public ProjectExplorer() {
	}
	
	
	public Set<String> scanProject(String projectPath) {
		
		String classPath = projectPath + "\\bin";
		packageNames = new HashSet<>();
			
	    File folder = new File(classPath);
	    File files[] = folder.listFiles();
		if(folder.isDirectory() && folder.exists()) {
			for (File file : files) {
				packageNames.addAll(parserFolder(file,folder.getAbsolutePath()));//folder.getAbsolutePath()=C:\Users\pc\UML-Diagrams-Generator\BounouaraGhita-UMLDiagramsGenerator\bin
			} 
		 }
		else {
	       System.out.println("The specified folder does not exist or is not a valid folder.");
	    }

     		
	 	return packageNames;
	}

	public Set<String> parserFolder(File folder,String binPath) {
		File[] files = folder.listFiles();
		if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                	packageNames.addAll(parserFolder(file,binPath));
                } else {
                    if (file.getName().endsWith(".class")) {
                     	packageNames.add(getPackageName(file,binPath));
					}
                }
            }
        }
		
	return packageNames;
	}
	

	private String getPackageName(File Classpath,String binPath) {
		//System.out.println(binPath);
		//System.out.println(Classpath);
		String absolutePath = Classpath.getAbsolutePath();	
		//System.out.println(absolutePath);
		String className =absolutePath.substring(0, absolutePath.length() - 6);
		String relativePath = className.substring(binPath.length() + 1);
		//System.out.println(relativePath);
        return removeLastElement(relativePath);
    }
	
	private static String removeLastElement(String relativePath) {
        int lastSeparatorIndex = relativePath.lastIndexOf("\\");
        
        if (lastSeparatorIndex != -1) {
        	String packageName = relativePath.substring(0, lastSeparatorIndex);
        	//System.out.println(packageName);
        	return packageName.replace('\\', '.');
        } else {
            return relativePath.replace('\\', '.');
        }
    }
	
	
	/*public static void main(String[] args) {
		ExplorerProject p =new ExplorerProject();
		p.getPackageName(null, null);
	}*/
}
