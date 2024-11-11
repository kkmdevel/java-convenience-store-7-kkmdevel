package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public static LocalDate parseStringToLocalDate(String input) {
        validateIsEmpty(input);
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public static Map<String, Integer> parseOrderItem(String input) {
        validateIsEmpty(input);
        validateBracketsFormat(input);

        Map<String, Integer> orderItems = new LinkedHashMap<>();
        Arrays.asList(input.split(",")).forEach(product -> {
            List<String> parts = parseAndValidateProduct(product);
            String name = parts.get(0).trim();
            int quantity = parseStringToInt(parts.get(1).trim());
            orderItems.put(name, quantity);
        });

        return orderItems;
    }

    private static void validateBracketsFormat(String input) {
        if (!input.matches("(\\[.*?],\\s*)*\\[.*?]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static List<String> parseAndValidateProduct(String product) {
        String cleanedProduct = product.trim().replaceAll("[\\[\\]]", "");
        List<String> parts = Arrays.asList(cleanedProduct.split("-"));
        if (parts.size() != 2) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        return parts;
    }

    public static boolean parseYesNo(String input) {
        if (input.equalsIgnoreCase("Y")) return true;
        if (input.equalsIgnoreCase("N")) return false;
        throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }


    private static void validateIsEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
