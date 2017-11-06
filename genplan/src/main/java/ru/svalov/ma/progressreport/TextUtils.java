package ru.svalov.ma.progressreport;

import org.springframework.util.StringUtils;

public class TextUtils {

    private static final String NL = System.getProperty("line.separator");

    private TextUtils() {
    }

    public static void append(StringBuilder builder, String text) {
        if (!StringUtils.isEmpty(text)) {
            builder.append(text);
            builder.append(NL);
        }
    }

}
