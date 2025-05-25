package dicepoker.combination;

import java.util.List;

import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

public class FullHouse implements Combination {
    public CombinationType getType() {
        return CombinationType.FULL_HOUSE;
    }

    public boolean matches(List<Integer> d) {
        return DiceUtils.kinds(d, 3).size() == 1 && DiceUtils.kinds(d, 2).size() == 1;
    }

    public int score(List<Integer> d) {
        return d.stream().mapToInt(i -> i).sum();
    }
}
