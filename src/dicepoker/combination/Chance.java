package dicepoker.combination;

import java.util.List;

import dicepoker.model.CombinationType;

public class Chance implements Combination {
    public CombinationType getType() {
        return CombinationType.CHANCE;
    }

    public boolean matches(List<Integer> d) {
        return true;
    }

    public int score(List<Integer> d) {
        return d.stream().mapToInt(i -> i).sum();
    }
}
