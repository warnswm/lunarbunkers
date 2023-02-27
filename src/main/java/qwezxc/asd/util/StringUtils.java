/*
 * Decompiled with CFR 0.150.
 */
package qwezxc.asd.util;

public class StringUtils {
    public static String addLineBreaks(String string, int afterChars) {
        StringBuilder sb = new StringBuilder(string);
        int i = 0;
        while ((i = sb.indexOf(" ", i + afterChars)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        return sb.toString();
    }

    public static String declineNoun(String noun, int numeral) {
        if (noun.equals("Дубовая калитка")) {
            if (numeral == 1) {
                return "Дубовую калитку";
            } else if (numeral >= 2 && numeral <= 4) {
                return "Дубовые калитки";
            } else {
                return "Дубовых калиток";
            }
        } else if (noun.equals("Лестница")) { // добавляем склонение для слова "лестница"
            if (numeral == 1) {
                return "Лестницу";
            } else if (numeral >= 2 && numeral <= 4) {
                return "Лестницы";
            } else {
                return "Лестниц";
            }
        } else if (noun.equals("уголь")) {
            if (numeral == 1) {
                return "уголь";
            } else {
                return "угля";
            }
        }
        if (noun.equals("железный слиток")) {
            if (numeral == 1) {
                return "железный слиток";
            } else {
                return "железных слитков";
            }
        }
        if (noun.equals("золотой слиток")) {
            if (numeral == 1) {
                return "золотой слиток";
            } else {
                return "золотых слитков";
            }
        } else { // возвращаем исходное слово, если не знаем его склонение
            if (numeral == 1) {
                return noun;
            } else if (numeral >= 2 && numeral <= 4) {
                return noun + "а";
            } else {
                return noun + "ов";
            }
        }
    }

}

