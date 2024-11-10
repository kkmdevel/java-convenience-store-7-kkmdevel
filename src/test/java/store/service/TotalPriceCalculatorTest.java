package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalPriceCalculatorTest {

    private TotalPriceCalculator totalPriceCalculator;
    private OrderItemManager orderItemManager;

    @BeforeEach
    void setUp() {
        ProductManager productManager = ProductManager.load();
        totalPriceCalculator = new TotalPriceCalculator(productManager);

        orderItemManager = OrderItemManager.from(Map.of(
                "콜라", 5,
                "사이다", 3
        ),productManager);
    }

    @Test
    @DisplayName("총 가격 계산 테스트")
    void calculateTotalPriceTest() {
        List<Integer> totalPrices = totalPriceCalculator.calculateTotalPrice(orderItemManager);

        assertThat(totalPrices.get(0)).isEqualTo(1000 * 5);
        assertThat(totalPrices.get(1)).isEqualTo(1000 * 3);
    }

}
