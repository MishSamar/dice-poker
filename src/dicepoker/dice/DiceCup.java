package dicepoker.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Кубики и стакан для бросков.
 */
public class DiceCup {
    private static final Random RAND = new Random();

    /**
     * Бросок заданного числа костей.
     */
    public List<Integer> roll(int count) {
        List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(RAND.nextInt(6) + 1);
        }
        return result;
    }
}
