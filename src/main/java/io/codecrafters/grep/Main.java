package io.codecrafters.grep;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-E")) {
            System.out.println("Usage: ./your_program.sh -E <pattern>");
            System.exit(1);
        }

        String pattern = args[1];
        try (Scanner scanner = new Scanner(System.in)) {
            String inputLine = scanner.nextLine();
            if (matchPattern(inputLine, pattern)) {
                System.exit(0);
            } else {
                System.exit(1);
            }
        }

    }

    public static boolean matchPattern(String inputLine, String pattern) {
        if (pattern.length() == 1) {
            return inputLine.contains(pattern);
        } else if ("\\d".equals(pattern)) {
            return inputLine.codePoints()
                    .anyMatch(ch -> isLatin(ch) && Character.isDigit(ch));
        } else if ("\\w".equals(pattern)) {
            return inputLine.codePoints()
                    .anyMatch(ch -> isLatin(ch) && (Character.isLetterOrDigit(ch) || ch == '_'));
        } else if (pattern.startsWith("[") && pattern.endsWith("]")) {
            boolean isPositiveCharacterGroup = pattern.charAt(1) != '^';
            int startIndex = isPositiveCharacterGroup ? 1 : 2;
            int endIndex = pattern.length() - 1;
            if (startIndex >= endIndex) {
                throw new RuntimeException("Empty character group in pattern: " + pattern);
            }
            Set<Integer> characterGroup = pattern.substring(startIndex, endIndex)
                    .codePoints()
                    .boxed()
                    .collect(Collectors.toUnmodifiableSet());

            if (isPositiveCharacterGroup) {
                return inputLine.codePoints()
                        .anyMatch(characterGroup::contains);
            } else {
                return inputLine.codePoints()
                        .anyMatch(ch -> !characterGroup.contains(ch));
            }
        } else {
            throw new RuntimeException("Unhandled pattern: " + pattern);
        }
    }

    private static boolean isLatin(int ch) {
        return Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.BASIC_LATIN;
    }

}
