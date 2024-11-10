package store.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.OrderItem;
import store.domain.OrderItemManager;
import store.domain.ProductManager;
import store.domain.PromotionManager;
import store.domain.PromotionResult;
import store.service.MembershipDiscountService;
import store.service.PromotionDiscountService;
import store.service.PriceCalculationService;
import store.utils.Parser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final ProductManager productManager;
    private final PromotionManager promotionManager;
    private final PromotionDiscountService promotionDiscountService;
    private final PriceCalculationService priceCalculationService;
    private final MembershipDiscountService membershipDiscountService;

    public StoreController() {
        this.productManager = ProductManager.load();
        this.promotionManager = PromotionManager.load();
        this.promotionDiscountService = new PromotionDiscountService(productManager, promotionManager);
        this.priceCalculationService = new PriceCalculationService(productManager);
        this.membershipDiscountService = new MembershipDiscountService(productManager);
    }

    public void start() {
        displayProducts();
        OrderItemManager orderItemManager = getOrderItemsFromUser();
        PromotionResult promotionResult = applyPromotions(orderItemManager);
        updateOrderItemsIndividually(orderItemManager, promotionResult);
        List<Integer> totalPrices = getTotalPrices(orderItemManager);
        int membershipDiscountAmount = askAndCalculateMembershipDiscount(orderItemManager, promotionResult);
        displayTotalAmount(orderItemManager,totalPrices);
        displayBonus(promotionResult);
        int totalPrice = displayTotalPrice(orderItemManager,totalPrices);
        int promotionDiscountAmount = calculateAndDisplayPromotionDiscount(promotionResult);
        OutputView.printMembershipDiscount(membershipDiscountAmount);
        displayPayableAmount(totalPrice,membershipDiscountAmount+promotionDiscountAmount);
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
            if(quantity>0) {
                OutputView.askForAdjustment(productName, quantity);
                if (!Parser.parseYesNo(InputView.readLine())) {
                    orderItemManager.updateOrderItemAdjustment(productName, quantity);
                }
            }
        });
    }


    private int askAndCalculateMembershipDiscount(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        OutputView.askForMembershipDiscount();
        if (Parser.parseYesNo(InputView.readLine())) {
            return membershipDiscountService.calculateMembershipDiscount(orderItemManager, promotionResult.getBonusMap());
        }
        return 0;
    }


    private void displayTotalAmount(OrderItemManager orderItemManager,List<Integer> totalPrices) {
        Map<String, Integer> purchasedItems = orderItemManager.getItems().stream()
                .collect(Collectors.toMap(
                        OrderItem::getName,
                        OrderItem::getRequestedQuantity
                ));
        OutputView.printOriginalPrice(purchasedItems, totalPrices);
    }

    private List<Integer> getTotalPrices(OrderItemManager orderItemManager) {
        return priceCalculationService.calculateTotalPrice(orderItemManager);
    }

    private void displayBonus(PromotionResult promotionResult) {
        Map<String, Integer> filteredBonusMap = promotionResult.getBonusMap().entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        OutputView.printBonusItems(filteredBonusMap);
    }

    private int displayTotalPrice(OrderItemManager orderItemManager, List<Integer> totalPrices) {
        int totalItemQuantity = orderItemManager.getItems().stream()
                .mapToInt(OrderItem::getRequestedQuantity)
                .sum();
        int totalPriceSum = totalPrices.stream().mapToInt(Integer::intValue).sum();
        OutputView.printTotalPrice(totalItemQuantity, totalPriceSum);
        return totalPriceSum;
    }

    private int calculateAndDisplayPromotionDiscount(PromotionResult promotionResult) {
        int PromotionDiscountAmount = priceCalculationService.calculatePromotionDiscount(promotionResult.getBonusMap());
        OutputView.printPromotionDiscountPrices(PromotionDiscountAmount);
        return PromotionDiscountAmount;
    }

    private void displayPayableAmount(int totalPrice, int discountAmount) {
        OutputView.printPayableAmount(totalPrice - discountAmount);
    }


}
