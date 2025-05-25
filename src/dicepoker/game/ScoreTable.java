package dicepoker.game;

import java.util.EnumMap;
import java.util.Map;

import dicepoker.config.GameConfig;
import dicepoker.model.CombinationType;

/**
 * Таблица очков игрока.
 */
public class ScoreTable {
    private final Map<CombinationType, Integer> scores = new EnumMap<>(CombinationType.class);

    public boolean isComplete() {
        return scores.size() == GameConfig.AVAILABLE_COMBINATIONS.size();
    }

    public void record(CombinationType t, int s) {
        scores.put(t, s);
    }

    public boolean used(CombinationType t) {
        return scores.containsKey(t);
    }

    public int total() {
        return scores.values().stream().mapToInt(i -> i).sum() + bonus();
    }

    private int bonus() {
        int sum = 0;
        for (var t : CombinationType.values())
            if (t.name().matches("ONES|TWOS|THREES|FOURS|FIVES|SIXES"))
                sum += scores.getOrDefault(t, 0);
        return sum >= 63 ? 50 : 0;
    }

    public void printTable() {
        for (var t : GameConfig.AVAILABLE_COMBINATIONS) {
            String v = used(t) ? scores.get(t) + "" : "-";
            System.out.printf("%15s: %s\n", t, v);
        }
        System.out.println("Bonus: " + bonus());
        System.out.println("Total: " + total());
    }
}
