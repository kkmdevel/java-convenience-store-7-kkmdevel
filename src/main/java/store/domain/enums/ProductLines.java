package store.domain.enums;

public enum ProductLines {

    COLA_PROMOTION(2),
    COLA_REGULAR(3),
    CIDER_PROMOTION(4),
    CIDER_REGULAR(5),
    ORANGE_JUICE(6),
    SPARKLING_WATER_PROMOTION(7),
    WATER(8),
    VITAMIN_WATER(9),
    POTATO_CHIPS_PROMOTION(10),
    POTATO_CHIPS_REGULAR(11),
    CHOCOLATE_BAR_PROMOTION(12),
    CHOCOLATE_BAR_REGULAR(13),
    ENERGY_BAR(14),
    MEAL_BOX(15),
    INSTANT_NOODLES_PROMOTION(16),
    INSTANT_NOODLES_REGULAR(17);

    private final int lineNumber;

    ProductLines(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
