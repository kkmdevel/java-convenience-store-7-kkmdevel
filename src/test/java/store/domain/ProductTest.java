package store.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @ParameterizedTest
    @CsvSource({
            "'콜라', 1000, 10, '탄산2+1'",
            "'사이다', 1200, 5, ''"
    })
    void testProductCreation(String name, int price, int quantity, String promotion) {
        if (promotion.isEmpty()) promotion = null;
        Product product = Product.of(name, price, quantity, promotion);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertEquals(promotion, product.getPromotion());
    }
}
