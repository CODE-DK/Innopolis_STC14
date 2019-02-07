package task_3.Parsers;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class MegaUrlWordsParserTest {

    private MegaUrlWordsParser parser;

    @Before
    public void setUp() {
        parser = new MegaUrlWordsParser();
    }

    @Test
    public void parse() throws IOException {
        String source = "https://ru.wikipedia.org/wiki/Google_(%D0%BA%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D1%8F)";
        String[] result = parser.parse(source);
        assertTrue(result.length <= 1000);
        HashSet<String> set = new HashSet<>(Arrays.asList(result));
        assertEquals(set.size(), result.length);
    }

    @Test (expected = IllegalArgumentException.class)
    public void parseNegativeTest() throws IOException {
        String source = "someNotValidUrl";
        parser.parse(source);
    }
}