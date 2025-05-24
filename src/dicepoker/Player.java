package dicepoker;

/**
 * Класс, описывающий одного игрока (человека или ИИ).
 */
public abstract class Player {
    protected final String name;
    protected final ScoreTable scoreTable;

    public Player(String name) {
        this.name = name;
        this.scoreTable = new ScoreTable();
    }

    public String getName() {
        return name;
    }

    public ScoreTable getScoreTable() {
        return scoreTable;
    }

    /**
     * Выполнить ход: бросок, перебросы, выбор комбинации.
     */
    public abstract void playTurn(Game game, DiceCup cup);
}
