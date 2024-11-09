package store.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderItemManager {
    private final List<OrderItem> items;

    private OrderItemManager(List<OrderItem> items) {
        this.items = items;
    }

    public static OrderItemManager from(Map<String, Integer> orderMap) {
        List<OrderItem> items = orderMap.entrySet().stream()
                .map(entry -> OrderItem.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new OrderItemManager(items);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

}
