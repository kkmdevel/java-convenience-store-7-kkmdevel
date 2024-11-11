package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Parser {

    private static final String PATTERN = "^\\[[a-zA-Z가-힣0-9]+-\\d+](,\\s*\\[[a-zA-Z가-힣0-9]+-\\d+])*$";
    private static final String ERROR_INVALID_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private static final String ERROR_STOCK_LIMIT = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    private static final String ERROR_INVALID_INPUT = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";

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
            throw new IllegalArgumentException(ERROR_STOCK_LIMIT);
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
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
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
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }
    }

    private static List<String> parseAndValidateProduct(String product) {
        String cleanedProduct = cleanBrackets(product);
        List<String> parts = Arrays.asList(cleanedProduct.split("-"));
        if (parts.size() != 2) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }
        return parts;
    }

    private static String cleanBrackets(String product) {
        return product.trim().replaceAll("^\\[|]$", "");
    }

    private static void validateDuplicateName(Map<String, Integer> orderItems, String name) {
        if (orderItems.containsKey(name)) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }
    }

    public static boolean parseYesNo(String input) {
        if (input.equalsIgnoreCase("Y")) return true;
        if (input.equalsIgnoreCase("N")) return false;
        throw new IllegalArgumentException(ERROR_INVALID_INPUT);
    }

    private static void validateIsEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }
    }
}
