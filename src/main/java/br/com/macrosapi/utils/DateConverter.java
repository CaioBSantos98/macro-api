package br.com.macrosapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static LocalDate convertStringToLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid format date: " + dateString, e);
        }
    }
}
