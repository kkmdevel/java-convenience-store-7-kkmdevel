package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemTest {

    @Test
    @DisplayName("OrderItem 객체 생성 테스트 - 올바른 입력")
    void createOrderItem_ValidInput() {
        OrderItem orderItem = OrderItem.of("콜라", 5);
        assertThat(orderItem.getName()).isEqualTo("콜라");
        assertThat(orderItem.getRequestedQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("OrderItem 객체 생성 테스트 - 이름이 null이거나 빈 문자열인 경우 예외 발생")
    void createOrderItem_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> OrderItem.of(null, 5));
        assertThrows(IllegalArgumentException.class, () -> OrderItem.of("", 5));
        assertThrows(IllegalArgumentException.class, () -> OrderItem.of("   ", 5));
    }

    @Test
    @DisplayName("OrderItem 객체 생성 테스트 - 수량이 0 이하인 경우 예외 발생")
    void createOrderItem_InvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> OrderItem.of("콜라", 0));
        assertThrows(IllegalArgumentException.class, () -> OrderItem.of("콜라", -1));
    }
}
