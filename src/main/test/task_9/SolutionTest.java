package task_9;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SolutionTest {

    private Solution solution;
    private String[] links;
    private String[] words;
    private String out;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void init() throws IOException {

        String firstSetOfTense =
                "This is a long tense. " +
                        "Creates checking the parse method. " +
                        "It needs be sure that every thing is working correct." +
                        "Some other tense were added." +
                        "Let's try to parse!";
        File one = testFolder.newFile("one.txt");
        try (FileWriter fw = new FileWriter(one)) {
            fw.write(firstSetOfTense);
            fw.flush();
        }

        String secondSetOfTense =
                "This is a long tense. " +
                        "Creates checking the parse method. " +
                        "It needs be sure that every thing is working correct." +
                        "Some other tense were added." +
                        "Let's try to parse!";
        File two = testFolder.newFile("two.txt");
        try (FileWriter fw = new FileWriter(two)) {
            fw.write(secondSetOfTense);
            fw.flush();
        }

        String thirdSetOfTense =
                "This is a long tense. " +
                        "Creates checking the parse method. " +
                        "It needs be sure that every thing is working correct." +
                        "Some other tense were added." +
                        "Let's try to parse!";
        File three = testFolder.newFile("three.txt");
        try (FileWriter fw = new FileWriter(three)) {
            fw.write(thirdSetOfTense);
            fw.flush();
        }


        links = new String[]{
                one.getAbsolutePath(),
                two.getAbsolutePath(),
                three.getAbsolutePath()
        };

        words = new String[]{
                "try"
        };

        File outPath = testFolder.newFile("out.txt");
        out = outPath.getAbsolutePath();
        solution = new Solution();
    }

    @Test
    public void getOccurrences() throws IOException {
        solution.getOccurrences(links, words, out);
        System.out.println(Files.readAllLines(Paths.get(out)));
    }
}