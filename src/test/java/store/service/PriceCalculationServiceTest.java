package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculationServiceTest {

    private PriceCalculationService priceCalculationService;
    private OrderItemManager orderItemManager;

    @BeforeEach
    void setUp() {
        ProductManager productManager = ProductManager.load();
        priceCalculationService = new PriceCalculationService(productManager);

        orderItemManager = OrderItemManager.from(Map.of(
                "콜라", 5,
                "사이다", 3
        ));
    }

    @Test
    @DisplayName("총 가격 계산 테스트")
    void calculateTotalPriceTest() {
        List<Integer> totalPrices = priceCalculationService.calculateTotalPrice(orderItemManager);

        assertThat(totalPrices.get(0)).isEqualTo(1000 * 5);
        assertThat(totalPrices.get(1)).isEqualTo(1000 * 3);
    }

    @Test
    @DisplayName("프로모션 할인 계산 테스트")
    void calculatePromotionDiscountTest() {
        Map<String, Integer> bonuses = Map.of(
                "콜라", 3,
                "사이다", 2
        );

        int promotionDiscountAmount = priceCalculationService.calculatePromotionDiscount(bonuses);

        assertThat(promotionDiscountAmount).isEqualTo((1000 * 3) + (1000 * 2));
    }
}
