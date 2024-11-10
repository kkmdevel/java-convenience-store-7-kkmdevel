package store.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.OrderItemManager;
import store.domain.OrderItem;
import store.service.PriceCalculationService;
import store.utils.Parser;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {
    private final PriceCalculationService priceCalculationService;

    public OrderController(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    public OrderItemManager getOrderItemsFromUser() {
        OutputView.printOrderPrefix();
        String input = InputView.readLine();
        Map<String, Integer> orderMap = Parser.parseOrderItem(input);
        return OrderItemManager.from(orderMap);
    }


    public void displayTotalAmount(OrderItemManager orderItemManager, List<Integer> totalPrices) {
        Map<String, Integer> purchasedItems = orderItemManager.getItems().stream()
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        OrderItem::getRequestedQuantity
                ));
        OutputView.printOriginalPrice(purchasedItems, totalPrices);
    }

    public List<Integer> getTotalPrices(OrderItemManager orderItemManager) {
        return priceCalculationService.calculateTotalPrice(orderItemManager);
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
