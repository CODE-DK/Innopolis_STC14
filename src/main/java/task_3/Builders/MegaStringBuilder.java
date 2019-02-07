package task_3.Builders;

import java.util.Random;

/**
 * класс служит для построения предложений из входного массива слов.
 *
 * @author Komovskiy Dmitriy
 * @version 18.01.19 v1.0
 */
class MegaStringBuilder {

    private final String[] words; //
    private static final char[] SYMBOLS = {'.', '!', '?'};
    private final int lengthOfString;
    private final Random random;

    /**
     * Конструктор инициализации списка слов и длины предложения
     *
     * @param words исходный список слов
     * @param probability вероятность появления слова в предложении
     */
    private int probability;

    MegaStringBuilder(String[] words, int probability) {
        if (probability > 100 || probability < 0) {
            throw new IllegalArgumentException("вероятнось можеть быть в диапазоне 0 - 100");
        }
        this.words = words;
        this.probability = probability;
        random = new Random();
        lengthOfString = 1 + random.nextInt(15); // длина предложения 1-15 слов
    }

    /**
     * Метод для построения нового предложения
     * Выходной параметр - строка, состоящая из {@code lengthOfString} слов
     * разделенных знаком {@code char = ' ' }.
     *
     * @return строка - предложение
     */
    String buildNewString() {

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < lengthOfString; i++) {

            int symbolProbability = 1 + random.nextInt(100); // вероятность появления символа после слова в предложении 1 - 100%
            int initProbability = random.nextInt(100); // вероятность того, что слово не появится в строке

            String s = getRandomWord();

            while (probability < initProbability) {
                s = getRandomWord();
                initProbability = random.nextInt(100);
            }

            if (i == 0) { // если слово в предложении первое
                out.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)); // то начинается с строчной буквы
            } else {
                out.append(getRandomWord()); //иначе просто слово
            }

            if (symbolProbability > 50 && i < lengthOfString - 1) { // добавляем символ после каждого слова кроме последнего
                out.append(", ");
            } else if (i < lengthOfString - 1) {
                out.append(' ');
            }
        }

        out.append(getRandomSymbol()); // и ставим символ окончания предложения

        return out.toString();
    }

    private char getRandomSymbol() {
        return SYMBOLS[random.nextInt(SYMBOLS.length)]; // рандомный символ окончания предложения
    }

    private String getRandomWord() {
        return words[random.nextInt(words.length)]; // рандомное слово из списка
    }
}
