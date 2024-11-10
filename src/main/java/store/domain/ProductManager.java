package store.domain;

import java.util.Collections;
import store.domain.enums.ProductLines;
import store.utils.DataReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.utils.Parser;

public class ProductManager {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private final List<Product> products;

    private ProductManager(List<Product> products) {
        this.products = products;
    }

    public static ProductManager load() {
        List<Product> products = new ArrayList<>();
        for (ProductLines productEnum : ProductLines.values()) {
            DataReader.readLineFromFileData(PRODUCTS_FILE_PATH, productEnum.getLineNumber())
                    .ifPresent(data -> products.add(initializeProduct(data)));
        }
        return new ProductManager(products);
    }

    private static Product initializeProduct(String data) {
        List<String> values = Arrays.asList(data.split(","));
        String name = values.get(0);
        int price = Parser.parseStringToInt(values.get(1));
        int quantity = Parser.parseStringToInt(values.get(2));
        String promotion = values.get(3);
        return Product.of(name, price, quantity, promotion);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public int calculatePrice(String productName, int requestedQuantity) {
        return products.stream()
                .filter(product -> product.hasName(productName))
                .findFirst()
                .map(product -> product.calculatePrice(requestedQuantity))
                .orElse(0);
    }

    public String findPromotion(String productName) {
        return products.stream()
                .filter(product -> product.hasName(productName) && product.hasPromotion())
                .map(Product::getPromotion)
                .findFirst()
                .orElse("");
    }

    public int getPromotionStock(String productName) {
        return products.stream()
                .filter(product -> product.hasName(productName) && product.hasPromotion())
                .mapToInt(Product::availableQuantity)
                .sum();
    }

    public int getRegularStock(String productName) {
        return products.stream()
                .filter(product -> product.hasName(productName) && !product.hasPromotion())
                .mapToInt(Product::availableQuantity)
                .sum();
    }


    public void reducePromotionStock(String productName, int usedPromotionStock) {
        products.stream()
                .filter(product -> product.hasName(productName) && product.hasPromotion())
                .findFirst()
                .ifPresent(product -> product.reduceStock(usedPromotionStock));
    }

    public void reduceRegularStock(String productName, int usedRegularStock) {
        products.stream()
                .filter(product -> product.hasName(productName) && !product.hasPromotion())
                .findFirst()
                .ifPresent(product -> product.reduceStock(usedRegularStock));
    }

}
