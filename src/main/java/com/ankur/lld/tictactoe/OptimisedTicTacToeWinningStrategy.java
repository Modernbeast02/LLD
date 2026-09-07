package com.ankur.lld.tictactoe;

public class OptimisedTicTacToeWinningStrategy implements WinningStrategy {

    private final int [] rowsCount;
    private final int [] colsCount;
    private final int size;

    private int mainDiagonalCount;
    private int antiDiagonalCount;

    OptimisedTicTacToeWinningStrategy(int size){
        this.size = size;
        rowsCount = new int[size];
        colsCount = new int[size];
    }

    @Override
    public boolean checkWinner(Board board, Player player, Position position) {

        int value = player.getSymbol() == Symbol.X ? 1 : -1;

        int row = position.getRow();
        int col = position.getCol();

        rowsCount[row] += value;
        colsCount[col] += value;

        if(row == col){
            mainDiagonalCount += value;
        }

        if(row + col == size - 1){
            antiDiagonalCount += value;
        }

        return Math.abs(rowsCount[row]) == size || Math.abs(colsCount[col]) == size || Math.abs(mainDiagonalCount) == size || Math.abs(antiDiagonalCount) == size;
    }
}
