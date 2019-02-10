package task_9;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class LoaderImplTest {

    private static final String SOURCE = "source";
    private static final String WORDS = "words";
    private static final String DIR = "dir";
    private static final String EMPTY = "";
    private static final String[] TEST_ARRAY =
            {
                    "some",
                    "number",
                    "of",
                    "words"
            };
    private Properties properties;
    private File file;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        file = testFolder.newFile("test.txt");
        properties = Mockito.mock(Properties.class);
    }

    @Test
    public void loadSourcesPositiveTest() throws IOException {
        Mockito.when(properties.getProperty(SOURCE)).thenReturn(file.getAbsolutePath());

        try (FileWriter fw = new FileWriter(file)) {
            for (String string : TEST_ARRAY) {
                fw.write(string + "\n");
                fw.flush();
            }
        }
        Loader loader = new LoaderImpl(properties);
        String[] result = loader.loadSources();
        Assert.assertArrayEquals(TEST_ARRAY, result);
    }

    @Test
    public void loadSourcesNegativeTest() {
        Mockito.when(properties.getProperty(Mockito.anyString())).thenReturn(EMPTY);

        String[] result = new LoaderImpl(properties).loadSources();
        Assert.assertEquals(0, result.length);
    }

    @Test
    public void loadWordsPositiveTest() throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            for (String string : TEST_ARRAY) {
                fw.write(string + "\n");
                fw.flush();
            }
        }
        Mockito.when(properties.getProperty(WORDS)).thenReturn(file.getAbsolutePath());

        Loader loader = new LoaderImpl(properties);
        String[] result = loader.loadWords();
        Assert.assertArrayEquals(TEST_ARRAY, result);
    }

    @Test
    public void loadWordsNegativeTest() {
        Mockito.when(properties.getProperty(Mockito.anyString())).thenReturn(EMPTY);

        String[] result = new LoaderImpl(properties).loadWords();
        Assert.assertEquals(0, result.length);
    }

    @Test
    public void getOutDirTest() {
        Mockito.when(properties.getProperty(DIR)).thenReturn(EMPTY);

        Loader loader = new LoaderImpl(properties);
        Assert.assertEquals(EMPTY, loader.getOutDir());
    }
}