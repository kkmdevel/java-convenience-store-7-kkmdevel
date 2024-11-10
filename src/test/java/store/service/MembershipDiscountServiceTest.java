package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.OrderItemManager;
import store.domain.ProductManager;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MembershipDiscountServiceTest {

    private MembershipDiscountService membershipDiscountService;
    private OrderItemManager orderItemManager;

    @BeforeEach
    void setUp() {
        ProductManager productManager = ProductManager.load();
        membershipDiscountService = new MembershipDiscountService(productManager);

        orderItemManager = OrderItemManager.from(Map.of(
                "콜라", 10,
                "사이다", 8,
                "오렌지주스", 6
        ), productManager);
    }

    @Test
    @DisplayName("멤버십 할인 계산 테스트 - 프로모션이 적용되지 않은 항목에 대한 멤버십 할인(8000보다 작을때)")
    void calculateMembershipDiscountTest_LessThanMaxDiscount() {
        Map<String, Integer> bonusMap = Map.of(
                "콜라", 2,
                "사이다", 0,
                "오렌지주스", 0
        );

        int discountAmount = membershipDiscountService.calculateMembershipDiscount(orderItemManager, bonusMap);

        int expectedDiscount = (int) ((1000 * 8 + 1800 * 6) * 0.3);
        assertThat(discountAmount).isEqualTo(expectedDiscount);
    }

    @Test
    @DisplayName("멤버십 할인 계산 테스트 - 할인액이 최대 할인액을 초과할 때")
    void calculateMembershipDiscountTest_ExceedsMaxDiscount() {
        Map<String, Integer> bonusMap = Map.of(
                "콜라", 0,
                "사이다", 0,
                "오렌지주스", 0
        );

        int discountAmount = membershipDiscountService.calculateMembershipDiscount(orderItemManager, bonusMap);

        assertThat(discountAmount).isEqualTo(8000);
    }
}
