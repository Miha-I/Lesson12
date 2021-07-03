package ua.itea.parser.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ua.itea.parser.Parser;
import ua.itea.pom.Dependency;
import ua.itea.pom.Project;
import ua.itea.pom.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component("DOMParser")
@SuppressWarnings({"unused", "RedundantSuppression"})
public class PomDOMParser implements Parser {

    @Value("${file.name}")
    private String fileName;

    public PomDOMParser() {
    }

    public PomDOMParser(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void parse() {
        if (fileName == null) {
            System.out.println("File name is not sets");
            return;
        }
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new File(fileName));

            Element root = document.getDocumentElement();
            Project project = createProject(root);
            printProject(project);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error parse - " + e.getMessage());
        }
    }

    private void printProject(Project project) {
        System.out.println("DOMParser - " + project);
    }

    private Project createProject(Element root) {
        if (root != null) {
            Project project = new Project();
            project.setModelVersion(getElementText(root, "modelVersion"));
            project.setGroupId(getElementText(root, "groupId"));
            project.setArtifactId(getElementText(root, "artifactId"));
            project.setVersion(getElementText(root, "version"));
            project.setProperties(getProperties(root));
            project.setDependencies(getDependencies(root));
            return project;
        }
        return null;
    }

    private Properties getProperties(Element root) {
        Node node = getFirstChildNodeByName(root, "properties");
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            Properties properties = new Properties();
            properties.setProjectBuildSourceEncoding(getElementText(element, "project.build.sourceEncoding"));
            properties.setMavenCompilerCourse(getElementText(element, "maven.compiler.course"));
            properties.setMavenCompilerTarget(getElementText(element, "maven.compiler.target"));
            return properties;
        }
        return null;
    }

    private List<Dependency> getDependencies(Element root) {
        List<Dependency> dependencies = null;
        if (root.getElementsByTagName("dependencies").getLength() != 0) {
            dependencies = new LinkedList<>();
            NodeList nodeList = root.getElementsByTagName("dependency");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Dependency dependency = new Dependency();
                    dependency.setGroupId(getElementText(element, "groupId"));
                    dependency.setArtifactId(getElementText(element, "artifactId"));
                    dependency.setVersion(getElementText(element, "version"));
                    dependencies.add(dependency);
                }
            }
        }
        return dependencies;
    }

    private String getElementText(Element element, String elementName) {
        Node node = getFirstChildNodeByName(element, elementName);
        if (node != null) {
            return node.getTextContent();
        }
        return null;
    }

    private Node getFirstChildNodeByName(Element element, String elementName) {
        NodeList nodeList = element.getElementsByTagName(elementName);
        if (nodeList.getLength() != 0) {
            return nodeList.item(0);
        }
        return null;
    }
}
