package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionTest {

    @ParameterizedTest
    @CsvSource({
            "'탄산2+1', 2, 1, '2024-01-01', '2024-12-31'",
            "'MD추천상품', 1, 1, '2024-01-01', '2024-12-31'",
            "'반짝할인', 1, 1, '2024-11-01', '2024-11-30'"
    })
    @DisplayName("프로모션 생성 테스트")
    void testPromotionCreation(String name, int buyQuantity, int giftQuantity, String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        Promotion promotion = Promotion.of(name, buyQuantity, giftQuantity, startDate, endDate);

        assertTrue(promotion.isNameEqualTo(name));
        assertTrue(promotion.isBuyQuantityEqualTo(buyQuantity));
        assertTrue(promotion.isGetQuantityEqualTo(giftQuantity));
        assertTrue(promotion.isStartDateEqualTo(startDate));
        assertTrue(promotion.isEndDateEqualTo(endDate));
    }

    @ParameterizedTest
    @CsvSource({
            "'탄산2+1', 2, 1, '2024-01-01', '2024-12-31', true",
            "'MD추천상품', 1, 1, '2024-01-01', '2024-12-31', true",
            "'반짝할인', 1, 1, '2024-10-01', '2024-10-30', false",
    })
    @DisplayName("프로모션 활성 상태 테스트")
    void testPromotionIsActive(String name, int buyQuantity, int giftQuantity, String startDateStr, String endDateStr, boolean expectedActive) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        LocalDate currentDate = LocalDate.from(DateTimes.now());

        Promotion promotion = Promotion.of(name, buyQuantity, giftQuantity, startDate, endDate);

        assertEquals(expectedActive, promotion.isActive(currentDate));
    }
}
