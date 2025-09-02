import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-E")) {
            System.out.println("Usage: ./your_program.sh -E <pattern>");
            System.exit(1);
        }

        String pattern = args[1];
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();

        if (matchPattern(inputLine, pattern)) {
            System.exit(0);
        } else {
            System.exit(1);
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
        } else {
            throw new RuntimeException("Unhandled pattern: " + pattern);
        }
    }

    private static boolean isLatin(int ch) {
        return Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.BASIC_LATIN;
    }

}
