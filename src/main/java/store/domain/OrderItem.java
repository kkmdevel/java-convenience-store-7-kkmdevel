package store.domain;

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
            throw new IllegalArgumentException();
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    public int getRequestedQuantity() {
        return quantity;
    }

}
