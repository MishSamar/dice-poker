package dicepoker.combination;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

/**
 * Пример реализации комбинации "Пара".
 */
public class Pair implements Combination {
    @Override
    public CombinationType getType() {
        return CombinationType.ONE_PAIR;
    }

    @Override
    public boolean matches(List<Integer> dice) {
        Map<Integer, Long> counts = DiceUtils.countFaces(dice);
        return counts.values().stream().anyMatch(c -> c >= 2);
    }

    @Override
    public int score(List<Integer> dice) {
        // Выбираем пару максимального достоинства
        return dice.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .mapToInt(e -> e.getKey() * 2)
                .max()
                .orElse(0);
    }
}
