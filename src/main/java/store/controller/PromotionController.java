package store.controller;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import store.domain.OrderItemManager;
import store.domain.PromotionResult;
import store.service.PromotionDiscountService;
import store.utils.Parser;
import store.view.InputView;
import store.view.OutputView;

public class PromotionController {
    private final PromotionDiscountService promotionDiscountService;

    public PromotionController(PromotionDiscountService promotionDiscountService) {
        this.promotionDiscountService = promotionDiscountService;
    }

    public int calculatePromotionDiscount(Map<String, Integer> bonusMap) {
        return promotionDiscountService.calculatePromotionDiscount(bonusMap);
    }

    public PromotionResult applyPromotions(OrderItemManager orderItemManager) {
        checkAndReceivePromotionItems(orderItemManager);
        Map<String, Integer> bonuses = promotionDiscountService.calculateBonuses(orderItemManager);
        Map<String, Integer> regularPrices = promotionDiscountService.calculateRegularPrices(orderItemManager, bonuses);
        return PromotionResult.of(bonuses, regularPrices);
    }

    private void checkAndReceivePromotionItems(OrderItemManager orderItemManager) {
        promotionDiscountService.findCanReceiveBonus(orderItemManager).forEach(item ->
                handleInputWithRetry(() -> {
                    OutputView.askForReceivePromotion(item.getName());
                    if (Parser.parseYesNo(InputView.readLine())) {
                        orderItemManager.updateOrderItemPlusPromotion(item.getName());
                    }
                })
        );
    }

    public void updateOrderItemsIndividually(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        promotionResult.getRegularPriceMap().forEach((productName, quantity) -> {
            if (quantity > 0) {
                handleInputWithRetry(() -> {
                    OutputView.askForAdjustment(productName, quantity);
                    if (!Parser.parseYesNo(InputView.readLine())) {
                        orderItemManager.updateOrderItemAdjustment(productName, quantity);
                    }
                });
            }
        });
    }

    public void displayBonus(PromotionResult promotionResult) {
        Map<String, Integer> filteredBonusMap = promotionResult.getBonusMap().entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
        OutputView.printBonusItems(filteredBonusMap);
    }

    private void handleInputWithRetry(Runnable action) {
        while (true) {
            try {
                action.run();
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
