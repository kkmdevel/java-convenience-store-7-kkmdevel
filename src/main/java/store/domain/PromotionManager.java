package store.domain;

import store.domain.enums.PromotionLines;
import store.utils.DataReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.utils.Parser;


public class PromotionManager {
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    private final List<Promotion> promotions;

    private PromotionManager(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static PromotionManager load() {
        List<Promotion> promotions = new ArrayList<>();
        for (PromotionLines promotionEnum : PromotionLines.values()) {
            DataReader.readLineFromFileData(PROMOTIONS_FILE_PATH, promotionEnum.getLineNumber())
                    .ifPresent(data -> promotions.add(initializePromotion(data)));
        }
        return new PromotionManager(promotions);
    }

    private static Promotion initializePromotion(String data) {
        List<String> values = Arrays.asList(data.split(","));
        String name = values.get(0);
        int buyQuantity = Parser.parseStringToInt(values.get(1));
        int getQuantity = Parser.parseStringToInt(values.get(2));
        LocalDate startDate = Parser.parseStringToLocalDate(values.get(3));
        LocalDate endDate = Parser.parseStringToLocalDate(values.get(4));
        return Promotion.of(name, buyQuantity, getQuantity, startDate, endDate);
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
