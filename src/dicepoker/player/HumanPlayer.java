package dicepoker.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import dicepoker.combination.Combination;
import dicepoker.combination.CombinationFactory;
import dicepoker.config.GameConfig;
import dicepoker.model.CombinationType;
import dicepoker.util.DiceUtils;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void play(Scanner in) {
        List<Integer> dice = DiceUtils.roll(5);
        int rolls = 0;
        boolean firstTry = false;
        while (rolls < 2 && !firstTry) {
            if (GameConfig.SORTING_VALUES) {
                dice.sort(Comparator.comparing(Integer::valueOf));
            }
            System.out.println("Dice: " + dice);
            List<Integer> idxs = List.of();
            while (true) { // Ввод прощающий ошибки
                System.out.print("Enter positions to reroll (e.g. 1 3), or empty to keep: ");
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    firstTry = true;
                    break;
                }

                try {
                    idxs = Arrays.stream(line.split("\\s+"))
                            .map(s -> {
                                int num = Integer.parseInt(s);
                                if (num < 1 || num > 5) {
                                    throw new IllegalArgumentException("Positions must be between 1-5");
                                }
                                return num;
                            })
                            .toList();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Use numbers separated by spaces (e.g. '1 3 5')");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            List<Integer> newDice = new ArrayList<>(dice);
            for (int i : idxs) {
                newDice.set(i - 1, DiceUtils.roll(1).getFirst());
            }
            dice = newDice;
            rolls++;
        }
        System.out.println("Final: " + dice);
        scoreTable.printTable();
        System.out.print("Enter combination to fill: ");
        CombinationType choice;
        while (true) {
            System.out.print("Enter combination to fill: ");
            String input = in.nextLine().trim().toUpperCase();

            try {
                choice = CombinationType.valueOf(input);
                List<Combination> combos = CombinationFactory.createAll();
                List<Integer> finalDice = dice;
                CombinationType finalChoice = choice;
                boolean finalFirstTry = firstTry;
                combos.stream().filter(c -> finalChoice == c.getType()).findFirst().ifPresent(c -> {
                    int sc = c.score(finalDice);
                    if (finalFirstTry && GameConfig.FIRST_THROW_DOUBLE && !finalChoice.name().matches(
                            "ONES|TWOS" +
                                    "|THREES|FOURS|FIVES" +
                                    "|SIXES"))
                        sc *= 2;
                    scoreTable.record(finalChoice, sc);
                    System.out.println("Scored " + sc);
                });
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid combination! Available options: ");
                Arrays.stream(CombinationType.values())
                        .forEach(ct -> System.out.print(ct.name() + " "));
                System.out.println("\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
