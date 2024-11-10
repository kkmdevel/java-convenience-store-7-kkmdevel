package store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.OrderItemManager;
import store.domain.ProductManager;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalPriceCalculatorTest {

    private TotalPriceCalculator totalPriceCalculator;
    private OrderItemManager orderItemManager;

    @BeforeEach
    void setUp() {
        ProductManager productManager = ProductManager.load();
        totalPriceCalculator = new TotalPriceCalculator(productManager);

        Map<String, Integer> orderMap = new LinkedHashMap<>();
        orderMap.put("콜라", 5);
        orderMap.put("사이다", 3);

        orderItemManager = OrderItemManager.from(orderMap, productManager);
    }

    @Test
    @DisplayName("총 가격 계산 테스트")
    void calculateTotalPriceTest() {
        List<Integer> totalPrices = totalPriceCalculator.calculateTotalPrice(orderItemManager);

        assertThat(totalPrices.get(0)).isEqualTo(1000 * 5);
        assertThat(totalPrices.get(1)).isEqualTo(1000 * 3);
    }

}
