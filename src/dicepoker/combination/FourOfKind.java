package dicepoker.combination;

import java.util.List;

import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

public class FourOfKind implements Combination {
    @Override
    public CombinationType getType() {
        return CombinationType.FOUR_OF_KIND;
    }

    @Override
    public boolean matches(List<Integer> dice) {
        return DiceUtils.hasCountAtLeast(dice, 4);
    }
    
    @Override
    public int score(List<Integer> d) {
        return DiceUtils.maxOfKind(d, 4) * 4;
    }
}
