package store.view;

public enum OutputMessage {
    WELCOME("안녕하세요. W편의점입니다."),
    ALL_PRODUCTS_PREFIX("현재 보유하고 있는 상품입니다."),
    PRODUCTS("- %s %s원 %s개 %s"),
    PURCHASE_PREFIX("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    CAN_RECEIVE_BONUS("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    ADJUSTMENT_QUANTITY("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    START_RECEIPT("==============W 편의점================"),
    ORIGINAL_PRICE_PREFIX("상품명\t\t수량\t금액"),
    PURCHASED_ITEMS("%s\t\t%d \t%s"),
    BONUS_ITEMS_PREFIX("=============증\t정==============="),
    BONUS_ITEMS("%s\t\t%d");


    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(message, args);
    }
}