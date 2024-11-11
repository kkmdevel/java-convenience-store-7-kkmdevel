package store.controller;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import store.domain.OrderItemManager;
import store.domain.OrderItem;
import store.domain.ProductManager;
import store.service.TotalPriceCalculator;
import store.utils.Parser;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {
    private final TotalPriceCalculator totalPriceCalculator;
    private final ProductManager productManager;

    public OrderController(TotalPriceCalculator totalPriceCalculator, ProductManager productManager) {
        this.totalPriceCalculator = totalPriceCalculator;
        this.productManager = productManager;
    }

    public OrderItemManager getOrderItemsFromUser() {
        while (true) {
            try {
                OutputView.printOrderPrefix();
                String input = InputView.readLine();
                Map<String, Integer> orderMap = Parser.parseOrderItem(input);
                return OrderItemManager.from(orderMap, productManager);
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public void displayTotalAmount(OrderItemManager orderItemManager, List<Integer> totalPrices) {
        Map<String, Integer> purchasedItems = orderItemManager.getItems().stream()
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        OrderItem::getRequestedQuantity,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
        OutputView.printOriginalPrice(purchasedItems, totalPrices);
    }

    public List<Integer> getTotalPrices(OrderItemManager orderItemManager) {
        return totalPriceCalculator.calculateTotalPrice(orderItemManager);
    }

    public int displayTotalPrice(OrderItemManager orderItemManager, List<Integer> totalPrices) {
        int totalItemQuantity = orderItemManager.getItems().stream()
                .mapToInt(OrderItem::getRequestedQuantity)
                .sum();
        int totalPriceSum = totalPrices.stream().mapToInt(Integer::intValue).sum();
        OutputView.printTotalPrice(totalItemQuantity, totalPriceSum);
        return totalPriceSum;
    }
}