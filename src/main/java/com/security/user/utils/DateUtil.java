package com.security.user.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static boolean isValidDate(String date) {
        System.out.println("in is valid date fucntion ");
        if (date == null || !date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }
        try {
            dateFormat.parse(date);
            dateFormat.setLenient(false);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Optional<Long> toUnixTimestamp(String date)  {
        try {
            return Optional.of(dateFormat.parse(date).getTime() / 1000);
        } catch (ParseException e) {
            return Optional.empty();
        }
    }
}