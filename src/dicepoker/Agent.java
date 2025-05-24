package dicepoker;

/**
 * Простейший ИИ-агент, выбирающий ход по эвристике или модели.
 */
public class Agent extends Player {
    public Agent(String name) {
        super(name);
    }

    @Override
    public void playTurn(Game game, DiceCup cup) {
        // TODO: здесь будет логика RL/DQN/PPO агента
    }
}
