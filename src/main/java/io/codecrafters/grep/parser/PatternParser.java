package io.codecrafters.grep.parser;

import io.codecrafters.grep.patterns.CharacterClass;
import io.codecrafters.grep.patterns.EscapedSequence;
import io.codecrafters.grep.patterns.LiteralCharacter;
import io.codecrafters.grep.patterns.PatternElement;

import java.util.List;

public class PatternParser {

    public static List<PatternElement> parse(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new ParseException("Pattern cannot be null or empty");
        } else if (pattern.length() == 1) {
            return List.of(new LiteralCharacter(pattern.charAt(0)));
        } else if (pattern.startsWith("\\") && pattern.length() == 2) {
            char escapeChar = pattern.charAt(1);
            return switch (escapeChar) {
                case 'd' -> List.of(EscapedSequence.DIGIT);
                case 'w' -> List.of(EscapedSequence.WORD);
                default -> throw new ParseException("Unknown escape sequence: \\" + escapeChar);
            };
        } else if (pattern.startsWith("[") && pattern.endsWith("]")) {
            boolean isNegative = pattern.charAt(1) == '^';
            int startIndex = isNegative ? 2 : 1;
            int endIndex = pattern.length() - 1;
            if (startIndex >= endIndex) {
                throw new ParseException("Empty character group in pattern: " + pattern);
            }
            String charGroup = pattern.substring(startIndex, endIndex);

            return List.of(new CharacterClass(charGroup, isNegative));
        } else {
            throw new ParseException("Unsupported pattern: " + pattern);
        }
    }

}
