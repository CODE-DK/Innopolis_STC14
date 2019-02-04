package task_9.IO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

public class SReaderTest {

    private SReader reader;

    @Before
    public void setUp() throws Exception {
        String file = "";
        String[] words = {"1", "string", "3"};
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        reader = new SReader(file, words, queue);
    }

    @Test (expected = FileNotFoundException.class)
    public void initEmptyPathToFile() throws FileNotFoundException {
        String file = "";
        Assert.assertEquals("несуществующий путь", BufferedReader.class, reader.initReader(file));
    }

    @Test (expected = NullPointerException.class)
    public void initNullFilePath() throws FileNotFoundException {
        String file = null;
        Assert.assertEquals("передан null", BufferedReader.class, reader.initReader(file));
    }

    @Test
    public void initReader() throws FileNotFoundException {
        String file = "https://www.baeldung.com/ant-maven-gradle";
        Assert.assertEquals("передан url", BufferedReader.class, reader.initReader(file));
    }

    @Test
    public void parseText() {

    }

    @Test
    public void findRegEx() {
        String sentence = "Some test string with different words";
        String[] words = {"1", "string", "3"};
        Queue mockQueue = Mockito.mock(LinkedBlockingDeque.class);
        reader = new SReader(sentence, words, mockQueue);
        StringBuilder builder = new StringBuilder(sentence);
        reader.findRegEx(builder);
        SReader reader = Mockito.mock(SReader.class);
        Mockito.verify(reader, times(1)).addToQueue(anyString());
    }

    @Test
    public void addToQueue(){
        String sentence = "123";
        reader.getQueue().add(sentence);
        Assert.assertEquals("добавляем в очередь", sentence, reader.getQueue().poll());
    }

    @Test
    public void checkQueue(){
        String sentence1 = "123";
        String sentence2 = "456";
        String sentence3 = "789";
        reader.getQueue().add(sentence1);
        reader.getQueue().add(sentence2);
        reader.getQueue().add(sentence3);
        Assert.assertEquals("добавляем порядок вставки снятия", sentence1, reader.getQueue().poll());
        Assert.assertEquals("добавляем порядок вставки снятия", sentence2, reader.getQueue().poll());
        Assert.assertEquals("добавляем порядок вставки снятия", sentence3, reader.getQueue().poll());
    }

    @Test
    public void getQueue() {
        Assert.assertEquals("getter", Queue.class, reader.getQueue());
    }

    @Test
    public void setQueue() {
        Queue<String> queue = new LinkedBlockingDeque<>();
        reader.setQueue(queue);
        Assert.assertEquals("setter", queue, reader.getQueue());
    }

    @Test
    public void findSentenceWithEmptySet() {
        String sentence = "123";
        String[] strings = {"карл", "у", "клары", "украл", "кораллы"};
        Assert.assertFalse("проверка на входжение : нет сравнений", reader.findSentence(sentence, strings));
    }

    @Test
    public void findSentence() {
        String sentence = "1 7 9";
        String[] strings = {"1", "2", "3", "4", "5"};
        Assert.assertTrue("проверка на входжение : пересечение", reader.findSentence(sentence, strings));
    }

    @Test (expected = NullPointerException.class)
    public void findWordsInNullSentence() {
        String[] strings = {"1", "2", "3", "4", "5"};
        Assert.assertTrue("проверка на входжение : пересечение", reader.findSentence(null, strings));
    }

    @Test
    public void parseNormalSentence() {
        String sentence = "Карл у Клары украл кораллы";
        String[] strings = {"карл", "у", "клары", "украл", "кораллы"};
        Assert.assertArrayEquals("парсим предложение", strings, reader.parseSentence(sentence));
    }

    @Test
    public void parseNormalEnglishSentence() {
        String sentence = "SoMe Is, We.OOooO";
        String[] strings = {"some", "is", "weooooo"};
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