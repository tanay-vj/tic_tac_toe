package controllers;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotCountException;
import exceptions.PlayersCountDimensionMismatchException;
import models.Game;
import models.Move;
import models.Player;
import strategies.WinningStrategy;

import java.util.List;

public class GameController {
    /*How will client interactions take place ?
    Start the game - Enter all details
    makeMove() - user to enter location
    displayBoard()
    checkStatus() - Check if someone has won
    undo()
    getWinner() - Display winner if someone has won

     */

    public Game startGame(int dimension, List<Player> players, List<WinningStrategy> strategies) throws DuplicateSymbolException, MoreThanOneBotCountException, PlayersCountDimensionMismatchException {
        return Game.getBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(strategies)
                .build();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public void printGame(Game game) {
        game.print();
    }

    public void undo(Game game) {
        game.undo();
    }
}
