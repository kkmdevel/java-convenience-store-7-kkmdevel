package store.utils;


import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @ParameterizedTest
    @CsvSource({
            "123, 123", "-456, -456"
    })
    @DisplayName("올바른 정수 변환 테스트")
    void testParseStringToInt_ValidInput(String input, int expected) {
        assertEquals(expected, Parser.parseStringToInt(input));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-11-07, 2024-11-07"
    })
    @DisplayName("올바른 날짜 변환 테스트")
    void testParseStringToLocalDate_ValidInput(String input, String expectedDate) {
        LocalDate expected = LocalDate.parse(expectedDate);
        assertEquals(expected, Parser.parseStringToLocalDate(input));
    }

    @ParameterizedTest
    @CsvSource({
            "'[item1-5],[item2-10]', 'item1', 5, 'item2', 10",
            "'[itemA-3],[itemB-7],[itemC-1]', 'itemA', 3, 'itemB', 7, 'itemC', 1"
    })
    @DisplayName("올바른 주문 아이템 파싱 테스트")
    void testParseOrderItem_ValidInput(String input, String expectedKey1, int expectedValue1, String expectedKey2, int expectedValue2) {
        Map<String, Integer> orderItems = Parser.parseOrderItem(input);

        assertEquals(expectedValue1, orderItems.get(expectedKey1));
        assertEquals(expectedValue2, orderItems.get(expectedKey2));
    }

    @ParameterizedTest
    @CsvSource({
            "'Y', true", "'N', false"
    })
    @DisplayName("올바른 Y/N 입력 테스트")
    void testParseYesNo_ValidInput(String input, boolean expected) {
        assertEquals(expected, Parser.parseYesNo(input));
    }

    @ParameterizedTest
    @CsvSource({
            "abc", "''", "' '"
    })
    @DisplayName("잘못된 정수 변환 예외 테스트")
    void testParseStringToInt_InvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseStringToInt(input));
    }

    @ParameterizedTest
    @CsvSource({
            "2024/11/07", "07-11-2024", "''", "' '"
    })
    @DisplayName("잘못된 날짜 변환 예외 테스트")
    void testParseStringToLocalDate_InvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseStringToLocalDate(input));
    }

    @ParameterizedTest
    @CsvSource({
            "'A'", "'yes'", "'no'", "' '", "''"
    })
    @DisplayName("잘못된 Y/N 입력 예외 테스트")
    void testParseYesNo_InvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseYesNo(input));
    }

}
