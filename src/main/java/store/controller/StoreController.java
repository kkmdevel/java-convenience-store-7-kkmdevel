package store.controller;

import store.domain.ProductManager;
import store.domain.PromotionManager;
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
    }

    private void displayProducts() {
        OutputView.printWelcomeAndAllProducts(productManager);
    }

}
