package dicepoker;

import java.util.*;

/**
 * Константы и конфигурация правил игры.
 */
public final class GameConfig {
    /** Вкл/выкл правила удвоенного первого броска. */
    public static final boolean FIRST_THROW_DOUBLE = true;

    /** Набор доступных комбинаций. Можно менять, убавлять или добавлять. */
    public static final List<CombinationType> AVAILABLE_COMBINATIONS =
            new ArrayList<>(Arrays.asList(
                    CombinationType.ONE_PAIR,
                    CombinationType.TWO_PAIRS,
                    CombinationType.THREE_OF_KIND,
                    CombinationType.FOUR_OF_KIND,
                    CombinationType.FIVE_OF_KIND,
                    CombinationType.FULL_HOUSE,
                    CombinationType.SMALL_STRAIGHT,
                    CombinationType.LARGE_STRAIGHT,
                    CombinationType.CHANCE,
                    CombinationType.ONES,
                    CombinationType.TWOS,
                    CombinationType.THREES,
                    CombinationType.FOURS,
                    CombinationType.FIVES,
                    CombinationType.SIXES
            ));

    private GameConfig() { /* prevent instantiation */ }
}

