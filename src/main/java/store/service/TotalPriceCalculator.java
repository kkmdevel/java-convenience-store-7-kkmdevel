package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.OrderItem;
import store.domain.OrderItemManager;
import store.domain.ProductManager;

public class TotalPriceCalculator {
    private final ProductManager productManager;

    public TotalPriceCalculator(ProductManager productManager) {
        this.productManager = productManager;
    }

    public List<Integer> calculateTotalPrice(OrderItemManager orderItemManager) {
        List<Integer> totalPrices = new ArrayList<>();
        for (OrderItem item : orderItemManager.getItems()) {
            int price = productManager.calculatePrice(item.getName(), item.getRequestedQuantity());
            totalPrices.add(price);
        }
        return totalPrices;
    }
}
