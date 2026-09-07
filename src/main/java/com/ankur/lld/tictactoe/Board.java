package com.ankur.lld.tictactoe;

public class Board {
    private final int size;
    private final Symbol[][] grid;

    public Board(int size){
        this.size = size;
        this.grid = new Symbol[size][size];
    }

    // We need to check if the position is valid or not
    // We need to place symbol
    // We need to check if the board is full or not
    // We need to check the position is empty or not
    // We need to get current symbol on the cell

    public boolean isValidPosition(Position position){
        int row = position.getRow();
        int col = position.getCol();

        if(row >= 0 && row < size && col >= 0 && col < size){
            return true;
        }
        return false;
    }

    public boolean isPositionEmpty(Position position){
        int row = position.getRow();
        int col = position.getCol();

        return grid[row][col] == null;
    }
    public void placeSymbol(Position position, Symbol symbol){

        if(!isValidPosition(position)){
            throw new IllegalArgumentException("Invalid Position");
        }

        if(!isPositionEmpty(position)){
            throw new IllegalArgumentException("Position is not empty");
        }

        grid[position.getRow()][position.getCol()] = symbol;
    }

    public boolean isBoardFull(){

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(grid[i][j] == null){
                    return false;
                }
            }
        }
        return true;
    }

    public Symbol getSymbol(Position position){
        if(!isValidPosition(position)){
            throw new IllegalArgumentException("Invalid Position to get Symbol");
        }

        return grid[position.getRow()][position.getCol()];
    }

    public int getSize(){
        return size;
    }
}
