package task_3.Builders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MegaFileBuilderTest {

    private static final int KBYTE = 1024;
    private MegaFileBuilder mfb;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private static final String[] MASS = new String[]{"some", "test", "words"};

    @Before
    public void setUp(){
        mfb = new MegaFileBuilder();
    }

    @Test
    public void getFiles() throws IOException {
        int numberOfFiles = 5;
        int sizeInKiloByte = 1;
        File dir = tempFolder.newFolder();
        mfb.getFiles(dir.getAbsolutePath(), numberOfFiles, sizeInKiloByte, MASS, 100);
        File[] list = dir.listFiles();

        assertEquals(list.length, numberOfFiles);
        for (File file : list) {
            assertTrue(file.length() <= sizeInKiloByte * KBYTE);
        }
    }

    @Test (expected = RuntimeException.class)
    public void getFilesNegativeTest() {
        int numberOfFiles = 5;
        int sizeInKiloByte = 1;
        mfb.getFiles("someWrongPath", numberOfFiles, sizeInKiloByte, MASS, 100);
    }
}