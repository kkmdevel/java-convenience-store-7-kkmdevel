package store.domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    private Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(String name, int price, int quantity, String promotion) {
        return new Product(name, price, quantity, promotion);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }

    public boolean hasName(String productName) {
        return this.name.equals(productName);
    }

    public int availableQuantity() {
        return this.quantity;
    }

    public boolean hasPromotion() {
        return this.promotion != null && !this.promotion.equals("null");
    }

    public int calculatePrice(int requestedQuantity) {
        return price * requestedQuantity;
    }

    public void reduceStock(int usedStock) {
            this.quantity -= usedStock;
    }

}
