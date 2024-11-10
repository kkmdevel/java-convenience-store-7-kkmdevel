package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemManagerTest {

    private final ProductManager productManager = ProductManager.load();

    @Test
    @DisplayName("OrderItemManager 객체 생성 테스트 - 올바른 입력")
    void createOrderItemManager_ValidInput() {
        Map<String, Integer> orderMap = Map.of("콜라", 5, "사이다", 3);
        OrderItemManager orderItemManager = OrderItemManager.from(orderMap, productManager);

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
        OrderItemManager orderItemManager = OrderItemManager.from(orderMap, productManager);

        List<OrderItem> items = orderItemManager.getItems();
        assertThrows(UnsupportedOperationException.class, () -> items.add(OrderItem.of("사이다", 3)));
    }

    @Test
    @DisplayName("updateOrderItemPlusPromotion 메서드 테스트 - 해당 아이템의 수량을 1 증가시킨다.")
    void updateOrderItemPlusPromotionTest() {
        OrderItemManager orderItemManager = OrderItemManager.from(Map.of("콜라", 5), productManager);
        orderItemManager.updateOrderItemPlusPromotion("콜라");
        assertThat(orderItemManager.getItems().getFirst().getRequestedQuantity()).isEqualTo(6);
    }

    @Test
    @DisplayName("updateOrderItemAdjustment 메서드 테스트 - 해당 아이템의 수량을 지정한 만큼 감소시킨다.")
    void updateOrderItemAdjustmentTest() {
        OrderItemManager orderItemManager = OrderItemManager.from(Map.of("사이다", 5), productManager);
        orderItemManager.updateOrderItemAdjustment("사이다", 2);
        assertThat(orderItemManager.getItems().getFirst().getRequestedQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("존재하지 않는 상품을 주문할 경우 예외 발생 테스트")
    void createOrderItemManager_InvalidProductName() {
        Map<String, Integer> orderMap = Map.of("없는상품", 5);
        assertThrows(IllegalArgumentException.class, () -> OrderItemManager.from(orderMap, productManager));
    }

    @Test
    @DisplayName("재고 수량을 초과하는 주문 요청 시 예외 발생 테스트")
    void createOrderItemManager_ExceedsStock() {
        Map<String, Integer> orderMap = Map.of("콜라", 1000);
        assertThrows(IllegalArgumentException.class, () -> OrderItemManager.from(orderMap, productManager));
    }

}
