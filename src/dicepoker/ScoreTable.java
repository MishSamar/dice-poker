package dicepoker;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Таблица очков игрока.
 */
public class ScoreTable {
    private final Map<CombinationType, Integer> scores = new EnumMap<>(CombinationType.class);
    private final Set<CombinationType> used = EnumSet.noneOf(CombinationType.class);

    public boolean isComplete() {
        return used.containsAll(GameConfig.AVAILABLE_COMBINATIONS);
    }

    public void record(CombinationType type, int score) {
        if (used.contains(type)) throw new IllegalStateException("Комбинация уже занята");
        scores.put(type, score);
        used.add(type);
    }

    public int totalScore() {
        return scores.values().stream().mapToInt(Integer::intValue).sum()
                + schoolBonus();
    }

    private int schoolBonus() {
        // Логика бонуса школы, если нужно
        return 0;
    }
}
