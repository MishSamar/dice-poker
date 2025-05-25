package dicepoker.config;

import java.util.*;

import dicepoker.model.CombinationType;

/**
 * Константы и конфигурация правил игры.
 */
public final class GameConfig {
    /**
     * Вкл/выкл правила удвоенного первого броска.
     */
    public static final boolean FIRST_THROW_DOUBLE = true;

    public static final boolean SORTING_VALUES = true;

    /**
     * Набор доступных комбинаций. Можно менять, убавлять или добавлять.
     */
    public static final List<CombinationType> AVAILABLE_COMBINATIONS =
            new ArrayList<>(Arrays.asList(CombinationType.values()));

    private GameConfig() { /* prevent instantiation */ }
}

