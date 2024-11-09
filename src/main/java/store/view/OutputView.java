package store.view;

import java.text.NumberFormat;
import store.domain.Product;
import store.domain.ProductManager;
import static store.view.OutputMessage.*;

import java.util.List;

public class OutputView {

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
        String priceMessage = NumberFormat.getInstance().format(product.getPrice());
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
        return Integer.toString(product.availableQuantity());
    }

    private static String formatPromotionMessage(Product product) {
        if (product.getPromotion().equals("null")) {
            return "";
        }
        return product.getPromotion();
    }

    public static void printOrderPrefix(){
        System.out.println();
        System.out.println(PURCHASE_PREFIX);
    }

    public static void askForReceivePromotion(String name) {
        System.out.println();
        System.out.println(CAN_RECEIVE_BONUS.getFormattedMessage(name));
    }

    public static void askForAdjustment(String productName, Integer quantity) {
        System.out.println();
        System.out.println(ADJUSTMENT_QUANTITY.getFormattedMessage(productName,quantity));
    }
}
