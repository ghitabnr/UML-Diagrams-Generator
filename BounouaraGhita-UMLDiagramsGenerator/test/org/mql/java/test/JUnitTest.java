package org.mql.java.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mql.java.explorer.ClassesExplorer;
import org.mql.java.explorer.DataMemory;
import org.mql.java.explorer.PackagesExplorer;
import org.mql.java.explorer.ProjectExplorer;
import org.mql.java.explorer.RelationExplorer;

import java.util.List;
import java.util.Set;

public class JUnitTest {

    @Test
    void testClassesExplorer() {
        ClassesExplorer explorer = new ClassesExplorer();
        Class<?> clazz = String.class; 
        
        String[][] fields = explorer.getFields(clazz);
        assertNotNull(fields, "Les champs ne devraient pas être null");
        assertTrue(fields.length > 0, "La classe devrait avoir des champs");

        String[][] methods = explorer.getMethods(clazz);
        assertNotNull(methods, "Les méthodes ne devraient pas être null");
        assertTrue(methods.length > 0, "La classe devrait avoir des méthodes");
    }

    @Test
    void testDataMemory() {
        String projectPath = "C:\\Users\\pc\\UML-Diagrams-Generator\\BounouaraGhita-UMLDiagramsGenerator";
        DataMemory dataMemory = new DataMemory(projectPath);

        Set<String> packages = dataMemory.getPackages();
        assertNotNull(packages, "Les packages ne devraient pas être null");
        assertFalse(packages.isEmpty(), "Le projet devrait contenir des packages");

        String packageName = packages.iterator().next(); 
        List<Class<?>> classes = dataMemory.getClasses(packageName, projectPath + "\\bin");
        assertNotNull(classes, "Les classes ne devraient pas être null");
        assertFalse(classes.isEmpty(), "Le package devrait contenir des classes");
    }

    @Test
    void testPackagesExplorer() {
        String projectPath = "C:\\Users\\pc\\UML-Diagrams-Generator\\BounouaraGhita-UMLDiagramsGenerator";
        PackagesExplorer explorer = new PackagesExplorer();
        String packageName = "org.mql.java"; 

        List<Class<?>> classes = explorer.scan(packageName, projectPath + "\\bin");
        assertNotNull(classes, "Les classes ne devraient pas être null");
        assertFalse(classes.isEmpty(), "Le package devrait contenir des classes");
    }

    @Test
    void testProjectExplorer() {
        String projectPath = "C:\\Users\\pc\\UML-Diagrams-Generator\\BounouaraGhita-UMLDiagramsGenerator";
        ProjectExplorer explorer = new ProjectExplorer();

        Set<String> packages = explorer.scanProject(projectPath);
        assertNotNull(packages, "Les packages ne devraient pas être null");
        assertFalse(packages.isEmpty(), "Le projet devrait contenir des packages");
    }

    @Test
    void testRelationExplorer() {
        RelationExplorer explorer = new RelationExplorer();
        Class<?> clazz = String.class;

        String superClass = explorer.getSuperClasse(clazz);
        assertNotNull(superClass, "La superclasse ne devrait pas être null");

        List<String> interfaces = explorer.getInterfaces(clazz);
        assertNotNull(interfaces, "Les interfaces ne devraient pas être null");
    }
}