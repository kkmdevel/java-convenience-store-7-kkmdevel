package store.controller;

import java.util.Map;
import store.domain.OrderItemManager;
import store.domain.ProductManager;
import store.domain.PromotionManager;
import store.domain.PromotionResult;
import store.service.PromotionDiscountService;
import store.utils.Parser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final ProductManager productManager;
    private final PromotionManager promotionManager;
    private final PromotionDiscountService promotionDiscountService;

    public StoreController() {
        this.productManager = ProductManager.load();
        this.promotionManager = PromotionManager.load();
        this.promotionDiscountService = new PromotionDiscountService(productManager, promotionManager);
    }

    public void start() {
        displayProducts();
        OrderItemManager orderItemManager = getOrderItemsFromUser();
        PromotionResult promotionResult = applyPromotions(orderItemManager);
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

    private PromotionResult applyPromotions(OrderItemManager orderItemManager) {
        Map<String, Integer> bonuses = promotionDiscountService.calculateBonuses(orderItemManager);
        Map<String, Integer> regularPrices = promotionDiscountService.calculateRegularPrices(orderItemManager, bonuses);
        return PromotionResult.of(bonuses, regularPrices);
    }


}
