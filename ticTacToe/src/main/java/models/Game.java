package models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotCountException;
import exceptions.PlayersCountDimensionMismatchException;
import strategies.WinningStrategy;

import java.util.*;

public class Game {

    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game( int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(dimension);
        this.players = players;
        this.winningStrategies = winningStrategies;

        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerIndex = 0;
    }

    public static GameBuilder getBuilder() {
        return new GameBuilder();
    }

    public void print() {
        board.printBoard();
    }

    public void makeMove() {
        Player player = players.get(nextPlayerIndex);
        Cell cell = player.makeMove(board);

        Move move = new Move(cell,player);
        moves.add(move);

        if(checkWinner(move,board)) {
            gameState = GameState.SUCCESS;
            winner = player;
            return;
        }

        if(moves.size()== board.getDimension()*board.getDimension()) {
            gameState = GameState.DRAW;
            return;
        }
        nextPlayerIndex++;
        /*
        2 players (0,1)
        0->1 then 1->0 & so on
         */
        nextPlayerIndex = nextPlayerIndex % players.size();

    }

    private boolean checkWinner(Move move, Board board) {
        for(WinningStrategy winningStrategy: winningStrategies) {
            if(winningStrategy.checkWinner(board,move)) {
                return true;
            }
        }
        return false;
    }

    public void undo() {
        if(moves.size()==0) {
            System.out.println("No moves to undo");
            return;
        }

        /*
        UNDO
        1. Remove the last move
        2. Make the cell empty
        3. Decrement the corresponding count in Hashmaps present in strategies
        4. Next player will be previous player

         */
        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);

        for(WinningStrategy winningStrategy: winningStrategies) {
            winningStrategy.handleUndo(board,lastMove);
        }

        if(nextPlayerIndex==0) {
            nextPlayerIndex = players.size()-1;
        } else {
            nextPlayerIndex--;
        }
    }


    public static class GameBuilder {
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimension;

        public GameBuilder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
            this.dimension = 0;
        }

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public GameBuilder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public GameBuilder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Game build() throws MoreThanOneBotCountException, PlayersCountDimensionMismatchException, DuplicateSymbolException {
            validateBotCount();
            validateDimensionAndPlayersCount();
            validateUniqueSymbolForPlayers();
            return new Game(dimension, players, winningStrategies);
        }

        private void validateUniqueSymbolForPlayers() throws DuplicateSymbolException {
            Set<Character> symbolsSet = new HashSet<>();
            for(Player player : players) {
                if (symbolsSet.contains(player.getSymbol())) {
                    throw new DuplicateSymbolException();
                }
                symbolsSet.add(player.getSymbol());
            }
        }

        private void validateDimensionAndPlayersCount() throws PlayersCountDimensionMismatchException {
            if (players!=null && players.size()!=dimension-1) {
                throw new PlayersCountDimensionMismatchException();
            }
        }

        private void validateBotCount() throws MoreThanOneBotCountException {
            int botCount = 0;

            for (Player player : players) {
                if(PlayerType.BOT.equals(player.getPlayerType())) {
                    botCount++;
                }
            }

            if(botCount >1) {
                throw new MoreThanOneBotCountException();
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
}
