package task_3.Builders;

import java.io.*;

/**
 * класс предназначен для построения текстовых файлов,
 * генерация текста происходит рандомайзером
 *
 * @author Komovskiy Dmitriy
 * @version 18.01.2019 v1.0
 */
public class MegaFileBuilder {

    private static final int KBYTE = 1024;

    /**
     * основной метод класса, на вход получает путь к директории, по которой создает требуемое количество текстовых файлов
     * заданого размера, при этом вероятность вхождения слова в предложение так же задается в параметре
     * метода
     *
     * @param pathToDir      путь к директории для создания файлов
     * @param numberOfFiles  количество файлов, которые необходимо сгенерировать
     * @param sizeInKiloByte размер выходного файла в килобайтах
     * @param words          входной массив слов для генерации в файл
     * @param probability    вероятность вхождения слова в предложение
     */
    public void getFiles(String pathToDir, int numberOfFiles, int sizeInKiloByte, String[] words, int probability) {

        if (probability > 100 || probability < 0) {
            throw new IllegalArgumentException("вероятнось можеть быть в диапазоне 0 - 100");
        }

        MegaTextBuilder mtb = new MegaTextBuilder(words, probability);

        /*
         * проверяем валидность переданного размера файла
         */
        if (sizeInKiloByte > 0) {
            for (int i = 0; i < numberOfFiles; i++) {

                String filePath = pathToDir + "/file_" + i + ".txt"; // генератор имен для файлов
                File file = new File(filePath);

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) { // используем символьный поток для записи в файл

                    /*
                     * локальная переменная, отражает количество свободного
                     * места в файле, записано как число символов из учета:
                     * 1 Kb = 1024 byte
                     */
                    int local = sizeInKiloByte * KBYTE;

                    /*
                     * пока свободное место в файле есть
                     */
                    while (local > 0) {
                        String s = mtb.buildNewText(); // формируем строку
                        if (local > s.length()) { // если в файле достаточн свободного места
                            bw.write(s); // запишем строку в файл
                            local -= s.length(); // вычитаем размер строки из оставшегося места в файле
                        } else break;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("неверный путь к файлам = " + e);
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    System.out.println("запись в файл не возможна = " + e);
                }
            }
        }
    }
}
