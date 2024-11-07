package store.domain;

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
        return products;
    }
}
