package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemManagerTest {

    @Test
    @DisplayName("OrderItemManager 객체 생성 테스트 - 올바른 입력")
    void createOrderItemManager_ValidInput() {
        Map<String, Integer> orderMap = Map.of("콜라", 5, "사이다", 3);
        OrderItemManager orderItemManager = OrderItemManager.from(orderMap);

        List<OrderItem> items = orderItemManager.getItems();
        assertThat(items).hasSize(2);
        assertThat(items.get(0).getName()).isEqualTo("콜라");
        assertThat(items.get(0).getRequestedQuantity()).isEqualTo(5);
        assertThat(items.get(1).getName()).isEqualTo("사이다");
        assertThat(items.get(1).getRequestedQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("OrderItemManager의 items 리스트가 불변인지 테스트")
    void getItems_ReturnsUnmodifiableList() {
        Map<String, Integer> orderMap = Map.of("콜라", 5);
        OrderItemManager orderItemManager = OrderItemManager.from(orderMap);

        List<OrderItem> items = orderItemManager.getItems();
        assertThrows(UnsupportedOperationException.class, () -> items.add(OrderItem.of("사이다", 3)));
    }


    @Test
    @DisplayName("updateOrderItemPlusPromotion 메서드 테스트 - 해당 아이템의 수량을 1 증가시킨다.")
    void updateOrderItemPlusPromotionTest() {
        OrderItemManager orderItemManager = OrderItemManager.from(Map.of("콜라", 5));
        orderItemManager.updateOrderItemPlusPromotion("콜라");
        assertThat(orderItemManager.getItems().getFirst().getRequestedQuantity()).isEqualTo(6);
    }

    @Test
    @DisplayName("updateOrderItemAdjustment 메서드 테스트 - 해당 아이템의 수량을 지정한 만큼 감소시킨다.")
    void updateOrderItemAdjustmentTest() {
        OrderItemManager orderItemManager = OrderItemManager.from(Map.of("사이다", 5));
        orderItemManager.updateOrderItemAdjustment("사이다", 2);
        assertThat(orderItemManager.getItems().getFirst().getRequestedQuantity()).isEqualTo(3);
    }
}
