package task_3.Builders;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MegaStringBuilderTest {

    private MegaStringBuilder msb;

    @Before
    public void setUp() {
        String[] testArray = {"some", "test", "array"};
        int probability = 100;
        msb = new MegaStringBuilder(testArray, probability);
    }

    @Test
    public void buildNewString() {
        for (int i = 0; i < 1000; i++) {
            if (!msb.buildNewString().matches("^[A-Z].+?[.|?|!]")){
                fail();
            }
        }
    }
}