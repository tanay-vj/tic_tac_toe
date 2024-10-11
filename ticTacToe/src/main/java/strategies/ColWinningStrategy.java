package strategies;

import models.Board;
import models.Move;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{
    private final Map<Integer, HashMap<Character,Integer>> counts = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        char symbol = move.getPlayer().getSymbol();
        //first entry in row
        if(!counts.containsKey(col)){
            counts.put(col, new HashMap<>());
        }
        Map<Character, Integer> colMap = counts.get(col);

        //is this symbol already exist in this row or not
        if(!colMap.containsKey(symbol)){
            colMap.put(symbol, 0);
        }
        colMap.put(symbol, colMap.get(symbol) + 1);

        if(colMap.get(symbol).equals(board.getDimension())){
            return true;
        }
        return false;
    }

    @Override
    public void handleUndo(Board board, Move lastMove) {
        int col = lastMove.getCell().getCol();
        char symbol = lastMove.getPlayer().getSymbol();

        HashMap<Character, Integer> colMap = counts.get(col);
        colMap.put(symbol, colMap.get(symbol) - 1);
    }
}
