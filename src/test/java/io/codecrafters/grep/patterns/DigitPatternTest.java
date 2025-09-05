package io.codecrafters.grep.patterns;

import io.codecrafters.grep.testutil.BaseRegexTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Comprehensive tests for digit pattern (\d) functionality.
 * Tests that \d matches any digit character [0-9].
 */
@DisplayName("Digit Pattern Tests")
class DigitPatternTest extends BaseRegexTest {

    private static final String DIGIT_PATTERN = "\\d";

    @Nested
    @DisplayName("Digit Pattern Correctness")
    class DigitPatternCorrectness {

        @ParameterizedTest
        @ValueSource(strings = {"0", "5", "9", "123", "abc123", "version 2.0", "line1", "test7end"})
        @DisplayName("\\d should match text containing digits")
        void testMatchesDigits(String text) {
            assertMatches(DIGIT_PATTERN, text);
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "Z", " ", "@", "_", "hello world", "no-digits-here", ""})
        @DisplayName("\\d should not match text without digits")
        void testDoesNotMatchNonDigits(String text) {
            assertDoesNotMatch(DIGIT_PATTERN, text);
        }

        @ParameterizedTest
        @ValueSource(chars = {'0', '5', '9'})
        @DisplayName("\\d should match digit characters")
        void testDigitCharacters(char digit) {
            assertMatches(DIGIT_PATTERN, String.valueOf(digit));
        }

        @ParameterizedTest
        @ValueSource(chars = {'a', 'Z', '@'})
        @DisplayName("\\d should not match non-digit characters")
        void testNonDigitCharacters(char nonDigit) {
            assertDoesNotMatch(DIGIT_PATTERN, String.valueOf(nonDigit));
        }
    }

    @Test
    @DisplayName("\\d should match first digit in mixed content")
    void testDigitInMixedContent() {
        assertMatches(DIGIT_PATTERN, "abc3def");
        assertMatches(DIGIT_PATTERN, "!@#1$%^");
        assertMatches(DIGIT_PATTERN, "   9   ");
    }

    @Test
    @DisplayName("\\d should match when digit is at start of text")
    void testDigitAtStart() {
        assertMatches(DIGIT_PATTERN, "1abc");
        assertMatches(DIGIT_PATTERN, "9xyz");
    }

    @Test
    @DisplayName("\\d should match when digit is at end of text")
    void testDigitAtEnd() {
        assertMatches(DIGIT_PATTERN, "abc1");
        assertMatches(DIGIT_PATTERN, "xyz9");
    }
}
