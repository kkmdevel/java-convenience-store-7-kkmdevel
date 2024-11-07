package store.view;

public enum OutputMessage {
    WELCOME("안녕하세요. W편의점입니다."),
    ALL_PRODUCTS_PREFIX("현재 보유하고 있는 상품입니다."),
    PRODUCTS("- %s %s원 %s개 %s");

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