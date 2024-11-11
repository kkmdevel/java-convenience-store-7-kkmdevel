package store.domain;

import static store.exception.ExceptionMessage.ERROR_INVALID_FORMAT;

public class OrderItem {
    private final String name;
    private int quantity;

    private OrderItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static OrderItem of(String name, int quantity) {
        validateName(name);
        validateQuantity(quantity);
        return new OrderItem(name, quantity);
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT.toString());
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT.toString());
        }
    }

    public String getName() {
        return name;
    }

    public int getRequestedQuantity() {
        return quantity;
    }

    public boolean hasName(String itemName) {
        return this.name.equals(itemName);
    }

    public void receivePromotion(){
        this.quantity++;
    }

    public void adjustmentQuantity(int adjustmentQuantity){
        this.quantity -=adjustmentQuantity;
    }

}
