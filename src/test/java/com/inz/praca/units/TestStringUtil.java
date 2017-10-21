package com.inz.praca.units;

public class TestStringUtil {

    public static String createStringWithLength(int length) {
        StringBuilder string = new StringBuilder();
        for (int index = 0; index < length; index++) {
            string.append("a");
        }
        return string.toString();
    }
}
