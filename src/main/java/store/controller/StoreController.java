package store.controller;

import store.domain.OrderItemManager;
import store.domain.PromotionManager;
import store.domain.PromotionResult;
import store.domain.ProductManager;
import store.service.PromotionDiscountService;
import store.service.TotalPriceCalculator;
import store.service.MembershipDiscountService;
import store.view.OutputView;

public class StoreController {
    private final ProductController productController;
    private final OrderController orderController;
    private final PromotionController promotionController;
    private final DiscountAndPaymentController discountAndPaymentController;

    public StoreController() {
        ProductManager productManager = ProductManager.load();
        PromotionManager promotionManager = PromotionManager.load();

        TotalPriceCalculator totalPriceCalculator = new TotalPriceCalculator(productManager);
        this.productController = new ProductController(productManager);
        this.orderController = new OrderController(totalPriceCalculator);
        this.promotionController = new PromotionController(new PromotionDiscountService(productManager, promotionManager));
        this.discountAndPaymentController = new DiscountAndPaymentController(new MembershipDiscountService(productManager));
    }

    public void start() {
        do {
            executeShoppingCycle();
        } while (discountAndPaymentController.askToContinueShopping());
    }

    private void executeShoppingCycle() {
        productController.displayProducts();
        OrderItemManager orderItemManager = getOrderItemsFromUser();
        PromotionResult promotionResult = applyPromotions(orderItemManager);
        processOrderAndCalculateDiscounts(orderItemManager, promotionResult);
        productController.manageStock(orderItemManager);
    }

    private OrderItemManager getOrderItemsFromUser() {
        return orderController.getOrderItemsFromUser();
    }

    private PromotionResult applyPromotions(OrderItemManager orderItemManager) {
        return promotionController.applyPromotions(orderItemManager);
    }

    private void processOrderAndCalculateDiscounts(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        applyPromotionsAndAdjustments(orderItemManager, promotionResult);
        int membershipDiscountAmount = askAndCalculateMembershipDiscount(orderItemManager, promotionResult);
        displayOrderDetails(orderItemManager);
        displayDiscountsAndPayableAmount(orderItemManager, promotionResult, membershipDiscountAmount);
    }

    private void displayDiscountsAndPayableAmount(OrderItemManager orderItemManager, PromotionResult promotionResult, int membershipDiscountAmount) {
        displayBonus(promotionResult);

        int totalPrice = orderController.displayTotalPrice(orderItemManager, orderController.getTotalPrices(orderItemManager));
        int promotionDiscountAmount = promotionController.calculatePromotionDiscount(promotionResult.getBonusMap());
        OutputView.printPromotionDiscountPrices(promotionDiscountAmount);
        displayMembershipDiscount(membershipDiscountAmount);

        discountAndPaymentController.displayPayableAmount(totalPrice, membershipDiscountAmount + promotionDiscountAmount);
    }

    private int askAndCalculateMembershipDiscount(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        return discountAndPaymentController.askAndCalculateMembershipDiscount(orderItemManager, promotionResult);
    }

    private void displayMembershipDiscount(int membershipDiscountAmount) {
        OutputView.printMembershipDiscount(membershipDiscountAmount);
    }

    private void displayOrderDetails(OrderItemManager orderItemManager) {
        orderController.displayTotalAmount(orderItemManager, orderController.getTotalPrices(orderItemManager));
    }

    private void applyPromotionsAndAdjustments(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        promotionController.updateOrderItemsIndividually(orderItemManager, promotionResult);
    }

    private void displayBonus(PromotionResult promotionResult) {
        promotionController.displayBonus(promotionResult);
    }
}