package store.domain;

import static store.exception.ExceptionMessage.ERROR_STOCK_LIMIT;
import static store.exception.ExceptionMessage.NO_EXIST_PRODUCT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrderItemManager {
    private List<OrderItem> items;

    private OrderItemManager(List<OrderItem> items) {
        this.items = items;
    }

    public static OrderItemManager from(Map<String, Integer> orderMap, ProductManager productManager) {
        validateOrderItems(orderMap, productManager);

        List<OrderItem> items = orderMap.entrySet().stream()
                .map(entry -> OrderItem.of(entry.getKey(), entry.getValue()))
                .toList();
        return new OrderItemManager(items);
    }

    private static void validateOrderItems(Map<String, Integer> orderMap, ProductManager productManager) {
        validateProductNames(orderMap, productManager);
        validateProductQuantities(orderMap, productManager);
    }

    private static void validateProductNames(Map<String, Integer> orderMap, ProductManager productManager) {
        for (String productName : orderMap.keySet()) {
            if (!productManager.isProductAvailable(productName)) {
                throw new IllegalArgumentException(NO_EXIST_PRODUCT.toString());
            }
        }
    }

    private static void validateProductQuantities(Map<String, Integer> orderMap, ProductManager productManager) {
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String productName = entry.getKey();
            int requestedQuantity = entry.getValue();
            int availableStock = productManager.calculateTotalStockForProduct(productName);

            if (requestedQuantity > availableStock) {
                throw new IllegalArgumentException(ERROR_STOCK_LIMIT.toString());
            }
        }
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void updateOrderItemPlusPromotion(String itemName) {
        items.stream()
                .filter(item -> item.hasName(itemName))
                .findFirst()
                .ifPresent(OrderItem::receivePromotion);
    }

    public void updateOrderItemAdjustment(String productName, int quantity) {
        List<OrderItem> mutableItems = new ArrayList<>(items);
        mutableItems.stream()
                .filter(item -> item.hasName(productName))
                .findFirst()
                .ifPresent(item -> {
                    item.adjustmentQuantity(quantity);
                    if (item.getRequestedQuantity() <= 0) {
                        mutableItems.remove(item); // 수량이 0 이하인 경우 제거
                    }
                });
        items = mutableItems;
    }
}
