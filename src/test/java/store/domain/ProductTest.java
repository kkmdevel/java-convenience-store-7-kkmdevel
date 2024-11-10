package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    @DisplayName("Product 객체 생성 및 필드 검증 테스트")
    void testProductCreationAndFields() {
        Product product = Product.of("콜라", 1000, 10, "탄산2+1");

        assertTrue(product.hasName("콜라"));
        assertEquals(1000, product.getPrice());
        assertEquals(10, product.availableQuantity());
        assertEquals("탄산2+1", product.getPromotion());
    }

    @Test
    @DisplayName("상품의 프로모션 여부 확인 테스트")
    void testHasPromotion() {
        Product productWithPromotion = Product.of("콜라", 1000, 10, "탄산2+1");
        Product productWithoutPromotion = Product.of("사이다", 1200, 5, "null");

        assertTrue(productWithPromotion.hasPromotion());
        assertFalse(productWithoutPromotion.hasPromotion());
    }

    @Test
    @DisplayName("재고 차감 기능 테스트")
    void testReduceStock() {
        Product product = Product.of("콜라", 1000, 10, "탄산2+1");
        product.reduceStock(3);

        assertEquals(7, product.availableQuantity());
    }

    @Test
    @DisplayName("요청한 수량에 따른 가격 계산 테스트")
    void testCalculatePrice() {
        Product product = Product.of("콜라", 1000, 10, "탄산2+1");

        assertEquals(3000, product.calculatePrice(3));
        assertEquals(5000, product.calculatePrice(5));
    }
}
