package dicepoker.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилитарные методы для работы с костями.
 */
public class DiceUtils {
    public static Map<Integer, Long> countFaces(List<Integer> d) {
        return d.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }

    public static boolean hasCountAtLeast(List<Integer> d, int n) {
        return countFaces(d).values().stream().anyMatch(c -> c >= n);
    }

    public static int maxOfKind(List<Integer> d, int n) {
        return countFaces(d).entrySet().stream()
                .filter(e -> e.getValue() >= n)
                .mapToInt(Map.Entry::getKey).max().orElse(0);
    }

    public static List<Integer> kinds(List<Integer> d, int n) {
        return countFaces(d).entrySet().stream()
                .filter(e -> e.getValue() >= n)
                .map(Map.Entry::getKey).toList();
    }
}
