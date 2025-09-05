package io.codecrafters.grep.testutil;

import io.codecrafters.grep.matcher.RegexPattern;
import io.codecrafters.grep.parser.ParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Base test class providing common testing utilities for regex pattern tests.
 * Reduces code duplication and provides consistent assertion methods.
 */
public abstract class BaseRegexTest {

    /**
     * Asserts that a pattern matches the given text.
     *
     * @param pattern the regex pattern to compile
     * @param text    the text to match against
     */
    protected void assertMatches(String pattern, String text) {
        try {
            RegexPattern compiledPattern = RegexPattern.compile(pattern);
            assertTrue(compiledPattern.matches(text), 
                String.format("Pattern '%s' should match text '%s'", pattern, text));
        } catch (ParseException e) {
            fail(String.format("Failed to compile pattern '%s': %s", pattern, e.getMessage()));
        }
    }

    /**
     * Asserts that a pattern does not match the given text.
     *
     * @param pattern the regex pattern to compile
     * @param text    the text to match against
     */
    protected void assertDoesNotMatch(String pattern, String text) {
        try {
            RegexPattern compiledPattern = RegexPattern.compile(pattern);
            assertFalse(compiledPattern.matches(text), 
                String.format("Pattern '%s' should not match text '%s'", pattern, text));
        } catch (ParseException e) {
            fail(String.format("Failed to compile pattern '%s': %s", pattern, e.getMessage()));
        }
    }

    /**
     * Asserts that compiling a pattern throws a ParseException.
     *
     * @param invalidPattern the invalid pattern that should fail to compile
     */
    protected void assertParseException(String invalidPattern) {
        assertThrows(ParseException.class, () -> RegexPattern.compile(invalidPattern),
            String.format("Pattern '%s' should throw ParseException", invalidPattern));
    }

    /**
     * Asserts that a compiled pattern throws a RuntimeException when matching against null text.
     *
     * @param pattern the pattern to compile and test
     */
    protected void assertRuntimeExceptionOnNullText(String pattern) {
        assertDoesNotThrow(() -> {
            RegexPattern compiledPattern = RegexPattern.compile(pattern);
            assertThrows(RuntimeException.class, () -> compiledPattern.matches(null),
                String.format("Pattern '%s' should throw RuntimeException for null text", pattern));
        });
    }

    /**
     * Compiles a pattern without throwing exceptions, for use in test setup.
     *
     * @param pattern the pattern to compile
     * @return the compiled RegexPattern
     */
    protected RegexPattern compilePattern(String pattern) {
        try {
            return RegexPattern.compile(pattern);
        } catch (ParseException e) {
            fail(String.format("Failed to compile pattern '%s': %s", pattern, e.getMessage()));
            return null; // Never reached due to fail()
        }
    }

    /**
     * Asserts that pattern compilation and matching completes within a reasonable time.
     * Useful for performance regression testing.
     *
     * @param pattern the pattern to test
     * @param text the text to match against
     * @param maxMillis maximum allowed time in milliseconds
     */
    protected void assertPerformance(String pattern, String text, long maxMillis) {
        long startTime = System.currentTimeMillis();
        try {
            RegexPattern compiledPattern = RegexPattern.compile(pattern);
            compiledPattern.matches(text);
            long elapsed = System.currentTimeMillis() - startTime;
            assertTrue(elapsed <= maxMillis, 
                String.format("Pattern '%s' took %dms, expected <= %dms", pattern, elapsed, maxMillis));
        } catch (ParseException e) {
            fail(String.format("Failed to compile pattern '%s': %s", pattern, e.getMessage()));
        }
    }

    /**
     * Test data holder for pattern and text pairs.
     * Useful for organizing test data in a structured way.
     */
    protected static class PatternTextPair {
        public final String pattern;
        public final String text;
        public final boolean shouldMatch;

        public PatternTextPair(String pattern, String text, boolean shouldMatch) {
            this.pattern = pattern;
            this.text = text;
            this.shouldMatch = shouldMatch;
        }

        @Override
        public String toString() {
            return String.format("PatternTextPair{pattern='%s', text='%s', shouldMatch=%s}", 
                pattern, text, shouldMatch);
        }
    }
}
