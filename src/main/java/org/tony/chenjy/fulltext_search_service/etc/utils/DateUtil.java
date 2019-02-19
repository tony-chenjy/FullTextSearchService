package org.tony.chenjy.fulltext_search_service.etc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String PATTERN_TIMESTAMP = "yyyy-MM-dd HH:mm:ss,SSS";

    public static final Date parseWithPattern(String str, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date datetime = sdf.parse(str);
            return datetime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Date parseTimeStamp(String str) {
        return parseWithPattern(str, PATTERN_TIMESTAMP);
    }
}
