package io.codecrafters.grep.integration;

import io.codecrafters.grep.testutil.BaseRegexTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end integration tests that verify complete regex workflows.
 * These tests ensure all components work together correctly.
 */
@DisplayName("End-to-End Integration Tests")
class EndToEndTest extends BaseRegexTest {

    @Nested
    @DisplayName("Real-World Pattern Matching")
    class RealWorldPatternMatching {

        @ParameterizedTest
        @CsvSource({
            "\\d, Version 2.0 released, true",
            "\\d, No version info, false",
            "[aeiou], Hello World, true",
            "[aeiou], rhythm, false"
        })
        @DisplayName("Common patterns should work with realistic text")
        void testRealWorldPatterns(String pattern, String text, boolean shouldMatch) {
            if (shouldMatch) {
                assertMatches(pattern, text);
            } else {
                assertDoesNotMatch(pattern, text);
            }
        }

        @Test
        @DisplayName("Log parsing patterns should work")
        void testLogParsingPatterns() {
            String logLine = "2023-01-01 ERROR: Database connection failed";
            
            assertMatches("\\d", logLine);  // Date contains digits
            assertMatches(":", logLine);      // Colon separator
        }
    }

    @Nested
    @DisplayName("Workflow Integration")
    class WorkflowIntegration {

        @Test
        @DisplayName("Pattern reuse should work correctly")
        void testPatternReuse() {
            var pattern = compilePattern("\\d");
            
            // Same pattern should work on different texts
            assertTrue(pattern.matches("123"));
            assertTrue(pattern.matches("version2"));
            assertFalse(pattern.matches("hello"));
            assertFalse(pattern.matches(""));
        }
    }

    @Nested
    @DisplayName("Data Processing Simulation")
    class DataProcessingSimulation {

        @Test
        @DisplayName("Filter numeric data")
        void testNumericDataFiltering() {
            List<String> data = Arrays.asList(
                "ID123", "NAME", "456", "USER789", "ADMIN", "999"
            );
            
            // Count items containing digits
            long numericCount = data.stream()
                .mapToLong(item -> {
                    try {
                        return compilePattern("\\d").matches(item) ? 1 : 0;
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .sum();
            
            assertEquals(4, numericCount); // ID123, 456, USER789, 999
        }

        @Test
        @DisplayName("Validate input data with realistic scenarios")
        void testInputDataValidation() {
            List<PatternTextPair> testCases = Arrays.asList(
                new PatternTextPair("[aeiou]", "hello", true),
                new PatternTextPair("[aeiou]", "rhythm", false),
                new PatternTextPair(":", "timestamp:12:34", true),
                new PatternTextPair("@", "user@domain.com", true)
            );
            
            for (PatternTextPair testCase : testCases) {
                if (testCase.shouldMatch) {
                    assertMatches(testCase.pattern, testCase.text);
                } else {
                    assertDoesNotMatch(testCase.pattern, testCase.text);
                }
            }
        }
    }
}
