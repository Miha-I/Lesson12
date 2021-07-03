import org.junit.jupiter.api.*;
import org.xml.sax.SAXException;
import ua.itea.parser.impl.PomSAXParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSAXParser {

    private String testFile;

    private PomSAXParser parser;

    private PomSAXParser.PomHandler handler;

    private PomSAXParser.PomHandler handlerWithoutParse;

    @BeforeAll
    void init() throws ParserConfigurationException, SAXException, IOException {
        testFile = "src/test/resources/testFile.xml";
        parser = new PomSAXParser();
        handler = new PomSAXParser.PomHandler();
        handlerWithoutParse = new PomSAXParser.PomHandler();
        SAXParserFactory.newInstance().newSAXParser().parse(testFile, handler);
    }

    @Test
    @DisplayName("Test without set file name")
    void testParser() {
        Assertions.assertDoesNotThrow(parser::parse);
    }

    @Test
    @DisplayName("Test with set wrong file name")
    void testParser2() {
        parser.setFileName("wrong.file");
        Assertions.assertDoesNotThrow(parser::parse);
    }

    @Test
    @DisplayName("Test with set file name")
    void testParser3() {
        parser.setFileName(testFile);
        Assertions.assertDoesNotThrow(parser::parse);
    }

    @Test
    @DisplayName("Test without set property")
    void testParser4() {
        Assertions.assertNull(handler.getProject().getProperties().getMavenCompilerCourse());
    }

    @Test
    @DisplayName("Test with set property as space")
    void testParser5() {
        Assertions.assertNull(handler.getProject().getProperties().getMavenCompilerTarget());
    }

    @Test
    @DisplayName("Test with set property")
    void testParser6() {
        Assertions.assertNotNull(handler.getProject().getVersion());
    }

    @Test
    @DisplayName("Test not empty nested objects")
    void testParser7() {
        Assertions.assertFalse(handler.getProject().getDependencies().isEmpty());
    }

    @Test
    @DisplayName("Test with same properties")
    void testParser8() {
        Assertions.assertEquals(handler.getProject().getGroupId(), handler.getProject().getDependencies().get(0).getGroupId());
    }

    @Test
    @DisplayName("Test with different properties")
    void testParser9() {
        Assertions.assertNotEquals(handler.getProject().getGroupId(), handler.getProject().getArtifactId());
    }

    @Test
    @DisplayName("Test handler startElement method")
    void testParser10() {
        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.startElement("", "", "root", null)),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.startElement("", "", null, null)),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.startElement("", "", "dependencies", null)),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.startElement("", "", "project", null)),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.startElement("", "", "groupId", null))
        );
    }

    @Test
    @DisplayName("Test handler endElement method")
    void testParser11() {
        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.endElement("", "", "child")),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.endElement("", "", null)),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.endElement("", "", "properties")),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.endElement("", "", "artifactId")),
                () -> Assertions.assertDoesNotThrow(() -> handlerWithoutParse.endElement("", "", "version"))
        );
    }
}
