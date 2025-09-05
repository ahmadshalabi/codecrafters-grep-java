package io.codecrafters.grep;

import io.codecrafters.grep.matcher.RegexPattern;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Validate arguments and show usage if needed
        if (args.length != 2) {
            showUsage();
            System.exit(1);
        }

        // At this point we have exactly 2 arguments
        if (!args[0].equals("-E")) {
            showUsage();  // First argument must be -E
            System.exit(1);
        }

        String pattern = args[1];
        try (Scanner scanner = new Scanner(System.in)) {
            String inputLine = scanner.nextLine();
            RegexPattern regexPattern = RegexPattern.compile(pattern);
            if (regexPattern.matches(inputLine)) {
                System.exit(0);
            } else {
                System.exit(1);
            }
        }
    }

    private static void showUsage() {
        System.out.println("Usage: ./your_program.sh -E <pattern>");
    }
}
