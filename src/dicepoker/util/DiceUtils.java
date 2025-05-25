package dicepoker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Утилитарные методы для работы с костями.
 * Содержит функции для генерации бросков и анализа комбинаций.
 */
public class DiceUtils {

    private static final Random RAND = new Random();

    /**
     * Выполняет бросок заданного количества шестигранных костей.
     *
     * @param count Количество кубиков для броска.
     * @return Список результатов броска, где каждый элемент — значение от 1 до 6.
     */
    public static List<Integer> roll(int count) {
        List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(RAND.nextInt(6) + 1);
        }
        return result;
    }

    /**
     * Подсчитывает количество вхождений каждого значения в списке бросков кубиков.
     *
     * @param dices Список значений, выпавших на кубиках.
     * @return Map, где ключ — значение кубика, а значение — количество его вхождений.
     */
    public static Map<Integer, Long> countFaces(List<Integer> dices) {
        return dices.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }

    /**
     * Проверяет, встречается ли какое-либо значение кубика хотя бы {@code minRepetitions} раз.
     *
     * @param dices Список значений кубиков.
     * @param minRepetitions Минимальное количество повторений.
     * @return {@code true}, если какое-либо значение встречается {@code minRepetitions} раз или более; иначе {@code false}.
     */
    public static boolean hasCountAtLeast(List<Integer> dices, int minRepetitions) {
        return countFaces(dices).values().stream().anyMatch(c -> c >= minRepetitions);
    }

    /**
     * Находит максимальное значение кубика, которое встречается как минимум {@code minRepetitions} раз.
     *
     * @param dices Список значений кубиков.
     * @param minRepetitions Минимальное количество повторений.
     * @return Максимальное значение кубика, удовлетворяющее условию, или {@code 0}, если такого нет.
     */
    public static int maxOfKind(List<Integer> dices, int minRepetitions) {
        return countFaces(dices).entrySet().stream()
                .filter(e -> e.getValue() >= minRepetitions)
                .mapToInt(Map.Entry::getKey).max().orElse(0);
    }

    /**
     * Возвращает список всех значений кубиков, которые встречаются как минимум {@code minRepetitions} раз.
     *
     * @param dices Список значений кубиков.
     * @param minRepetitions Минимальное количество повторений.
     * @return Список значений, встречающихся не менее {@code minRepetitions} раз.
     */
    public static List<Integer> kinds(List<Integer> dices, int minRepetitions) {
        return countFaces(dices).entrySet().stream()
                .filter(e -> e.getValue() >= minRepetitions)
                .map(Map.Entry::getKey).toList();
    }
}