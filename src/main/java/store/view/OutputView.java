package store.view;

import java.text.NumberFormat;
import java.util.Map;
import store.domain.Product;
import store.domain.ProductManager;
import static store.view.OutputMessage.*;

import java.util.List;

public class OutputView {

    private static String formatPrice(int price) {
        return NumberFormat.getInstance().format(price);
    }

    public static void printBlankLine() {
        System.out.println();
    }

    public static void printWelcomeAndAllProducts(ProductManager productManager) {
        printBlankLine();
        System.out.println(WELCOME.getMessage());
        System.out.println(ALL_PRODUCTS_PREFIX.getMessage());
        printBlankLine();

        List<Product> products = productManager.getProducts();

        for (Product product : products) {
            System.out.println(formatProductMessage(product));
        }
        printBlankLine();
    }

    private static String formatProductMessage(Product product) {
        String priceMessage = formatPrice(product.getPrice());
        String quantityMessage = formatQuantityMessage(product);
        String promotionMessage = formatPromotionMessage(product);
        return PRODUCTS.getFormattedMessage(
                product.getName(),
                priceMessage,
                quantityMessage,
                promotionMessage);
    }

    private static String formatQuantityMessage(Product product) {
        if (product.availableQuantity() == 0) {
            return "재고 없음";
        }
        return product.availableQuantity() + "개";
    }

    private static String formatPromotionMessage(Product product) {
        if (product.getPromotion().equals("null")) {
            return "";
        }
        return product.getPromotion();
    }

    public static void printOrderPrefix() {
        printBlankLine();
        System.out.println(PURCHASE_PREFIX.getMessage());
        printBlankLine();
    }

    public static void askForReceivePromotion(String name) {
        printBlankLine();
        System.out.println(CAN_RECEIVE_BONUS.getFormattedMessage(name));
        printBlankLine();
    }

    public static void askForAdjustment(String productName, Integer quantity) {
        printBlankLine();
        System.out.println(ADJUSTMENT_QUANTITY.getFormattedMessage(productName, quantity));
        printBlankLine();
    }

    public static void askForMembershipDiscount() {
        printBlankLine();
        System.out.println(RECEIVE_MEMBERSHIP_DISCOUNT.getMessage());
        printBlankLine();
    }

    public static void printOriginalPrice(Map<String, Integer> purchasedItems, List<Integer> totalPrices) {
        printBlankLine();
        System.out.println(START_RECEIPT.getMessage());
        System.out.println(ORIGINAL_PRICE_PREFIX.getMessage());
        printBlankLine();
        int index = 0;
        for (String name : purchasedItems.keySet()) {
            int quantity = purchasedItems.get(name);
            int totalPrice = totalPrices.get(index++);
            System.out.println(PURCHASED_ITEMS.getFormattedMessage(name, quantity, formatPrice(totalPrice)));
        }
        printBlankLine();
    }

    public static void printBonusItems(Map<String, Integer> bonusMap) {
        printBlankLine();
        System.out.println(BONUS_ITEMS_PREFIX.getMessage());
        bonusMap.forEach((name, bonusQuantity) -> System.out.println(BONUS_ITEMS.getFormattedMessage(name, bonusQuantity)));
        printBlankLine();
    }

    public static void printTotalPrice(int totalItemQuantity, int totalPriceSum) {
        printBlankLine();
        System.out.println(CALCULATION_DISCOUNT_PREFIX.getMessage());
        System.out.println(TOTAL_PRICE.getFormattedMessage(totalItemQuantity, formatPrice(totalPriceSum)));
        printBlankLine();
    }

    public static void printPromotionDiscountPrices(int totalPromotionDiscountAmount) {
        printBlankLine();
        System.out.println(PROMOTION_DISCOUNT.getFormattedMessage(formatPrice(totalPromotionDiscountAmount)));
        printBlankLine();
    }

    public static void printMembershipDiscount(int membershipDiscountAmount) {
        printBlankLine();
        System.out.println(MEMBERSHIP_DISCOUNT.getFormattedMessage(formatPrice(membershipDiscountAmount)));
        printBlankLine();
    }

    public static void printPayableAmount(int payableAmount) {
        printBlankLine();
        System.out.println(PAYABLE_AMOUNT.getFormattedMessage(formatPrice(payableAmount)));
        printBlankLine();
    }

    public static void askToContinueShopping() {
        printBlankLine();
        System.out.println(CONTINUE_SHOPPING.getMessage());
        printBlankLine();
    }

    public static void printErrorMessage(String errorMessage) {
        printBlankLine();
        System.out.println(errorMessage);
        printBlankLine();
    }
}
