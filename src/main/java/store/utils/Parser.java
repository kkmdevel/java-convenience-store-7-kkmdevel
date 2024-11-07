package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class Parser {

    private Parser() {
    }

    public static int parseStringToInt(String input) {
        validateIsEmpty(input);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException();
        }
    }

    public static LocalDate parseStringToLocalDate(String input) {
        validateIsEmpty(input);
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateIsEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
