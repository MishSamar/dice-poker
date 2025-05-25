package dicepoker.combination;

import java.util.List;

import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

public class FiveOfKind implements Combination {
    public CombinationType getType() {
        return CombinationType.FIVE_OF_KIND;
    }

    public boolean matches(List<Integer> d) {
        return DiceUtils.hasCountAtLeast(d, 5);
    }

    public int score(List<Integer> d) {
        return DiceUtils.maxOfKind(d, 5) * 5;
    }
}
