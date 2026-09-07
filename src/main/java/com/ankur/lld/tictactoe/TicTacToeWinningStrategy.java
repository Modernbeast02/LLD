package com.ankur.lld.tictactoe;

public class TicTacToeWinningStrategy implements WinningStrategy {
    @Override
    public boolean checkWinner(Board board, Player player, Position position){
        Symbol symbol = player.getSymbol();

        int size = board.getSize();

        // check rows
        for(int i = 0; i < size; i++){
            boolean won = true;

            for(int j = 0; j < size; j++){
                if(board.getSymbol(new Position(i, j)) != symbol){
                    won = false;
                    break;
                }
            }

            if(won){
                return true;
            }
        }

        // check col
        for(int i = 0; i < size; i++){
            boolean won = true;

            for(int j = 0; j < size; j++){
                if(board.getSymbol(new Position(j, i)) != symbol){
                    won = false;
                    break;
                }
            }

            if(won){
                return true;
            }
        }

        // check main diagonal

        boolean won = true;

        for(int i = 0; i < size; i++){
            if(board.getSymbol(new Position(i, i)) != symbol){
                won = false;
                break;
            }
        }
        if(won){
            return true;
        }

        // check anti diagonal
        won = true;

        for(int i = 0; i < size; i++){
            if(board.getSymbol(new Position(i, size - i - 1)) != symbol){
                won = false;
                break;
            }
        }
        return won;
    }
}
