package store.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrderItemManager {
    private final List<OrderItem> items;

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
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    private static void validateProductQuantities(Map<String, Integer> orderMap, ProductManager productManager) {
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String productName = entry.getKey();
            int requestedQuantity = entry.getValue();
            int availableStock = productManager.calculateTotalStockForProduct(productName);

            if (requestedQuantity > availableStock) {
                throw new IllegalArgumentException();
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
        items.stream()
                .filter(item -> item.hasName(productName))
                .findFirst()
                .ifPresent(item -> item.adjustmentQuantity(quantity));
    }
}
