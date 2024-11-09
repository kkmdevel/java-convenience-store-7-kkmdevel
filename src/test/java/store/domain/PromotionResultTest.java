package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PromotionResultTest {

    @Test
    @DisplayName("PromotionResult 객체 생성 테스트")
    void createPromotionResult() {
        Map<String, Integer> bonusMap = Map.of("콜라", 2, "사이다", 1);
        Map<String, Integer> regularPriceMap = Map.of("콜라", 4, "사이다", 2);

        PromotionResult promotionResult = PromotionResult.of(bonusMap, regularPriceMap);

        assertThat(promotionResult.getBonusMap()).isEqualTo(bonusMap);
        assertThat(promotionResult.getRegularPriceMap()).isEqualTo(regularPriceMap);
        assertThat(promotionResult.getBonusMap().keySet()).isEqualTo(promotionResult.getRegularPriceMap().keySet());
    }

}
