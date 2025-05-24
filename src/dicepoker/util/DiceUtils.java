package dicepoker.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилитарные методы для работы с костями.
 */
public class DiceUtils {
    public static Map<Integer, Long> countFaces(List<Integer> dice) {
        return dice.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }
}
