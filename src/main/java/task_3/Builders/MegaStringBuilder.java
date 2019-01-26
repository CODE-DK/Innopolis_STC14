package task_3.Builders;

import java.util.Random;

/**
 * класс служит для построения предложений из входного массива слов.
 *
 * @author Komovskiy Dmitriy
 * @version 18.01.19 v1.0
 */
class MegaStringBuilder {

    /**
     * исходный список слов
     */
    private final String[] words; //

    /**
     * массив символов окончания предложения
     */
    private static final char[] SYMBOLS = {'.', '!', '?'};

    /**
     * длина предложения
     */
    private final int lengthOfString; // предложение состоит 1-15 слов

    /**
     * рандомайзер
     */
    private final Random random;

    /**
     * Конструктор инициализации списка слов и длины предложения
     *
     * @param words исходный список слов
     */
    private int probability;

    MegaStringBuilder(String[] words, int probability) {
        this.words = words;
        this.probability = probability;
        random = new Random();
        lengthOfString = 1 + random.nextInt(15);
    }

    /**
     * Метод для построения нового предложения
     * Выходной параметр - строка, состоящая из {@code lrngthOfString} слов
     * разделенных знаком {@code char = ' ' }.
     *
     * @return строка - предложение
     */
    String buildNewString() {

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < lengthOfString; i++) {

            int v = 1 + random.nextInt(100); // вероятность появления символа после слова в предложении 1 - 100%
            int x = random.nextInt(100); // вероятность того, что слово не появится в строке

            String s = getRandomWord();

            while (probability < x) {
                s = getRandomWord();
                x = random.nextInt(100);
            }

            if (i == 0) { // если слово в предложении первое
                out.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)); // то начинается с строчной буквы
            } else {
                out.append(getRandomWord()); //иначе просто слово
            }

            if (v > 50 && i < lengthOfString - 1) { // добавляем символ после каждого слова кроме последнего
                out.append(", ");
            } else if (i < lengthOfString - 1) {
                out.append(' ');
            }
        }

        out.append(getRandomSymbol()); // и ставим символ окончания предложения

        return out.toString();
    }

    /**
     * Метод возвращает произвольный символ из {@code SYMBOLS} массива
     *
     * @return символ типа {@link java.lang.Character}
     */
    private char getRandomSymbol() {
        return SYMBOLS[random.nextInt(SYMBOLS.length)]; // рандомный символ окончания предложения
    }

    /**
     * Метод возвращает произвольное слово из исходного массива {@code words}
     * типа {@link java.lang.String}
     *
     * @return строка {@link java.lang.String} из массива {@code words}
     */
    private String getRandomWord() {
        return words[random.nextInt(words.length)]; // рандомное слово из списка
    }
}
