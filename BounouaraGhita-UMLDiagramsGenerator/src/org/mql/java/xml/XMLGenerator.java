package org.mql.java.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.explorer.ClassesExplorer;
import org.mql.java.explorer.PackagesExplorer;
import org.mql.java.explorer.ProjectExplorer;
import org.mql.java.explorer.RelationExplorer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLGenerator {
    static String path = "C:\\Users\\pc\\UML-Diagrams-Generator\\BounouaraGhita-UMLDiagramsGenerator";
    static String classpath = "C:\\Users\\pc\\UML-Diagrams-Generator\\BounouaraGhita-UMLDiagramsGenerator\\bin";

    public static void main(String[] args) throws FileNotFoundException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("projet");
            document.appendChild(root);

            Attr rootAttr = document.createAttribute("name");
            rootAttr.setValue(path);
            root.setAttributeNode(rootAttr);

            ProjectExplorer packages = new ProjectExplorer();
           RelationExplorer explorerRelation = new RelationExplorer();

            for (String p : packages.scanProject(path)) {
                Element pack = document.createElement("package");
                Attr packageNameAttr = document.createAttribute("name");
                packageNameAttr.setValue(p);
                pack.setAttributeNode(packageNameAttr);

                PackagesExplorer classes = new PackagesExplorer();
                for (Class<?> c : classes.scan(p, classpath)) {
                    String tagName = c.isInterface() ? "interface" : c.isAnnotation() ? "annotation" : "classe";
                    Element cls = document.createElement(tagName);

                    Attr classNameAttr = document.createAttribute("name");
                    classNameAttr.setValue(c.getSimpleName());
                    cls.setAttributeNode(classNameAttr);

                    ClassesExplorer properties = new ClassesExplorer();

                    String[][] fieldsData = properties.getFields(c);
                    if (fieldsData[0].length > 0) {
                        Element fields = document.createElement("fields");
                        for (int i = 0; i < fieldsData[0].length; i++) {
                            Element field = document.createElement("field");
                            
                            Attr modifier = document.createAttribute("modifier");
                            modifier.setValue(fieldsData[0][i]);
                            field.setAttributeNode(modifier);

                            Attr type = document.createAttribute("type");
                            type.setValue(fieldsData[1][i]);
                            field.setAttributeNode(type);

                            Attr name = document.createAttribute("name");
                            name.setValue(fieldsData[2][i]);
                            field.setAttributeNode(name);

                            fields.appendChild(field);
                        }
                        cls.appendChild(fields);
                    }

                    String[][] methodsData = properties.getMethods(c);
                    if (methodsData[0].length > 0) {
                        Element methods = document.createElement("methods");
                        for (int i = 0; i < methodsData[0].length; i++) {
                            Element method = document.createElement("method");

                            Attr modifier = document.createAttribute("modifier");
                            modifier.setValue(methodsData[0][i]);
                            method.setAttributeNode(modifier);

                            Attr type = document.createAttribute("type");
                            type.setValue(methodsData[1][i]);
                            method.setAttributeNode(type);

                            Attr name = document.createAttribute("name");
                            name.setValue(methodsData[2][i]);
                            method.setAttributeNode(name);

                            methods.appendChild(method);
                        }
                        cls.appendChild(methods);
                    }

                    Element relations = document.createElement("relations");
                    if (explorerRelation.getSuperClasse(c) != null) {
                        Element relation = document.createElement("relation");
                        Attr classe = document.createAttribute("classe");
                        classe.setValue(explorerRelation.getSuperClasse(c));
                        relation.setAttributeNode(classe);
                        relation.setTextContent("extension");
                        relations.appendChild(relation);
                    }

                    if (explorerRelation.getInterfaces(c) != null) {
                        for (String iface : explorerRelation.getInterfaces(c)) {
                            Element relation = document.createElement("relation");
                            Attr classe = document.createAttribute("classe");
                            classe.setValue(iface);
                            relation.setAttributeNode(classe);
                            relation.setTextContent("implementation");
                            relations.appendChild(relation);
                        }
                    }

                    cls.appendChild(relations);
                    pack.appendChild(cls);
                }
                root.appendChild(pack);
            }

            saveXML(document, "resources/project.xml");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void saveXML(Document document, String filePath) throws TransformerException, FileNotFoundException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new FileOutputStream(filePath));
        transformer.transform(domSource, streamResult);
    }
}
