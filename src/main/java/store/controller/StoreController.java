package store.controller;

import java.util.Map;
import store.domain.OrderItemManager;
import store.domain.ProductManager;
import store.domain.PromotionManager;
import store.utils.Parser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final ProductManager productManager;
    private final PromotionManager promotionManager;

    public StoreController() {
        this.productManager = ProductManager.load();
        this.promotionManager = PromotionManager.load();
    }

    public void start() {
        displayProducts();
        OrderItemManager orderItemManager = getOrderItemsFromUser();
    }

    private void displayProducts() {
        OutputView.printWelcomeAndAllProducts(productManager);
    }

    private OrderItemManager getOrderItemsFromUser() {
        OutputView.printOrderPrefix();
        String input = InputView.readLine();
        Map<String, Integer> orderMap = Parser.parseOrderItem(input);
        return OrderItemManager.from(orderMap);
    }



}
