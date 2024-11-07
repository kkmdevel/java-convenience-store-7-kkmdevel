package store.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @ParameterizedTest
    @CsvSource({
            "123, 123",
            "-456, -456"
    })
    @DisplayName("올바른 정수 변환 테스트")
    void testParseStringToInt_ValidInput(String input, int expected) {
        assertEquals(expected, Parser.parseStringToInt(input));
    }

    @ParameterizedTest
    @CsvSource({
            "abc",
            "''",
            "' '"
    })
    @DisplayName("잘못된 정수 변환 예외 테스트")
    void testParseStringToInt_InvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseStringToInt(input));
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
            "2024/11/07",
            "07-11-2024",
            "''",
            "' '"
    })
    @DisplayName("잘못된 날짜 변환 예외 테스트")
    void testParseStringToLocalDate_InvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseStringToLocalDate(input));
    }
}
