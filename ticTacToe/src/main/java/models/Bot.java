package models;

import factories.BotPlayingStrategyFactory;
import strategies.BotPlayingStrategy;

public class Bot extends Player {

    private BotDifficultyLevel botDifficultyLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public Bot(char symbol, String name, int id, PlayerType playerType, BotDifficultyLevel botDifficultyLevel ) {
        super(symbol, name, id, playerType);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.
                getBotPlayingStrategyForLevel(botDifficultyLevel);

    }

    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        this.botDifficultyLevel = botDifficultyLevel;
    }

    @Override
    public Cell makeMove(Board board) {
        Cell cell = botPlayingStrategy.makeMove(board);
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(this);
        return cell;
    }
}
