package task_10;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestClassTest {

    @Test (expected = OutOfMemoryError.class)
    public void main() {
        new TestClass().javaHeapSpace();
    }
}