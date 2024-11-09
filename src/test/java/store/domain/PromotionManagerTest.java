package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionManagerTest {

    private static List<Promotion> promotions;
    private static Promotion firstPromotion;

    @BeforeAll
    static void setUp() {
        PromotionManager promotionManager = PromotionManager.load();
        promotions = promotionManager.getPromotions();
        firstPromotion = promotions.getFirst();
    }

    @Test
    @DisplayName("프로모션 매니저가 프로모션을 성공적으로 로드하는지 테스트")
    void testLoadPromotions() {
        assertFalse(promotions.isEmpty());
    }

    @Test
    @DisplayName("첫 번째 프로모션 데이터가 올바른지 검증")
    void testFirstPromotionData() {
        assertTrue(firstPromotion.isNameEqualTo("탄산2+1"));
        assertTrue(firstPromotion.isBuyQuantityEqualTo(2));
        assertTrue(firstPromotion.isGetQuantityEqualTo(1));
        assertTrue(firstPromotion.isStartDateEqualTo(LocalDate.of(2024, 1, 1)));
        assertTrue(firstPromotion.isEndDateEqualTo(LocalDate.of(2024, 12, 31)));
    }

    @Test
    @DisplayName("프로모션이 활성 상태인지 검증")
    void testPromotionIsActive() {
        LocalDate currentDate = LocalDate.from(DateTimes.now());
        assertTrue(firstPromotion.isActive(currentDate));
    }
}
