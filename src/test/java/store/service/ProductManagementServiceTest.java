package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.OrderItemManager;
import store.domain.ProductManager;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductManagementServiceTest {

    private ProductManagementService productManagementService;
    private ProductManager productManager;
    private OrderItemManager orderItemManager;

    @BeforeEach
    void setUp() {
        productManager = ProductManager.load();
        productManagementService = new ProductManagementService(productManager);

        orderItemManager = OrderItemManager.from(Map.of(
                "콜라", 15
        ));
    }

    @Test
    @DisplayName("재고 업데이트 테스트 - 프로모션 재고 초과 시 일반 재고에서 차감")
    void updateStockWhenExceedingPromotionStockTest() {
        int initialPromotionStock = productManager.getPromotionStock("콜라");
        int initialRegularStock = productManager.getRegularStock("콜라");

        productManagementService.updateStock(orderItemManager);

        int usedPromotionStock = Math.min(initialPromotionStock, 15);
        int remainingQuantity = 15 - usedPromotionStock;

        assertThat(productManager.getPromotionStock("콜라")).isEqualTo(initialPromotionStock - usedPromotionStock);
        assertThat(productManager.getRegularStock("콜라")).isEqualTo(initialRegularStock - remainingQuantity);
    }
}
