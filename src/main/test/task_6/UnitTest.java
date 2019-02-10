package task_6;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    private Unit testUnit;

    @Before
    public void setUp(){
        testUnit = new Unit();
    }

    @Test
    public void unitEqualsTest() {
        for (int i = 0; i < 100; i++) {
            if (!testUnit.equals(new Unit())){
                fail();
            }
        }
    }

    @Test
    public void unitHashCodeTest() {
        for (int i = 0; i < 100; i++) {
            if (new Unit().hashCode() == testUnit.hashCode()){
                fail();
            }
        }
    }
}