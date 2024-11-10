package store.controller;

import store.domain.OrderItemManager;
import store.domain.PromotionResult;
import store.service.MembershipDiscountService;
import store.view.InputView;
import store.view.OutputView;
import store.utils.Parser;

public class DiscountAndPaymentController {
    private final MembershipDiscountService membershipDiscountService;

    public DiscountAndPaymentController(MembershipDiscountService membershipDiscountService) {
        this.membershipDiscountService = membershipDiscountService;
    }

    public int askAndCalculateMembershipDiscount(OrderItemManager orderItemManager, PromotionResult promotionResult) {
        while (true) {
            try {
                OutputView.askForMembershipDiscount();
                if (Parser.parseYesNo(InputView.readLine())) {
                    return membershipDiscountService.calculateMembershipDiscount(orderItemManager, promotionResult.getBonusMap());
                }
                return 0;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public void displayPayableAmount(int totalPrice, int discountAmount) {
        OutputView.printPayableAmount(totalPrice - discountAmount);
    }

    public boolean askToContinueShopping() {
        while (true) {
            try {
                OutputView.askToContinueShopping();
                return Parser.parseYesNo(InputView.readLine());
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
