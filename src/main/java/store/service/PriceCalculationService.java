package store.service;

import java.util.ArrayList;
import java.util.List;
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
            totalPrices.add(productManager.calculateTotalPrice(item.getName(), item.getRequestedQuantity()));
        }
        return totalPrices;
    }

}
