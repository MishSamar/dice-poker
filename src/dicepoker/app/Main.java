package dicepoker.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dicepoker.dice.DiceCup;
import dicepoker.player.HumanPlayer;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int n = Integer.parseInt(in.nextLine());
        List<HumanPlayer> players = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.print("Name " + i + ": ");
            players.add(new HumanPlayer(in.nextLine()));
        }
        DiceCup cup = new DiceCup();
        // поочередные ходы
        while (players.stream().anyMatch(p -> !p.getScoreTable().isComplete())) {
            for (var p : players) {
                System.out.println("--- " + p.getName() + " turn ---");
                p.play(cup, in);
            }
        }
        // результаты
        players.forEach(p -> {
            System.out.println(p.getName() + " total: " + p.getScoreTable().total());
        });
    }
}
