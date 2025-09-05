package io.codecrafters.grep.performance;

import io.codecrafters.grep.testutil.BaseRegexTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Performance tests to ensure regex patterns compile and match efficiently.
 * These tests help catch performance regressions.
 */
@DisplayName("Performance Tests")
class PerformanceTest extends BaseRegexTest {

    private static final long REASONABLE_TIME_MS = 100;

    @Nested
    @DisplayName("Pattern Compilation Performance")
    class PatternCompilationPerformance {

        @Test
        @DisplayName("Complex character classes should compile efficiently")
        void testComplexCharacterClasses() {
            assertPerformance("[abcdefghijklmnopqrstuvwxyz]", "m", REASONABLE_TIME_MS);
            assertPerformance("[^0123456789]", "a", REASONABLE_TIME_MS);
        }
    }

    @Nested
    @DisplayName("Text Matching Performance")
    class TextMatchingPerformance {

        @Test
        @DisplayName("Matching against long text should be efficient")
        void testLongTextMatching() {
            String longText = "a".repeat(10000);
            assertPerformance("a", longText, REASONABLE_TIME_MS);
        }

        @Test
        @DisplayName("Character class matching should be efficient")
        void testCharacterClassMatching() {
            String longText = "x".repeat(1000) + "a" + "x".repeat(1000);
            assertPerformance("[abc]", longText, REASONABLE_TIME_MS);
        }

        @Test
        @DisplayName("Negative character class matching should be efficient")
        void testNegativeCharacterClassMatching() {
            String longText = "x".repeat(1000) + "a" + "x".repeat(1000);
            assertPerformance("[^xyz]", longText, REASONABLE_TIME_MS);
        }
    }

    @Nested
    @DisplayName("Edge Case Performance")
    class EdgeCasePerformance {

        @Test
        @DisplayName("Pattern with no matches should fail fast")
        void testNoMatchPerformance() {
            String longText = "x".repeat(10000);
            assertPerformance("z", longText, REASONABLE_TIME_MS);
        }

        @Test
        @DisplayName("Empty text matching should be instant")
        void testEmptyTextPerformance() {
            assertPerformance("a", "", 10); // Should be very fast
        }
    }
}
