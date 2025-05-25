package dicepoker.combination;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

public class ThreeOfKind implements Combination {
    public CombinationType getType() {
        return CombinationType.THREE_OF_KIND;
    }

    public boolean matches(List<Integer> d) {
        return DiceUtils.hasCountAtLeast(d, 3);
    }

    public int score(List<Integer> d) {
        return DiceUtils.maxOfKind(d, 3) * 3;
    }
}


