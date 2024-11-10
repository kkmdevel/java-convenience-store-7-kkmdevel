package store.service;

import store.domain.OrderItemManager;
import store.domain.ProductManager;

public class ProductManagementService {
    private final ProductManager productManager;

    public ProductManagementService(ProductManager productManager) {
        this.productManager = productManager;
    }

    public void updateStock(OrderItemManager orderItemManager) {
        orderItemManager.getItems().forEach(item -> {
            int usedPromotionStock = Math.min(productManager.getPromotionStock(item.getName()), item.getRequestedQuantity());
            productManager.reducePromotionStock(item.getName(), usedPromotionStock);
            int remainingQuantity = item.getRequestedQuantity() - usedPromotionStock;
            if (remainingQuantity > 0) productManager.reduceRegularStock(item.getName(), remainingQuantity);
        });
    }
}
