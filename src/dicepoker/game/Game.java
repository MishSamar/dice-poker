package dicepoker.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dicepoker.dice.DiceCup;
import dicepoker.player.Player;

/**
 * Игровой раунд, управляющий ходами игроков.
 */
public class Game {
    private final List<Player> players;

    public Game(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public void start() {
        DiceCup cup = new DiceCup();
        // Пока не заполнены таблицы всех игроков
        while (!gameOver()) {
            for (Player p : players) {
                p.playTurn(this, cup);
            }
        }
        announceWinner();
    }

    private boolean gameOver() {
        return players.stream()
                .allMatch(p -> p.getScoreTable().isComplete());
    }

    private void announceWinner() {
        Player winner = players.stream()
                .max(Comparator.comparingInt(p -> p.getScoreTable().totalScore()))
                .orElseThrow();
        System.out.println("Winner: " + winner.getName());
    }
}
