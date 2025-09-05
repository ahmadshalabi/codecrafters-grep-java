package io.codecrafters.grep.error;

import io.codecrafters.grep.testutil.BaseRegexTest;
import io.codecrafters.grep.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for error handling in pattern compilation and matching.
 * Organized into logical groups for better test reporting and maintenance.
 */
@DisplayName("Error Handling Tests")
class ErrorHandlingTest extends BaseRegexTest {

    @Nested
    @DisplayName("Pattern Compilation Errors")
    class PatternCompilationErrors {

        @ParameterizedTest
        @ValueSource(strings = {
            "\\x", "\\z", "\\q",     // Invalid escape sequences
            "[]",                    // Empty character classes
            "[abc", "[^",            // Unclosed character classes
            "[^]"                    // Empty negative character class
        })
        @DisplayName("Invalid patterns should throw ParseException")
        void testInvalidPatterns(String invalidPattern) {
            assertParseException(invalidPattern);
        }

        @Test
        @DisplayName("Null pattern should throw ParseException")
        void testNullPattern() {
            assertParseException(null);
        }

        @Test
        @DisplayName("Empty pattern should throw ParseException")
        void testEmptyPattern() {
            assertParseException("");
        }
    }

    @Nested
    @DisplayName("Runtime Matching Errors")
    class RuntimeMatchingErrors {

        @Test
        @DisplayName("Null input text should throw RuntimeException")
        void testNullInputText() {
            assertRuntimeExceptionOnNullText("a");
            assertRuntimeExceptionOnNullText("\\d");
            assertRuntimeExceptionOnNullText("[abc]");
        }

        @Test
        @DisplayName("Empty input should be handled gracefully")
        void testEmptyInputHandling() {
            // These should all return false for empty input
            assertDoesNotMatch("a", "");
            assertDoesNotMatch("\\d", "");
            assertDoesNotMatch("\\w", "");
            assertDoesNotMatch("[abc]", "");
            assertDoesNotMatch("[^abc]", "");
        }
    }

    @Nested
    @DisplayName("Exception Construction and Hierarchy")
    class ExceptionConstructionAndHierarchy {

        @Test
        @DisplayName("ParseException should extend IllegalArgumentException")
        void testParseExceptionHierarchy() {
            ParseException exception = new ParseException("test message");
            assertInstanceOf(IllegalArgumentException.class, exception);
            assertInstanceOf(RuntimeException.class, exception);
        }

        @Test
        @DisplayName("ParseException should preserve message")
        void testParseExceptionMessage() {
            String message = "Custom error message";
            ParseException exception = new ParseException(message);
            assertEquals(message, exception.getMessage());
        }

        @Test
        @DisplayName("ParseException should handle null message")
        void testParseExceptionNullMessage() {
            assertDoesNotThrow(() -> new ParseException((String) null));
        }
    }

    @Nested
    @DisplayName("Boundary Conditions")
    class BoundaryConditions {

        @Test
        @DisplayName("Very long patterns should throw ParseException")
        void testVeryLongPattern() {
            String longPattern = "a".repeat(1000);
            assertParseException(longPattern);
        }

        @Test
        @DisplayName("Very long input text should be handled appropriately")
        void testVeryLongInputText() {
            String longText = "hello world ".repeat(1000);
            assertDoesNotThrow(() -> assertMatches("h", longText));
        }

        @Test
        @DisplayName("Pattern with only special characters should throw ParseException")
        void testSpecialCharacterOnlyPattern() {
            assertParseException("!@#$%^&*()");
        }

        @Test
        @DisplayName("Unicode characters should throw ParseException for unsupported characters")
        void testUnicodeCharacters() {
            assertDoesNotThrow(() -> compilePattern("α"));  // Greek alpha might work
            assertParseException("🚀"); // Emoji should fail
        }

        @Test
        @DisplayName("All ASCII printable characters should work in patterns")
        void testAllAsciiPrintableCharacters() {
            for (char c = 32; c <= 126; c++) {
                // Skip characters that have special meaning in regex
                if (c == '[' || c == ']' || c == '^' || c == '\\') {
                    continue;
                }
                final char ch = c;
                assertDoesNotThrow(() -> compilePattern(String.valueOf(ch)),
                    "Character '" + ch + "' (ASCII " + (int)ch + ") should be compilable");
            }
        }

        @Test
        @DisplayName("Single character patterns should work")
        void testSingleCharacterPattern() {
            assertMatches("a", "a");
            assertDoesNotMatch("a", "b");
        }
    }
}
