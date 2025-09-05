package io.codecrafters.grep.patterns;

import io.codecrafters.grep.testutil.BaseRegexTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Comprehensive tests for word pattern (\w) functionality.
 * Tests that \w matches word characters: letters, digits, and underscore.
 */
@DisplayName("Word Pattern Tests")
class WordPatternTest extends BaseRegexTest {

    private static final String WORD_PATTERN = "\\w";

    @Nested
    @DisplayName("Word Pattern Correctness")
    class WordPatternCorrectness {

        @ParameterizedTest
        @ValueSource(strings = {"a", "Z", "5", "_", "hello_world", "test123", "Class1", "var_name"})
        @DisplayName("\\w should match text containing word characters")
        void testWordMatches(String text) {
            assertMatches(WORD_PATTERN, text);
        }

        @ParameterizedTest
        @ValueSource(strings = {"!", "@", " ", ".", "()[]", "#$%", ""})
        @DisplayName("\\w should not match text without word characters")
        void testWordNonMatches(String text) {
            assertDoesNotMatch(WORD_PATTERN, text);
        }
    }

    @Nested
    @DisplayName("Character Classification")
    class CharacterClassification {

        @ParameterizedTest
        @ValueSource(chars = {'a', 'Z', '5', '_'})
        @DisplayName("\\w should match word characters (letters, digits, underscore)")
        void testWordCharacters(char wordChar) {
            assertMatches(WORD_PATTERN, String.valueOf(wordChar));
        }

        @ParameterizedTest
        @ValueSource(chars = {'!', '@', ' ', '\t'})
        @DisplayName("\\w should not match special characters or whitespace")
        void testNonWordCharacters(char nonWordChar) {
            assertDoesNotMatch(WORD_PATTERN, String.valueOf(nonWordChar));
        }
    }

    @Test
    @DisplayName("\\w should match first word character in mixed content")
    void testWordCharacterInMixedContent() {
        assertMatches(WORD_PATTERN, "!@#a$%^");
        assertMatches(WORD_PATTERN, "   Z   ");
        assertMatches(WORD_PATTERN, "+++1---");
        assertMatches(WORD_PATTERN, "()_[]");
    }

    @Test
    @DisplayName("\\w should match when word character is at different positions")
    void testWordCharacterPositions() {
        assertMatches(WORD_PATTERN, "a###");  // at start
        assertMatches(WORD_PATTERN, "###a");  // at end
        assertMatches(WORD_PATTERN, "#a#");   // in middle
    }
}
