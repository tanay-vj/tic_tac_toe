package factories;

import models.BotDifficultyLevel;
import strategies.BotPlayingStrategy;
import strategies.EasyBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategyForLevel(BotDifficultyLevel level) {
    //write all Bot playing strategies
        return new EasyBotPlayingStrategy();
    }
}
