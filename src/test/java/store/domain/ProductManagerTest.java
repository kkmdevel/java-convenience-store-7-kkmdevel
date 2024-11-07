package store.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductManagerTest {

    private static List<Product> products;

    @BeforeAll
    static void setUp() {
        ProductManager productManager = ProductManager.load();
        products = productManager.getProducts();
    }

    @Test
    @DisplayName("ProductManager는 products.md에서 데이터를 로드하여 Product 목록을 생성한다.")
    void loadProductsFromFile() {
        assertThat(products).isNotEmpty();
        assertAll(
                () -> assertThat(products.getFirst().getName()).isEqualTo("콜라"),
                () -> assertThat(products.getFirst().getPrice()).isEqualTo(1000),
                () -> assertThat(products.getFirst().getQuantity()).isEqualTo(10),
                () -> assertThat(products.getFirst().getPromotion()).isEqualTo("탄산2+1")
                );
    }

}
