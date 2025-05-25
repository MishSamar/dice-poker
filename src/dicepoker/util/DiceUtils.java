package dicepoker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Утилитарные методы для работы с костями.
 */
public class DiceUtils {
    private static final Random RAND = new Random();

    /**
     * Бросок заданного числа костей.
     */
    public static List<Integer> roll(int count) {
        List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(RAND.nextInt(6) + 1);
        }
        return result;
    }

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
