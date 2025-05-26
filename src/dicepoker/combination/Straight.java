package dicepoker.combination;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import dicepoker.model.CombinationType;

public class Straight implements Combination {
    private final CombinationType type;
    private final Set<Set<Integer>> patterns;

    public Straight(CombinationType t) {
        type = t;

        if (t == CombinationType.SMALL_STRAIGHT) {
            patterns = Set.of(Set.of(1, 2, 3, 4), Set.of(2, 3, 4, 5), Set.of(3, 4, 5, 6));
        } else {
            patterns = Set.of(Set.of(1, 2, 3, 4, 5), Set.of(2, 3, 4, 5, 6));
        }
    }

    public CombinationType getType() {
        return type;
    }

    public boolean matches(List<Integer> dices) {
        return patternMatching(dices) != null;
    }

    public int score(List<Integer> dices) {
        Set<Integer> pattern = patternMatching(dices);
        return pattern == null ? 0 : pattern.stream().mapToInt(i -> i).sum();
    }

    private Set<Integer> patternMatching(List<Integer> dices) {
        Set<Integer> uniq = new HashSet<>(dices);
        for (var pat : patterns) {
            if (uniq.containsAll(pat)) {
                return pat;
            }
        }

        return null;
    }
}
