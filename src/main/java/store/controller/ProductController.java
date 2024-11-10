package store.controller;

import store.domain.OrderItemManager;
import store.domain.ProductManager;
import store.service.ProductManagementService;
import store.view.OutputView;

public class ProductController {
    private final ProductManager productManager;
    private final ProductManagementService productManagementService;

    public ProductController(ProductManager productManager) {
        this.productManager = productManager;
        this.productManagementService = new ProductManagementService(productManager);
    }

    public void displayProducts() {
        OutputView.printWelcomeAndAllProducts(productManager);
    }

    public void manageStock(OrderItemManager orderItemManager) {
        productManagementService.updateStock(orderItemManager);
    }
}
