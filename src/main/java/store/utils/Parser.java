package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, Integer> parseOrderItem(String input) {
        validateIsEmpty(input);
        Map<String, Integer> orderItems = new HashMap<>();
        Arrays.stream(input.split(","))
                .forEach(product -> {
                    List<String> parts = List.of(product.trim().replaceAll("[\\[\\]]", "").split("-"));
                    orderItems.put(parts.get(0).trim(), Parser.parseStringToInt(parts.get(1).trim()));
                });
        return orderItems;
    }


    private static void validateIsEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
