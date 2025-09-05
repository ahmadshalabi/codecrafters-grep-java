package io.codecrafters.grep.patterns;

import java.util.Set;
import java.util.stream.Collectors;

public class CharacterClass implements PatternElement {

    private final Set<Character> characterGroup;
    private final boolean isNegative;

    public CharacterClass(String characterGroup, boolean isNegative) {
        this.characterGroup = characterGroup.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
        this.isNegative = isNegative;
    }

    @Override
    public boolean match(char ch) {
        boolean isFound = characterGroup.contains(ch);
        return isNegative != isFound;
    }

}
