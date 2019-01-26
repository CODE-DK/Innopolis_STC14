package task_3.Builders;

import java.util.Random;

/**
 * @author Komovskiy Dmitriy
 * @version 18.01.2019 v1.0
 */
class MegaTextBuilder {

    private static final int MAX_WORD_NUMBER = 20;
    /**
     * поле класса {@link task_3.Builders.MegaStringBuilder}
     * является генератором случайных предложений
     */
    private final MegaStringBuilder megaStringBuilder;

    /**
     * рандомайзер
     */
    private final Random random;

    /**
     * Конструктор инииализации, входной параметр - массив слов типа {@link java.lang.String}
     *
     * @param words массив слов тип {@link java.lang.String}
     */
    MegaTextBuilder(String[] words, int probability) {
        this.megaStringBuilder = new MegaStringBuilder(words, probability);
        random = new Random();
    }

    /**
     * Метод предназначем для построения абзацев, состоящих из
     * произвольных предложений. Количество предложений в абзаце 1-20
     *
     * @return абзац текста произвольных предложенийю тип {@link java.lang.String}
     */
    String buildNewText() {
        StringBuilder sb = new StringBuilder();

        int textLength = 1 + random.nextInt(MAX_WORD_NUMBER); // количество слов в пердложении 1-20

        for (int i = 0; i < textLength; i++) {

            if (i == 0) {
                sb.append('\t'); // если первое слово в абзаце => начинается с табуляции
            }

            sb.append(megaStringBuilder.buildNewString()); // добавляем строку в абзац

            if (i < textLength - 1) { // добавляем отступ в конце предложения в абаце
                sb.append(' ');
            }
        }
        sb.append('\n'); // возврат коретки с переносом строки в конце абзаца

        return sb.toString();
    }
}
