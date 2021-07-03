package ua.itea.parser.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.itea.parser.Parser;
import ua.itea.pom.Dependency;
import ua.itea.pom.Project;
import ua.itea.pom.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component("SAXParser")
public class PomSAXParser implements Parser {

    @Value("${file.name}")
    private String fileName;

    public PomSAXParser() {
    }

    public PomSAXParser(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void parse() {
        if(fileName == null){
            System.out.println("File name is not sets");
            return;
        }
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            PomHandler handler = new PomHandler();
            saxParser.parse(fileName, handler);
            printProject(handler.getProject());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error parse - " + e.getMessage());
        }
    }

    private void printProject(Project project) {
        System.out.println("SAXParser - " + project);
    }

    public static class PomHandler extends DefaultHandler {

        private static final String PROJECT_ELEMENT = "project";

        private static final String PROJECT_FIELD_MODEL_VERSION = "modelVersion";

        private static final String PROJECT_FIELD_GROUP = "groupId";

        private static final String PROJECT_FIELD_ARTIFACT = "artifactId";

        private static final String PROJECT_FIELD_VERSION = "version";

        private static final String PROPERTIES_ELEMENT = "properties";

        private static final String PROPERTIES_FIELD_ENCODING = "project.build.sourceEncoding";

        private static final String PROPERTIES_FIELD_COURSE = "maven.compiler.course";

        private static final String PROPERTIES_FIELD_TARGET = "maven.compiler.target";

        private static final String DEPENDENCIES_ELEMENT = "dependencies";

        private static final String DEPENDENCY_ELEMENT = "dependency";

        private static final String DEPENDENCY_FIELD_GROUP = "groupId";

        private static final String DEPENDENCY_FIELD_ARTIFACT = "artifactId";

        private static final String DEPENDENCY_FIELD_VERSION = "version";

        private Project project;

        private Properties properties;

        private List<Dependency> dependencies;

        private Dependency dependency;

        private String thisElementName;

        private String currentObject;

        public Project getProject() {
            return project;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            thisElementName = qName;
            if (PROJECT_ELEMENT.equals(thisElementName)) {
                project = new Project();
                currentObject = PROJECT_ELEMENT;
            } else if (PROPERTIES_ELEMENT.equals(thisElementName)) {
                properties = new Properties();
                currentObject = PROPERTIES_ELEMENT;
            } else if (DEPENDENCIES_ELEMENT.equals(thisElementName)) {
                dependencies = new LinkedList<>();
                currentObject = DEPENDENCIES_ELEMENT;
            } else if (DEPENDENCY_ELEMENT.equals(thisElementName)) {
                dependency = new Dependency();
                currentObject = DEPENDENCY_ELEMENT;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length).trim();
            if (!value.isEmpty()) {
                if (PROJECT_ELEMENT.equals(currentObject) && project != null) {
                    addValueToProject(value);
                } else if (PROPERTIES_ELEMENT.equals(currentObject) && properties != null) {
                    addValueToProperties(value);
                } else if (DEPENDENCY_ELEMENT.equals(currentObject) && dependency != null) {
                    addValueToDependency(value);
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (PROPERTIES_ELEMENT.equals(qName) && project != null) {
                project.setProperties(properties);
                properties = null;
                currentObject = null;
            } else if (DEPENDENCIES_ELEMENT.equals(qName) && project != null) {
                project.setDependencies(dependencies);
                dependencies = null;
                currentObject = null;
            } else if (DEPENDENCY_ELEMENT.equals(qName) && dependencies != null) {
                dependencies.add(dependency);
                dependency = null;
                currentObject = null;
            }
        }

        private void addValueToProject(String value) {
            switch (thisElementName) {
                case PROJECT_FIELD_MODEL_VERSION:
                    project.setModelVersion(value);
                    break;
                case PROJECT_FIELD_GROUP:
                    project.setGroupId(value);
                    break;
                case PROJECT_FIELD_ARTIFACT:
                    project.setArtifactId(value);
                    break;
                case PROJECT_FIELD_VERSION:
                    project.setVersion(value);
                    break;
            }
        }

        private void addValueToProperties(String value) {
            switch (thisElementName) {
                case PROPERTIES_FIELD_ENCODING:
                    properties.setProjectBuildSourceEncoding(value);
                    break;
                case PROPERTIES_FIELD_COURSE:
                    properties.setMavenCompilerCourse(value);
                    break;
                case PROPERTIES_FIELD_TARGET:
                    properties.setMavenCompilerTarget(value);
                    break;
            }
        }

        private void addValueToDependency(String value) {
            switch (thisElementName) {
                case DEPENDENCY_FIELD_GROUP:
                    dependency.setGroupId(value);
                    break;
                case DEPENDENCY_FIELD_ARTIFACT:
                    dependency.setArtifactId(value);
                    break;
                case DEPENDENCY_FIELD_VERSION:
                    dependency.setVersion(value);
                    break;
            }
        }
    }
}
