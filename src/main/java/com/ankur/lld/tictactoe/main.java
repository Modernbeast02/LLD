package com.ankur.lld.tictactoe;

import java.util.List;

public class main {
    public static void main(String[] args) {
        Player player1 = new Player("Player1", Symbol.X);
        Player player2 = new Player("Player2", Symbol.O);

        int boardSize = 3;
        Board board = new Board(boardSize);

        WinningStrategy winningStrategy = new TicTacToeWinningStrategy();
        WinningStrategy optimisedWinningStrategy = new OptimisedTicTacToeWinningStrategy(boardSize);

        Game game = new Game(board, List.of(player1, player2), winningStrategy);

        game.makeMove(new Position(0, 0));
        game.makeMove(new Position(1, 1));
        game.makeMove(new Position(0, 1));
        game.makeMove(new Position(1, 0));
        game.makeMove(new Position(0, 2));

        System.out.println(game.getGameState());

        if(game.getWinner() != null){
            System.out.println(game.getWinner().getName());
        }

        // Printing the board
        Board finalBoard = game.getBoard();
        int size = finalBoard.getSize();

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(finalBoard.getSymbol(new Position(i, j)) + " ");
            }
            System.out.println();
        }
    }
}
