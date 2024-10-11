package strategies;

import models.Board;
import models.Cell;
import models.CellState;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Cell makeMove(Board board) {
        /*need to implement easy bot playing strategy
        -> going from top left to right bottom, bot will search for empty boxes
        -> each row is list of cells

         */
        System.out.println("Bot is making the move");
        for(List<Cell> eachRow : board.getBoard()) {
            for(Cell cell : eachRow) {
                if (CellState.EMPTY.equals(cell.getCellState())) {
                    return cell;
                }
            }
        }
        //Board is full. Any exception can be thrown.
        return null;
    }
}
