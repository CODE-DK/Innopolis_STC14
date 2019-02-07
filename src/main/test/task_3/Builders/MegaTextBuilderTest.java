package task_3.Builders;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MegaTextBuilderTest {

    private MegaTextBuilder mtb;

    @Before
    public void setUp(){
        String[] testArray = {"some", "test", "array"};
        int probability = 100;
        mtb = new MegaTextBuilder(testArray, probability);
    }

    @Test
    public void buildNewText() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(mtb.buildNewText().matches("^[\\t].+[.|?|!][\\n]"));
        }
    }
}