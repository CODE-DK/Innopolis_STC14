package task_9;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class LoaderImplTest {

    private final String EMPTY = "";
    private Properties properties;
    private Loader loader;

    @Before
    public void init() {
        properties = Mockito.mock(Properties.class);
        Mockito.when(properties.getProperty("source")).thenReturn(EMPTY);
        Mockito.when(properties.getProperty("words")).thenReturn(EMPTY);
        Mockito.when(properties.getProperty("dir")).thenReturn(EMPTY);
        loader = new LoaderImpl(properties);
    }


    @Test
    public void loadSourcesPositiveTest() {
        //todo test with PowerMock
    }

    @Test
    public void loadSourcesNegativeTest() {
        loader.loadSources();
        Mockito.doThrow(IOException.class);
    }

    @Test
    public void loadWordsPositiveTest() {
        //todo test with PowerMock
    }

    @Test
    public void loadWordsNegativeTest() {
        loader.loadWords();
        Mockito.doThrow(IOException.class);
    }

    @Test
    public void getOutDirTest() {
        assertEquals(EMPTY, loader.getOutDir());
    }
}