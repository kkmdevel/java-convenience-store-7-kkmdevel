package store.service;

import store.domain.OrderItemManager;
import store.domain.ProductManager;

public class ProductManagementService {
    private final ProductManager productManager;

    public ProductManagementService(ProductManager productManager) {
        this.productManager = productManager;
    }

    public void updateStock(OrderItemManager orderItemManager) {
        orderItemManager.getItems().forEach(item -> updateItemStock(item.getName(), item.getRequestedQuantity()));
    }

    private void updateItemStock(String itemName, int requestedQuantity) {
        int usedPromotionStock = Math.min(productManager.getPromotionStock(itemName), requestedQuantity);
        productManager.reducePromotionStock(itemName, usedPromotionStock);

        int remainingQuantity = requestedQuantity - usedPromotionStock;
        if (remainingQuantity > 0) {
            productManager.reduceRegularStock(itemName, remainingQuantity);
        }

    }
}
