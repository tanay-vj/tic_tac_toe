package app;

import controllers.GameController;
import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotCountException;
import exceptions.PlayersCountDimensionMismatchException;
import models.*;
import strategies.ColWinningStrategy;
import strategies.DiagonalWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) throws DuplicateSymbolException, MoreThanOneBotCountException, PlayersCountDimensionMismatchException {
        System.out.println("Hello world!");

        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);


        int dimension = 3;
        List<Player> players = new ArrayList<>();
        players.add(new Player('X', "Tanay", 001, PlayerType.HUMAN));
        players.add(new Bot('O', "ChatGPT", 002, PlayerType.BOT, BotDifficultyLevel.EASY));

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

        Game game = gameController.startGame(dimension, players, winningStrategies);
        while (game.getGameState().equals(GameState.IN_PROGRESS)) {

            /*
            1. Print the board
            2. Anybody wants to undo the movement
            3. x's turn, ask him to make the move
             */

            gameController.printGame(game);

            System.out.println("Does anybody wants to Undo ? (Y/N)");
            String undoAnswer = scanner.next();

            if(undoAnswer.equalsIgnoreCase("Y")) {
                gameController.undo(game);
                continue;
            }

            gameController.printGame(game);
            gameController.makeMove(game);
        }

        if(GameState.SUCCESS.equals(game.getGameState())) {
            System.out.println(game.getWinner().getName() + " has won the game "+game.getWinner().getSymbol());
            game.print();
        }

        if(GameState.DRAW.equals(game.getGameState())) {
            System.out.println("It's a tie!");
        }







    }
}