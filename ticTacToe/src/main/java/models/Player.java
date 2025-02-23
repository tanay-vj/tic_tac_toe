package models;

import java.util.Scanner;

public class Player {
    private char symbol;
    private String name;
    private int id;
    private PlayerType playerType;
    private Scanner scanner = new Scanner(System.in);

    public Player(char symbol, String name, int id, PlayerType playerType) {
        this.symbol = symbol;
        this.name = name;
        this.id = id;
        this.playerType = playerType;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Cell makeMove(Board board) {
        System.out.println( this.name + " , its your turn to make move. Please enter row & column");

        int row = scanner.nextInt();
        int col = scanner.nextInt();

        while(!validateRowAndCol(row,col,board)) {
            System.out.println( this.name + " , Invalid move. Please enter row & column");
            row = scanner.nextInt();
            col = scanner.nextInt();
        }

        Cell cell = board.getBoard().get(row).get(col);
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(this);
        return cell;
    }

    private boolean validateRowAndCol(int row, int col, Board board) {
        if(row>=board.getBoard().size() || row<0 || col>=board.getBoard().size() || col<0) {
            return false;
        }
        if(CellState.FILLED.equals(board.getBoard().get(row).get(col).getCellState())) {
            return false;
        }
        return true;
    }
}
