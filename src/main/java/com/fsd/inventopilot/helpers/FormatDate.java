package com.fsd.inventopilot.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    public static String formatTimestamp(Date date) {
        SimpleDateFormat timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return timestamp.format(date);
    }

    public static Date parseTimestamp(String timestampStr) throws ParseException {
        SimpleDateFormat timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return timestamp.parse(timestampStr);
    }
}
