package store.domain;

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
    void testPromotionCreation(String name, int buyQuantity, int getQuantity, String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        Promotion promotion = Promotion.of(name, buyQuantity, getQuantity, startDate, endDate);

        assertEquals(name, promotion.getName());
        assertEquals(buyQuantity, promotion.getBuyQuantity());
        assertEquals(getQuantity, promotion.getGetQuantity());
        assertEquals(startDate, promotion.getStartDate());
        assertEquals(endDate, promotion.getEndDate());
    }

    @ParameterizedTest
    @CsvSource({
            "'탄산2+1', 2, 1, '2024-01-01', '2024-12-31', '2024-06-15', true",
            "'MD추천상품', 1, 1, '2024-01-01', '2024-12-31', '2024-01-01', true",
            "'반짝할인', 1, 1, '2024-11-01', '2024-11-30', '2024-12-01', false",
            "'반짝할인', 1, 1, '2024-11-01', '2024-11-30', '2024-11-30', true"
    })
    @DisplayName("프로모션 활성 상태 테스트")
    void testPromotionIsActive(String name, int buyQuantity, int getQuantity, String startDateStr, String endDateStr, String currentDateStr, boolean expectedActive) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        LocalDate currentDate = LocalDate.parse(currentDateStr);

        Promotion promotion = Promotion.of(name, buyQuantity, getQuantity, startDate, endDate);

        assertEquals(expectedActive, promotion.isActive(currentDate));
    }
}
