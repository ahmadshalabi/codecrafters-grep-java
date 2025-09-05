package io.codecrafters.grep.patterns;

public enum EscapedSequence implements PatternElement {

    DIGIT {
        @Override
        public boolean match(char ch) {
            return Character.isDigit(ch);
        }
    },

    WORD {
        @Override
        public boolean match(char ch) {
            return Character.isLetterOrDigit(ch) || ch == '_';
        }
    }

}
