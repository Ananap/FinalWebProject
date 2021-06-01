package by.panasenko.webproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private static final DateParser instance = new DateParser();

    public static DateParser getInstance() {
        return instance;
    }

    private DateParser() {
    }

    public Date parse(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(date);
    }
}
