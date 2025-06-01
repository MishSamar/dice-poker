package dicepoker.player;

import java.util.*;
import dicepoker.combination.Combination;
import dicepoker.combination.CombinationFactory;
import dicepoker.config.GameConfig;
import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

/**
 * HumanPlayer — игрок, управляющийся консольным вводом.
 *
 * <p>Игровой процесс:
 * <ol>
 *   <li>Бросок пяти кубиков.</li>
 *   <li>До двух перебросов: ввод позиций кубиков для переброса.</li>
 *   <li>Показ таблицы комбинаций:
 *     <ul>
 *       <li>«(empty)» — ещё не занятая комбинация, показываем потенциальные очки.</li>
 *       <li>«(used: X)» — уже использована, очки X.</li>
 *     </ul>
 *   </li>
 *   <li>Можно выбрать любую комбинацию, даже если результат не совпадает (вычёркивание):
 *       при вычёркивании начисляется 0 очков.</li>
 *   <li>Если правило FIRST_THROW_DOUBLE включено и не было переброса после первого броска,
 *       и комбинация не из «школы», очки удваиваются.</li>
 * </ol>
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Основной метод хода: броски, показ комбинаций, выбор и запись результата.
     *
     * @param in объект Scanner для чтения ввода
     */
    @Override
    public void play(Scanner in) {
        // 1) Первый бросок
        List<Integer> dice = DiceUtils.roll(5);
        boolean firstNoReroll = false;
        int rolls = 0;

        // до двух перебросов
        while (rolls < 2) {
            if (GameConfig.SORTING_VALUES) {
                dice.sort(Comparator.naturalOrder());
            }
            System.out.println("Dice: " + dice);
            System.out.print("Enter positions to reroll (1–5), or empty to keep all: ");
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                if (rolls == 0) {
                    firstNoReroll = true;
                }
                break;
            }

            // парсим ввод
            List<Integer> idxs = new ArrayList<>();
            try {
                for (String token : line.split("\\s+")) {
                    int pos = Integer.parseInt(token);
                    if (pos < 1 || pos > 5) {
                        throw new IllegalArgumentException("Positions must be 1–5");
                    }
                    idxs.add(pos - 1);
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
                continue;  // повторяем тот же бросок
            }

            // выполняем переброс
            for (int i : idxs) {
                dice.set(i, DiceUtils.roll(1).get(0));
            }
            rolls++;
        }

        System.out.println("Final: " + dice);

        List<Combination> combos = CombinationFactory.createAll();

        // 2) Показ таблицы комбинаций
        if (GameConfig.HINTS) {
            System.out.println("\nAvailable Combinations:");
            for (int i = 0; i < combos.size(); i++) {
                Combination comb = combos.get(i);
                CombinationType type = comb.getType();
                boolean used = scoreTable.used(type);
                int prevScore = used ? scoreTable.getScore(type) : comb.score(dice);
                String status = used
                        ? "(used: " + prevScore + ")"
                        : "(empty, potential: " + (comb.matches(dice) ? comb.score(dice) : 0) + ")";  // TODO сделать нормальный score у всех
                System.out.printf("%2d. %-15s %s%n", i + 1, type.name(), status);
            }
        } else {
            scoreTable.printTable();
        }

        // 3) Выбор комбинации
        CombinationType choiceType = null;
        while (choiceType == null) {
            System.out.print("Choose combination number (1–" + combos.size() + "): ");
            String input = in.nextLine().trim();
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= combos.size()) {
                    throw new IndexOutOfBoundsException();
                }
                combinationLoop:
                {
                    CombinationType ct = combos.get(idx).getType();
                    if (scoreTable.used(ct)) {
                        System.out.println("Already used. You may only cross out an unused combination.");
                        break combinationLoop;
                    }
                    choiceType = ct;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Number out of range.");
            }
        }

        // 4) Фиксация очков или вычёркивание
        CombinationType finalChoiceType = choiceType;
        Combination chosen = combos.stream()
                .filter(c -> c.getType() == finalChoiceType)
                .findFirst().get();

        // спрашиваем, вычёркивать ли?
        boolean crossOut = false;
        while (true) {
            System.out.print("Cross out (for zero)? [y/N]: ");
            String ans = in.nextLine().trim().toLowerCase();
            if (ans.isEmpty() || ans.equals("n")) {
                break;
            } else if (ans.equals("y")) {
                crossOut = true;
                break;
            } else {
                System.out.println("Enter 'y' or 'n'.");
            }
        }

        int score = crossOut ? 0 : chosen.score(dice);

        // бонус за первый бросок
        if (!crossOut
                && firstNoReroll
                && GameConfig.FIRST_THROW_DOUBLE
                && !choiceType.name().matches("ONES|TWOS|THREES|FOURS|FIVES|SIXES")) {
            score *= 2;
            System.out.println("First throw bonus applied! Score doubled.");
        }

        scoreTable.record(choiceType, score);
        System.out.println("Recorded " + score + " in " + choiceType.name() + "\n");
    }
}
