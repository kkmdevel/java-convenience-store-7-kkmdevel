package store.controller;

import java.util.List;
import java.util.Map;
import store.domain.OrderItem;
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
        updateOrderItemsIndividually(orderItemManager, promotionResult);
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
        checkAndReceivePromotionItems(orderItemManager);
        Map<String, Integer> bonuses = promotionDiscountService.calculateBonuses(orderItemManager);
        Map<String, Integer> regularPrices = promotionDiscountService.calculateRegularPrices(orderItemManager, bonuses);
        return PromotionResult.of(bonuses, regularPrices);
    }

    private void checkAndReceivePromotionItems(OrderItemManager orderItemManager) {
        List<OrderItem> canReceivePromotion = promotionDiscountService.findCanReceiveBonus(orderItemManager);
        for (OrderItem item : canReceivePromotion) {
            OutputView.askForReceivePromotion(item.getName());
            if (Parser.parseYesNo(InputView.readLine())) {
                orderItemManager.updateOrderItemPlusPromotion(item.getName());
            }
        }
    }

    private void updateOrderItemsIndividually(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        promotionResult.getRegularPriceMap().forEach((productName, quantity) -> {
            OutputView.askForAdjustment(productName, quantity);
            if (!Parser.parseYesNo(InputView.readLine())) {
                orderItemManager.updateOrderItemAdjustment(productName, quantity);
            }
        });
    }

}
