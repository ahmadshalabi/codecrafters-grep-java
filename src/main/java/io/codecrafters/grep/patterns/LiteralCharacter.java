package io.codecrafters.grep.patterns;

public class LiteralCharacter implements PatternElement {

    private final char character;

    public LiteralCharacter(char character) {
        this.character = character;
    }

    @Override
    public boolean match(char ch) {
        return character == ch;
    }

}
