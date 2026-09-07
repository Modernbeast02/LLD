package com.ankur.lld.tictactoe;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }
}
