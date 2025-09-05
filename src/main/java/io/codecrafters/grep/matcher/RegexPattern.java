package io.codecrafters.grep.matcher;

import io.codecrafters.grep.parser.PatternParser;
import io.codecrafters.grep.patterns.PatternElement;

import java.util.List;

public class RegexPattern {

    private final List<PatternElement> patternElements;

    private RegexPattern(String pattern) {
        this.patternElements = PatternParser.parse(pattern);
    }

    public static RegexPattern compile(String pattern) {
        return new RegexPattern(pattern);
    }

    public boolean matches(String text) {
        PatternElement patternElement = patternElements.getFirst();

        boolean matches = false;
        for (char ch : text.toCharArray()) {
            if (patternElement.match(ch)) {
                matches = true;
                break;
            }
        }
        return matches;
    }

}
