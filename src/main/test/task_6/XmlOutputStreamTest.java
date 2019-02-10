package task_6;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class XmlOutputStreamTest {

    private Unit testUnit;
    private XmlOutputStream outputStream;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        testUnit = new Unit(1, "John", 19216801L);
        outputStream = new XmlOutputStream();
    }

    @Test
    public void serializeToFile() throws IOException, IllegalAccessException {
        File file = tempFolder.newFile("test.xml");
        outputStream.serialize(testUnit, file.getAbsolutePath());
        assertTrue(file.length() > 0);
    }

    @Test
    public void serializeFormat() throws IOException, IllegalAccessException {
        File file = tempFolder.newFile("test.xml");
        outputStream.serialize(testUnit, file.getAbsolutePath());
        List<String> allLinesFromFile = Files.readAllLines(file.toPath());
        Field[] fields = testUnit.getClass().getDeclaredFields();

        int numberOfCorrectLines = 0;
        for (String line : allLinesFromFile) {
            if (line.contains(testUnit.getClass().getName())) {
                numberOfCorrectLines++;
                continue;
            }
            for (Field field : fields) {
                if (line.contains(field.getType().getSimpleName())) {
                    numberOfCorrectLines++;
                    break;
                }
            }
        }
        if (numberOfCorrectLines != allLinesFromFile.size()) {
            fail();
        }
    }

    @Test (expected = UnserializableClassException.class)
    public void unserializableObjectTest() throws IOException, IllegalAccessException {
        File file = tempFolder.newFile("test.xml");
        Object o = new Object();
        outputStream.serialize(o, file.getAbsolutePath());
    }
}