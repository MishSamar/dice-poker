package dicepoker.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dicepoker.combination.Combination;
import dicepoker.combination.CombinationFactory;
import dicepoker.config.GameConfig;
import dicepoker.dice.DiceCup;
import dicepoker.model.CombinationType;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void play(DiceCup cup, Scanner in) {
        List<Integer> dice = cup.roll(5);
        int rolls = 0;
        while (rolls < 2) {
            System.out.println("Dice: " + dice);
            System.out.print("Enter positions to reroll (e.g. 1 3), or empty to keep: ");
            String line = in.nextLine().trim();
            if (line.isEmpty()) break;
            var idxs = Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList();
            List<Integer> newDice = new ArrayList<>(dice);
            for (int i : idxs) {
                if (i >= 1 && i <= 5) {
                    newDice.set(i - 1, cup.roll(1).getFirst());
                }
            }
            dice = newDice;
            rolls++;
        }
        System.out.println("Final: " + dice);
        scoreTable.printTable();
        System.out.print("Enter combination to fill: ");
        CombinationType choice = CombinationType.valueOf(in.nextLine().trim());
        List<Combination> combos = CombinationFactory.createAll();
        List<Integer> finalDice = dice;
        int finalRolls = rolls;
        combos.stream().filter(c -> c.getType() == choice).findFirst().ifPresent(c -> {
            int sc = c.score(finalDice);
            if (finalRolls == 0 && GameConfig.FIRST_THROW_DOUBLE && !choice.name().matches("ONES|TWOS|THREES|FOURS|FIVES|SIXES"))
                sc *= 2;
            scoreTable.record(choice, sc);
            System.out.println("Scored " + sc);
        });
    }
}