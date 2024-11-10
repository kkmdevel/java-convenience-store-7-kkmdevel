package store.service;

import store.domain.OrderItemManager;
import store.domain.ProductManager;

import java.util.List;
import java.util.Map;

public class MembershipDiscountService {
    private final ProductManager productManager;
    private static final int MAX_DISCOUNT_AMOUNT = 8000;
    private static final double DISCOUNT_RATE = 0.3;

    public MembershipDiscountService(ProductManager productManager) {
        this.productManager = productManager;
    }

    public int calculateMembershipDiscount(OrderItemManager orderItemManager, Map<String, Integer> bonusMap) {
        List<Integer> discountedPrices = orderItemManager.getItems().stream()
                .filter(item -> bonusMap.getOrDefault(item.getName(), 0) == 0)
                .map(item -> productManager.calculatePrice(item.getName(), item.getRequestedQuantity()))
                .map(this::applyDiscount)
                .toList();

        int totalDiscountAmount = discountedPrices.stream()
                .mapToInt(Integer::intValue)
                .sum();

        return Math.min(totalDiscountAmount, MAX_DISCOUNT_AMOUNT);
    }

    private int applyDiscount(int price) {
        return (int) (price * DISCOUNT_RATE);
    }
}
