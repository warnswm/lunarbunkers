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
}

