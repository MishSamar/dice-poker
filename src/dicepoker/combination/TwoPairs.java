package dicepoker.combination;
import dicepoker.util.DiceUtils;
import java.util.Collections;
import java.util.List;

import dicepoker.model.CombinationType;

public class TwoPairs implements Combination {
    public CombinationType getType() {
        return CombinationType.TWO_PAIRS;
    }

    public boolean matches(List<Integer> d) {
        List<Integer> pairs = DiceUtils.kinds(d, 2);
        return pairs.size() >= 2;
    }

    public int score(List<Integer> d) {
        return DiceUtils.kinds(d, 2).stream().mapToInt(i -> i).sum() * 2;

//        List<Integer> ps = DiceUtils.kinds(d, 2);
//        if (ps.size() < 2) return 0;
//        ps.sort(Collections.reverseOrder());
//        return ps.get(0) * 2 + ps.get(1) * 2;
    }
}
