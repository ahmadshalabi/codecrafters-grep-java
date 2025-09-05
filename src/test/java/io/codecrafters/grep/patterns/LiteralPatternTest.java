package io.codecrafters.grep.patterns;

import io.codecrafters.grep.testutil.BaseRegexTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Comprehensive tests for literal character pattern functionality.
 * Tests single character matches and case sensitivity.
 */
@DisplayName("Literal Pattern Tests")
class LiteralPatternTest extends BaseRegexTest {

    @Nested
    @DisplayName("Pattern Correctness")
    class PatternCorrectness {

        @ParameterizedTest
        @CsvSource({
            "a, apple",
            "A, Apple", 
            "5, version5",
            "!, hello!",
            "z, amazed",
            "0, test0ing",
            "*, star*light"
        })
        @DisplayName("Literal characters should match when present in text")
        void testLiteralMatches(String pattern, String text) {
            assertMatches(pattern, text);
        }

        @ParameterizedTest
        @CsvSource({
            "a, Apple",
            "x, hello",
            "A, apple",
            "1, zero",
            "!, question?"
        })
        @DisplayName("Literal characters should not match when not present or case differs")
        void testLiteralNonMatches(String pattern, String text) {
            assertDoesNotMatch(pattern, text);
        }
    }

    @Nested
    @DisplayName("Character Type Coverage")
    class CharacterTypeCoverage {

        @ParameterizedTest
        @ValueSource(chars = {'a', 'Z', '5'})
        @DisplayName("Different character types should match correctly")
        void testCharacterTypes(char character) {
            String pattern = String.valueOf(character);
            String text = "prefix" + character + "suffix";
            assertMatches(pattern, text);
        }
    }

    @Nested
    @DisplayName("Special Characters")
    class SpecialCharacters {

        @ParameterizedTest
        @ValueSource(chars = {'@', '_', ' '})
        @DisplayName("Special and whitespace characters should match literally")
        void testSpecialCharacters(char c) {
            String pattern = String.valueOf(c);
            String text = "x" + c + "y";
            assertMatches(pattern, text);
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @ParameterizedTest
        @CsvSource({
            "a, a",
            "!, !"
        })
        @DisplayName("Single character text should match exactly")
        void testSingleCharacterText(String pattern, String text) {
            assertMatches(pattern, text);
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "@"})
        @DisplayName("Patterns should not match empty text")
        void testEmptyText(String pattern) {
            assertDoesNotMatch(pattern, "");
        }
    }
}
