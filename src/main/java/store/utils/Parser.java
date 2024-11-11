package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static store.exception.ExceptionMessage.ERROR_STOCK_LIMIT;
import static store.exception.ExceptionMessage.ERROR_INVALID_INPUT;
import static store.exception.ExceptionMessage.ERROR_INVALID_FORMAT;

public final class Parser {

    private static final String PATTERN = "^\\[[a-zA-Z가-힣0-9]+-\\d+](,\\s*\\[[a-zA-Z가-힣0-9]+-\\d+])*$";

    private Parser() {
    }

    public static int parseStringToInt(String input) {
        validateIsEmpty(input);
        return parseInt(input);
    }

    private static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(ERROR_STOCK_LIMIT.toString());
        }
    }

    public static LocalDate parseStringToLocalDate(String input) {
        validateIsEmpty(input);
        return parseLocalDate(input);
    }

    private static LocalDate parseLocalDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(ERROR_INVALID_INPUT.toString());
        }
    }

    public static Map<String, Integer> parseOrderItem(String input) {
        validateIsEmpty(input);
        validateBracketsFormat(input);
        return extractOrderItems(input);
    }

    private static Map<String, Integer> extractOrderItems(String input) {
        Map<String, Integer> orderItems = new LinkedHashMap<>();
        List<String> products = Arrays.asList(input.split(",\\s*"));
        products.forEach(product -> addProduct(orderItems, product));
        return orderItems;
    }

    private static void addProduct(Map<String, Integer> orderItems, String product) {
        List<String> parts = parseAndValidateProduct(product);
        String name = parts.get(0).trim();
        int quantity = parseStringToInt(parts.get(1).trim());
        validateDuplicateName(orderItems, name);
        orderItems.put(name, quantity);
    }

    private static void validateBracketsFormat(String input) {
        if (!input.matches(PATTERN)) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT.toString());
        }
    }

    private static List<String> parseAndValidateProduct(String product) {
        String cleanedProduct = cleanBrackets(product);
        List<String> parts = Arrays.asList(cleanedProduct.split("-"));
        if (parts.size() != 2) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT.toString());
        }
        return parts;
    }

    private static String cleanBrackets(String product) {
        return product.trim().replaceAll("^\\[|]$", "");
    }

    private static void validateDuplicateName(Map<String, Integer> orderItems, String name) {
        if (orderItems.containsKey(name)) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT.toString());
        }
    }

    public static boolean parseYesNo(String input) {
        if (input.equalsIgnoreCase("Y")) return true;
        if (input.equalsIgnoreCase("N")) return false;
        throw new IllegalArgumentException(ERROR_INVALID_INPUT.toString());
    }

    private static void validateIsEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT.toString());
        }
    }
}
