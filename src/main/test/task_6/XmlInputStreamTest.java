package task_6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class XmlInputStreamTest {

    private Unit beforeSerialize;
    private XmlInputStream xis;
    private XmlOutputStream xos;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        beforeSerialize = new Unit(1, "John", 19216801L);
        xis = new XmlInputStream();
        xos = new XmlOutputStream();
    }

    @Test
    public void deSerialize() throws IOException, IllegalAccessException {
        File forSerialize = testFolder.newFile("test.xml");
        xos.serialize(beforeSerialize, forSerialize.getAbsolutePath());
        Unit afterSerialize = (Unit) xis.deSerialize(forSerialize.getAbsolutePath());
        Assert.assertEquals(beforeSerialize, afterSerialize);
    }

    @Test (expected = NoSuchElementException.class)
    public void deSerializeNegativeTest() throws IOException {
        File forSerialize = testFolder.newFile("test.xml");
        Unit afterSerialize = (Unit) xis.deSerialize(forSerialize.getAbsolutePath());
        Assert.assertEquals(beforeSerialize, afterSerialize);
    }


}