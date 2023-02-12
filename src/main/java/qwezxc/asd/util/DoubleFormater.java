/*
 * Decompiled with CFR 0.150.
 */
package qwezxc.asd.util;

public class DoubleFormater {
    public static double maxDecimalPlaces(double number, int decimalPlaces) {
        return (double)((int)(number * Math.pow(10.0, decimalPlaces))) / Math.pow(10.0, decimalPlaces);
    }
}

