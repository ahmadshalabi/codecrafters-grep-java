package io.codecrafters.grep.patterns;

import io.codecrafters.grep.testutil.BaseRegexTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Comprehensive tests for character class pattern functionality.
 * Tests positive [abc] and negative [^abc] character classes.
 */
@DisplayName("Character Class Pattern Tests")
class CharacterClassPatternTest extends BaseRegexTest {

    @Nested
    @DisplayName("Character Class Pattern Correctness")
    class CharacterClassPatternCorrectness {

        @ParameterizedTest
        @CsvSource({
            "[abc], a",
            "[567], 7",
            "[xyz], y",
            "[!@#], @"
        })
        @DisplayName("Positive character classes should match included characters")
        void testPositiveCharacterClasses(String pattern, String text) {
            assertMatches(pattern, text);
        }

        @ParameterizedTest
        @CsvSource({
            "[abc], x",
            "[abc], 5",
            "[567], a",
            "[xyz], 1"
        })
        @DisplayName("Positive character classes should not match excluded characters")
        void testPositiveCharacterClassNonMatches(String pattern, String text) {
            assertDoesNotMatch(pattern, text);
        }

        @ParameterizedTest
        @CsvSource({
            "[^abc], x",
            "[^abc], 5",
            "[^567], a"
        })
        @DisplayName("Negative character classes should match excluded characters")
        void testNegativeCharacterClasses(String pattern, String text) {
            assertMatches(pattern, text);
        }

        @ParameterizedTest
        @CsvSource({
            "[^abc], a",
            "[^567], 5",
            "[^xyz], y"
        })
        @DisplayName("Negative character classes should not match included characters")
        void testNegativeCharacterClassNonMatches(String pattern, String text) {
            assertDoesNotMatch(pattern, text);
        }
    }

    @Nested
    @DisplayName("Vowel Character Classes")
    class VowelCharacterClasses {

        @ParameterizedTest
        @ValueSource(chars = {'a', 'e', 'i', 'o', 'u'})
        @DisplayName("Positive vowel class should match vowels")
        void testPositiveVowelClass(char vowel) {
            assertMatches("[aeiou]", String.valueOf(vowel));
        }

        @ParameterizedTest
        @ValueSource(chars = {'a', 'e', 'i', 'o', 'u'})
        @DisplayName("Negative vowel class should not match vowels")
        void testNegativeVowelClass(char vowel) {
            assertDoesNotMatch("[^aeiou]", String.valueOf(vowel));
        }

        @ParameterizedTest
        @ValueSource(chars = {'b', 'x', 'z'})
        @DisplayName("Negative vowel class should match consonants")
        void testNegativeVowelClassMatchesConsonants(char consonant) {
            assertMatches("[^aeiou]", String.valueOf(consonant));
        }
    }

    @Nested
    @DisplayName("Digit Character Classes")
    class DigitCharacterClasses {

        @ParameterizedTest
        @ValueSource(chars = {'0', '5', '9'})
        @DisplayName("Digit character class should match digits")
        void testDigitCharacterClass(char digit) {
            assertMatches("[0123456789]", String.valueOf(digit));
        }

        @ParameterizedTest
        @ValueSource(chars = {'a', 'Z', '@'})
        @DisplayName("Digit character class should not match non-digits")
        void testDigitCharacterClassNonMatches(char nonDigit) {
            assertDoesNotMatch("[0123456789]", String.valueOf(nonDigit));
        }
    }

    @Nested
    @DisplayName("Special Character Classes")
    class SpecialCharacterClasses {

        @ParameterizedTest
        @ValueSource(chars = {'!', '#', '%'})
        @DisplayName("Special character class should match included symbols")
        void testSpecialCharacterClass(char symbol) {
            assertMatches("[!@#$%]", String.valueOf(symbol));
        }

        @ParameterizedTest
        @ValueSource(chars = {'^', '&', '('})
        @DisplayName("Special character class should not match excluded symbols")
        void testSpecialCharacterClassNonMatches(char symbol) {
            assertDoesNotMatch("[!@#$%]", String.valueOf(symbol));
        }
    }

    @Nested
    @DisplayName("Character Class Boundaries")
    class CharacterClassBoundaries {

        @ParameterizedTest
        @CsvSource({
            "[a], a",
            "[Z], Z",
            "[5], 5"
        })
        @DisplayName("Single character classes should work")
        void testSingleCharacterClass(String pattern, String text) {
            assertMatches(pattern, text);
        }

        @ParameterizedTest
        @ValueSource(strings = {"[abc]", "[xyz]", "[123]"})
        @DisplayName("Character classes should not match empty text")
        void testCharacterClassWithEmptyText(String pattern) {
            assertDoesNotMatch(pattern, "");
        }
    }

    @Nested
    @DisplayName("Character Class Consistency")
    class CharacterClassConsistency {

        @ParameterizedTest
        @CsvSource({
            "[abc], abcdef",
            "[xyz], xyzetc",
            "[123], 123456"
        })
        @DisplayName("Character classes should match when character present in longer text")
        void testCharacterClassInLongerText(String pattern, String text) {
            assertMatches(pattern, text);
        }
    }
}
