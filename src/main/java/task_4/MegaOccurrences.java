package task_4;

import task_4.IO.MultiReader;
import task_4.IO.Occurrences;
import task_4.IO.SingleWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MegaOccurrences implements Occurrences {

    @Override
    public void getOccurrences(String[] sources, String[] words, String res) throws IOException {
        ExecutorService ex = Executors.newFixedThreadPool(10);
        for (String source : sources) {
            ex.submit(new MultiReader(source, new HashSet<>(Arrays.asList(words)), new SingleWriter(res)));
        }
        ex.shutdown();
    }
}
