package store.domain;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buyQuantity;
    private final int getQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(String name, int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buyQuantity, getQuantity, startDate, endDate);
    }

    public boolean isNameEqualTo(String promotionName) {
        return this.name.equals(promotionName);
    }

    public boolean isBuyQuantityEqualTo(int quantity) {
        return this.buyQuantity == quantity;
    }

    public boolean isGetQuantityEqualTo(int quantity) {
        return this.getQuantity == quantity;
    }

    public boolean isStartDateEqualTo(LocalDate date) {
        return this.startDate.equals(date);
    }

    public boolean isEndDateEqualTo(LocalDate date) {
        return this.endDate.equals(date);
    }

    public int calculatePromotionQuantity() {
        return buyQuantity + getQuantity;
    }

    public boolean isActive(LocalDate currentDate) {
        return (currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.isEqual(endDate) || currentDate.isBefore(endDate));
    }
}
