package store.domain.enums;

public enum ProductLines {

    COLA_PROMOTION(2),
    COLA_REGULAR(3),
    CIDER_PROMOTION(4),
    CIDER_REGULAR(5),
    ORANGE_JUICE_PROMOTION(6),
    ORANGE_JUICE_REGULAR(7),
    SPARKLING_WATER_PROMOTION(8),
    SPARKLING_WATER_REGULAR(9),
    WATER(10),
    VITAMIN_WATER(11),
    POTATO_CHIPS_PROMOTION(12),
    POTATO_CHIPS_REGULAR(13),
    CHOCOLATE_BAR_PROMOTION(14),
    CHOCOLATE_BAR_REGULAR(15),
    ENERGY_BAR(16),
    MEAL_BOX(17),
    INSTANT_NOODLES_PROMOTION(18),
    INSTANT_NOODLES_REGULAR(19);

    private final int lineNumber;

    ProductLines(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
