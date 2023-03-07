package qwezxc.asd.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void msgPlayer(Player player, String... strings) {
        for (String string : strings) {
            player.sendMessage(color(string));
        }
    }

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


    public String getTimeFormat(int _time) {
        int time = _time;
        boolean plus = time < 0;
        if (time < 0) time = Math.abs(time);
        String first = "";
        if (!plus) first = "-";
        int sec = 0;
        if (time % 60 >= 10) sec = time % 60;

        return first + time/60 + ":" + sec;
    }

}
