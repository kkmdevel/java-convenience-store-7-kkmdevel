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

    public static void printWelcomeAndAllProducts(ProductManager productManager) {
        System.out.println(WELCOME.getMessage());
        System.out.println(ALL_PRODUCTS_PREFIX.getMessage());
        System.out.println();

        List<Product> products = productManager.getProducts();

        for (Product product : products) {
            System.out.println(formatProductMessage(product));
        }
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
        if(product.availableQuantity()==0){
            return "재고 없음";
        }
        return product.availableQuantity() +"개";
    }

    private static String formatPromotionMessage(Product product) {
        if (product.getPromotion().equals("null")) {
            return "";
        }
        return product.getPromotion();
    }

    public static void printOrderPrefix(){
        System.out.println();
        System.out.println(PURCHASE_PREFIX.getMessage());
    }

    public static void askForReceivePromotion(String name) {
        System.out.println();
        System.out.println(CAN_RECEIVE_BONUS.getFormattedMessage(name));
    }

    public static void askForAdjustment(String productName, Integer quantity) {
        System.out.println();
        System.out.println(ADJUSTMENT_QUANTITY.getFormattedMessage(productName,quantity));
    }

    public static void askForMembershipDiscount() {
        System.out.println();
        System.out.println(RECEIVE_MEMBERSHIP_DISCOUNT.getMessage());
    }

    public static void printOriginalPrice(Map<String, Integer> purchasedItems, List<Integer> totalPrices) {
        System.out.println();
        System.out.println(START_RECEIPT.getMessage());
        System.out.println(ORIGINAL_PRICE_PREFIX.getMessage());
        int index = 0;
        for (String name : purchasedItems.keySet()) {
            int quantity = purchasedItems.get(name);
            int totalPrice = totalPrices.get(index++);
            System.out.println(PURCHASED_ITEMS.getFormattedMessage(name, quantity, formatPrice(totalPrice)));
        }
    }

    public static void printBonusItems(Map<String, Integer> bonusMap) {
        System.out.println(BONUS_ITEMS_PREFIX.getMessage());
        bonusMap.forEach((name, bonusQuantity) -> System.out.println(BONUS_ITEMS.getFormattedMessage(name,bonusQuantity)));
    }

    public static void printTotalPrice(int totalItemQuantity, int totalPriceSum) {
        System.out.println(CALCULATION_DISCOUNT_PREFIX.getMessage());
        System.out.println(TOTAL_PRICE.getFormattedMessage(totalItemQuantity,formatPrice(totalPriceSum)));
    }

    public static void printPromotionDiscountPrices(int totalPromotionDiscountAmount) {
        System.out.println(PROMOTION_DISCOUNT.getFormattedMessage(formatPrice(totalPromotionDiscountAmount)));
    }

    public static void printMembershipDiscount(int membershipDiscountAmount) {
        System.out.println(MEMBERSHIP_DISCOUNT.getFormattedMessage(formatPrice(membershipDiscountAmount)));
    }

    public static void printPayableAmount(int payableAmount) {
        System.out.println(PAYABLE_AMOUNT.getFormattedMessage(formatPrice(payableAmount)));
    }

    public static void askToContinueShopping() {
        System.out.println();
        System.out.println(CONTINUE_SHOPPING.getMessage());
    }
}
