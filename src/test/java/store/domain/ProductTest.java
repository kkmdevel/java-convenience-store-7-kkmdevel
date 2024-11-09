package store.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @ParameterizedTest
    @CsvSource({
            "'콜라', 1000, 10, '탄산2+1'",
            "'사이다', 1200, 5, 'null'"
    })
    void testProductCreation(String name, int price, int quantity, String promotion) {
        Product product = Product.of(name, price, quantity, promotion);

        assertTrue(product.hasName(name));
        assertEquals(price, product.calculatePrice(1));
        assertEquals(quantity, product.availableQuantity());
        assertEquals(promotion, product.getPromotion());
    }
}
