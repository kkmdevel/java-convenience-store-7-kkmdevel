package store.domain;

import java.util.Map;

public class PromotionResult {
    private final Map<String, Integer> bonusMap;
    private final Map<String, Integer> regularPriceMap;

    private PromotionResult(Map<String, Integer> bonusMap, Map<String, Integer> regularPriceMap) {
        this.bonusMap = bonusMap;
        this.regularPriceMap = regularPriceMap;
    }

    public static PromotionResult of(Map<String, Integer> bonusMap, Map<String, Integer> regularPriceMap){
        return new PromotionResult(bonusMap,regularPriceMap);
    }

    public Map<String, Integer> getBonusMap() {
        return bonusMap;
    }

    public Map<String, Integer> getRegularPriceMap() {
        return regularPriceMap;
    }
}
