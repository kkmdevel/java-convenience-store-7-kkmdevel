package store.exception;

public enum ExceptionMessage {
    ERROR_INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ERROR_STOCK_LIMIT("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ERROR_INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    NO_EXIST_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[ERROR] " + message;
    }
}