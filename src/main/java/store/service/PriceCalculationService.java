package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.OrderItem;
import store.domain.OrderItemManager;
import store.domain.ProductManager;

public class PriceCalculationService {

    private final ProductManager productManager;

    public PriceCalculationService(ProductManager productManager) {
        this.productManager = productManager;
    }

    public List<Integer> calculateTotalPrice(OrderItemManager orderItemManager) {
        List<Integer> totalPrices = new ArrayList<>();
        for (OrderItem item : orderItemManager.getItems()) {
            totalPrices.add(productManager.calculatePrice(item.getName(), item.getRequestedQuantity()));
        }
        return totalPrices;
    }

    public int calculatePromotionDiscount(Map<String, Integer> bonuses) {
        return bonuses.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .mapToInt(entry -> {
                    String productName = entry.getKey();
                    int bonusQuantity = entry.getValue();
                    return productManager.calculatePrice(productName, bonusQuantity);
                })
                .sum();
    }

}
