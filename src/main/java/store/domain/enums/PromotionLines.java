package store.domain.enums;

public enum PromotionLines {

    TWO_PLUS_ONE(2),
    MD_RECOMMENDED(3),
    FLASH_DISCOUNT(4);

    private final int lineNumber;

    PromotionLines(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber(){
        return lineNumber;
    }
}
