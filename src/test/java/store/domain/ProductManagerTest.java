package store.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductManagerTest {

    private static ProductManager productManager;
    private static List<Product> products;

    @BeforeAll
    static void setUp() {
        productManager = ProductManager.load();
        products = productManager.getProducts();
    }

    @Test
    @DisplayName("ProductManager는 products.md에서 데이터를 로드하여 Product 목록을 생성한다.")
    void loadProductsFromFile() {
        assertThat(products).isNotEmpty();
        assertAll(
                () -> assertThat(products.getFirst().hasName("콜라")).isTrue(),
                () -> assertThat(products.getFirst().calculatePrice(1)).isEqualTo(1000),
                () -> assertThat(products.getFirst().availableQuantity()).isEqualTo(10),
                () -> assertThat(products.getFirst().getPromotion()).isEqualTo("탄산2+1")
        );
    }

    @Test
    @DisplayName("요청한 수량에 맞는 상품 가격을 정확히 계산한다.")
    void calculatePrice() {
        int price = productManager.calculatePrice("콜라", 5);
        assertThat(price).isEqualTo(1000 * 5);
    }

    @Test
    @DisplayName("특정 상품의 프로모션 정보를 반환한다.")
    void findPromotion() {
        String promotion = productManager.findPromotion("콜라");
        assertThat(promotion).isEqualTo("탄산2+1");
    }

    @Test
    @DisplayName("프로모션 재고와 일반 재고를 정확히 반환한다.")
    void getStockInformation() {
        int promotionStock = productManager.getPromotionStock("콜라");
        int regularStock = productManager.getRegularStock("콜라");

        assertThat(promotionStock).isEqualTo(10);
        assertThat(regularStock).isEqualTo(10);
    }

    @Test
    @DisplayName("ProductManager는 프로모션 재고와 일반 재고를 정확히 차감한다.")
    void reduceStock() {
        int initialPromotionStock = productManager.getPromotionStock("콜라");
        int initialRegularStock = productManager.getRegularStock("콜라");

        productManager.reducePromotionStock("콜라", 3);
        productManager.reduceRegularStock("콜라", 2);

        assertThat(productManager.getPromotionStock("콜라")).isEqualTo(initialPromotionStock - 3);
        assertThat(productManager.getRegularStock("콜라")).isEqualTo(initialRegularStock - 2);
    }
}
