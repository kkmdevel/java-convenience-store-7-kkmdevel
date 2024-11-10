package store.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.OrderItem;
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
        List<OrderItem> canReceivePromotion = promotionDiscountService.findCanReceiveBonus(orderItemManager);
        for (OrderItem item : canReceivePromotion) {
            OutputView.askForReceivePromotion(item.getName());
            if (Parser.parseYesNo(InputView.readLine())) {
                orderItemManager.updateOrderItemPlusPromotion(item.getName());
            }
        }
    }

    public void updateOrderItemsIndividually(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        promotionResult.getRegularPriceMap().forEach((productName, quantity) -> {
            if (quantity > 0) {
                OutputView.askForAdjustment(productName, quantity);
                if (!Parser.parseYesNo(InputView.readLine())) {
                    orderItemManager.updateOrderItemAdjustment(productName, quantity);
                }
            }
        });
    }

    public void displayBonus(PromotionResult promotionResult) {
        Map<String, Integer> filteredBonusMap = promotionResult.getBonusMap().entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        OutputView.printBonusItems(filteredBonusMap);
    }
}
