package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import store.domain.OrderItem;
import store.domain.OrderItemManager;
import store.domain.ProductManager;
import store.domain.PromotionManager;

public class PromotionDiscountService {
    private static final int REQUIRED_REMAINDER_FOR_BONUS = 1;

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
                .filter(this::canReceiveBonus)
                .toList();
    }

    public Map<String, Integer> calculateBonuses(OrderItemManager orderItemManager) {
        return orderItemManager.getItems().stream()
                .filter(this::isBonusApplicable)
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        this::calculateBonusQuantity,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Integer> calculateRegularPrices(OrderItemManager orderItemManager, Map<String, Integer> bonuses) {
        return orderItemManager.getItems().stream()
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        item -> calculateRegularPriceForItem(item, bonuses),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }


    public int calculatePromotionDiscount(Map<String, Integer> bonuses) {
        return bonuses.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .mapToInt(this::calculateDiscountForEntry)
                .sum();
    }

    private boolean canReceiveBonus(OrderItem item) {
        String promotion = productManager.findPromotion(item.getName());
        int promotionQuantity = getPromotionQuantity(item);
        int promotionStock = productManager.getPromotionStock(item.getName());

        return hasValidPromotion(promotion) &&
                isBonusApplicable(item) &&
                isNearBonusThreshold(item, promotionQuantity) &&
                hasSufficientStock(item, promotionStock);
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

    private int calculateRegularPriceForItem(OrderItem item, Map<String, Integer> bonuses) {
        int promotionStock = productManager.getPromotionStock(item.getName());
        int bonusQuantity = bonuses.getOrDefault(item.getName(), 0);
        int totalPromotionQuantity = calculateTotalPromotionQuantity(bonusQuantity, item);
        int requestedQuantity = item.getRequestedQuantity();

        if ( promotionStock == 0 || promotionStock > requestedQuantity || getPromotionName(item).isEmpty()) {
            return 0;
        }
        return requestedQuantity - totalPromotionQuantity;
    }

    private int calculateDiscountForEntry(Map.Entry<String, Integer> entry) {
        String productName = entry.getKey();
        int bonusQuantity = entry.getValue();
        return productManager.calculatePrice(productName, bonusQuantity);
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

    private boolean hasValidPromotion(String promotion) {
        return !promotion.isEmpty();
    }

    private boolean isNearBonusThreshold(OrderItem item, int promotionQuantity) {
        return item.getRequestedQuantity() % promotionQuantity == promotionQuantity - REQUIRED_REMAINDER_FOR_BONUS;
    }

    private boolean hasSufficientStock(OrderItem item, int promotionStock) {
        return item.getRequestedQuantity() + 1 <= promotionStock;
    }

    private String getPromotionName(OrderItem item) {
        return productManager.findPromotion(item.getName());
    }
}
