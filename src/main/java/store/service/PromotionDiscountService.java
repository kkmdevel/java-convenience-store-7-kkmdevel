package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.OrderItem;
import store.domain.OrderItemManager;
import store.domain.ProductManager;
import store.domain.PromotionManager;

public class PromotionDiscountService {
    private final ProductManager productManager;
    private final PromotionManager promotionManager;
    private final LocalDate currentDate;

    public PromotionDiscountService(ProductManager productManager, PromotionManager promotionManager) {
        this.productManager = productManager;
        this.promotionManager = promotionManager;
        this.currentDate = LocalDate.from(DateTimes.now());
    }

    public List<OrderItem> findCanReceiveBonus(OrderItemManager orderItemManager) {
        return orderItemManager.getItems().stream()
                .filter(item -> {
                    String promotion = productManager.findPromotion(item.getName());
                    return !promotion.isEmpty() &&
                            item.getRequestedQuantity() % getPromotionQuantity(item) == getPromotionQuantity(item) - 1 &&
                            item.getRequestedQuantity() + 1 <= productManager.getPromotionStock(item.getName());
                })
                .toList();
    }

    public Map<String, Integer> calculateBonuses(OrderItemManager orderItemManager) {
        return orderItemManager.getItems().stream()
                .filter(this::isBonusApplicable)
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        this::calculateBonusQuantity
                ));
    }

    public Map<String, Integer> calculateRegularPrices(OrderItemManager orderItemManager, Map<String, Integer> bonuses) {
        return orderItemManager.getItems().stream()
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        item -> calculateRegularPriceForItem(item, bonuses)
                ));
    }

    private int calculateRegularPriceForItem(OrderItem item, Map<String, Integer> bonuses) {
        int promotionStock = productManager.getPromotionStock(item.getName());
        int bonusQuantity = bonuses.getOrDefault(item.getName(), 0);
        int totalPromotionQuantity = calculateTotalPromotionQuantity(bonusQuantity, item);
        int requestedQuantity = item.getRequestedQuantity();

        if (promotionStock >= requestedQuantity || bonusQuantity == 0) {
            return 0;
        }
        return requestedQuantity - totalPromotionQuantity;
    }

    private boolean isBonusApplicable(OrderItem item) {
        String promotionName = getPromotionName(item);
        return promotionManager.isPromotionActive(promotionName, currentDate);
    }

    private int calculateBonusQuantity(OrderItem item) {
        int promotionQuantity = getPromotionQuantity(item);
        int maxPromotionQuantity = getMaxPromotionQuantity(item, promotionQuantity);
        return Math.min(item.getRequestedQuantity() / promotionQuantity, maxPromotionQuantity / promotionQuantity);
    }

    private int calculateTotalPromotionQuantity(int bonus, OrderItem item) {
        int promotionQuantity = getPromotionQuantity(item);
        return bonus * promotionQuantity;
    }

    private int getPromotionQuantity(OrderItem item) {
        String promotionName = getPromotionName(item);
        return promotionManager.calculatePromotionQuantity(promotionName);
    }

    private int getMaxPromotionQuantity(OrderItem item, int promotionQuantity) {
        int promotionStock = productManager.getPromotionStock(item.getName());
        return promotionStock - (promotionStock % promotionQuantity);
    }

    private String getPromotionName(OrderItem item) {
        return productManager.findPromotion(item.getName());
    }
}
