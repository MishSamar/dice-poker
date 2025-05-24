package dicepoker.combination;

import java.util.List;

import dicepoker.model.CombinationType;

/**
 * Общий интерфейс для всех комбинаций.
 */
public interface Combination {
    CombinationType getType();
    /**
     * Проверить, подходит ли текущий бросок под комбинацию.
     */
    boolean matches(List<Integer> dice);
    /**
     * Рассчитать очки за комбинацию.
     */
    int score(List<Integer> dice);
}
