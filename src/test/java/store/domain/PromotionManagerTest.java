package store.domain;

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
        assertEquals("탄산2+1", firstPromotion.getName());
        assertEquals(2, firstPromotion.getBuyQuantity());
        assertEquals(1, firstPromotion.getGetQuantity());
        assertEquals(LocalDate.of(2024, 1, 1), firstPromotion.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), firstPromotion.getEndDate());
    }

    @Test
    @DisplayName("프로모션이 활성 상태인지 검증")
    void testPromotionIsActive() {
        LocalDate currentDate = LocalDate.of(2024, 6, 15);
        assertTrue(firstPromotion.isActive(currentDate));
    }
}
