package task_9.IO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SReaderTest {

    private SReader reader;
    private final String TEST = "This is a test sentence.";
    private final String[] POSITIVE_TEST_ARRAY = {"test", "array"};
    private final String[] NEGATIVE_TEST_ARRAY = {"singleton", "array"};
    private Queue<String> queue;

    @Before
    public void setUp() {
        queue = new ConcurrentLinkedQueue<>();
        reader = new SReader(TEST, POSITIVE_TEST_ARRAY, queue);
    }

    @Test (expected = FileNotFoundException.class)
    public void initEmptyPathToFile() throws FileNotFoundException {
        assertEquals("несуществующий путь", BufferedReader.class, reader.initReader(TEST));
    }

    @Test (expected = NullPointerException.class)
    public void initNullFilePath() throws FileNotFoundException {
        assertEquals("передан null", BufferedReader.class, reader.initReader(null));
    }


    @Test
    public void parseText() {

    }

    @Test
    public void PositiveRunTest(){

    }

    @Test
    public void NegativeRunTest(){
        Mockito.doThrow(FileNotFoundException.class);
        reader.run();
    }

    @Test
    public void findRegEx() {
        StringBuilder builder = new StringBuilder(TEST);
        reader.findRegEx(builder);
        assertEquals(TEST, queue.poll());
    }

    @Test
    public void addToQueue(){
        reader.getQueue().add(TEST);
        Assert.assertEquals(TEST, reader.getQueue().poll());
    }

    @Test
    public void getQueue() {
        Assert.assertEquals(queue, reader.getQueue());
    }

    @Test
    public void setQueue() {
        queue = new LinkedBlockingDeque<>();
        reader.setQueue(queue);
        Assert.assertEquals(queue, reader.getQueue());
    }

    @Test
    public void findSentenceNegativeTest() {
        String sentence = "123";
        String[] strings = {"карл", "у", "клары", "украл", "кораллы"};
        Assert.assertFalse(reader.findSentence(sentence, strings));
    }

    @Test
    public void findSentencePositiveTest() {
        String sentence = "1 7 9";
        String[] strings = {"1", "2", "3", "4", "5"};
        Assert.assertTrue(reader.findSentence(sentence, strings));
    }

    @Test (expected = NullPointerException.class)
    public void findWordsInNullSentence() {
        String[] strings = {"1", "2", "3", "4", "5"};
        Assert.assertTrue("проверка на входжение : пересечение", reader.findSentence(null, strings));
    }

    @Test
    public void parseSentencePositiveTest() {
        String sentence = "Карл у Клары украл кораллы";
        String[] strings = {"карл", "у", "клары", "украл", "кораллы"};
        Assert.assertArrayEquals("парсим предложение", strings, reader.parseSentence(sentence));
    }

    @Test
    public void parseEnglishSentencePositiveTest() {
        String sentence = "SoMe Is, We.OOooO";
        String[] strings = {"singleton", "is", "weooooo"};
        Assert.assertArrayEquals("парсим предложение на английском", strings, reader.parseSentence(sentence));
    }

    @Test (expected = NullPointerException.class)
    public void parseNullSentence() {
        assertNull("парсим null", reader.parseSentence(null));
    }

    @Test
    public void removeAllPunctuationFromRussianText() {
        String test = "Добро!победит злО,.";
        Assert.assertEquals("убираем пунктуацию", "Добропобедит злО", reader.removePunctuation(test));
    }

    @Test
    public void removeAllPunctuationFromEnglishText() {
        String test = ".,;';lk";
        Assert.assertEquals("убираем пунктуацию", "lk", reader.removePunctuation(test));
    }
}