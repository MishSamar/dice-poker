package dicepoker.combination;

import java.util.List;

import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

public class NumberCombination implements Combination {
    private final CombinationType type;
    private final int face;

    public NumberCombination(CombinationType t, int face) {
        this.type = t;
        this.face = face;
    }

    public CombinationType getType() {
        return type;
    }

    public boolean matches(List<Integer> d) {
        return true;
    }

    public int score(List<Integer> d) {
        return (DiceUtils.countFaces(d).getOrDefault(face, 0L).intValue()) * face;
    }
}
