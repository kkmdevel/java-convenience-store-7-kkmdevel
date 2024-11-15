package store.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionDiscountServiceTest {

    private PromotionDiscountService promotionDiscountService;
    private OrderItemManager orderItemManager;
    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        productManager = ProductManager.load();
        PromotionManager promotionManager = PromotionManager.load();
        promotionDiscountService = new PromotionDiscountService(productManager, promotionManager);

        orderItemManager = OrderItemManager.from(Map.of(
                "콜라", 11,
                "사이다", 4
        ),productManager);
    }

    @Test
    @DisplayName("증정 계산 테스트")
    void calculateBonusesTest() {
        Map<String, Integer> bonuses = promotionDiscountService.calculateBonuses(orderItemManager);

        assertThat(bonuses.get("콜라")).isEqualTo(3);
        assertThat(bonuses.get("사이다")).isEqualTo(1);
    }

    @Test
    @DisplayName("프로모션 재고를 넘을 시 정가 계산 테스트")
    void calculateRegularPricesTest() {
        Map<String, Integer> bonuses = promotionDiscountService.calculateBonuses(orderItemManager);
        Map<String, Integer> regularPrices = promotionDiscountService.calculateRegularPrices(orderItemManager, bonuses);

        assertThat(regularPrices.get("콜라")).isEqualTo(2);
        assertThat(regularPrices.get("사이다")).isEqualTo(0);
    }

    @Test
    @DisplayName("증정을 받을 수 있는 구매 상품 찾기 테스트")
    void findCanReceiveBonusTest() {
        orderItemManager = OrderItemManager.from(Map.of(
                "콜라", 5,
                "사이다", 3,
                "컵라면", 1
        ),productManager);
        List<OrderItem> canReceiveBonusItems = promotionDiscountService.findCanReceiveBonus(orderItemManager);
        assertThat(canReceiveBonusItems).hasSize(1);
        assertThat(canReceiveBonusItems.getFirst().getName()).isEqualTo("콜라");
    }
}
