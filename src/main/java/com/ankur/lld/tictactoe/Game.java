package com.ankur.lld.tictactoe;

import java.util.List;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final WinningStrategy winningStrategy;
    private int currentPlayerIndex;
    private GameState gameState;
    private Player winner;

    public Game(Board board, List<Player> players, WinningStrategy winningStrategy){
        this.board = board;
        this.players = players;
        this.winningStrategy = winningStrategy;

        this.currentPlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
    }

    public void makeMove(Position position){
        if(gameState != GameState.IN_PROGRESS){
            throw new IllegalStateException("Game is already over");
        }

        Player currentPlayer = players.get(currentPlayerIndex);

        board.placeSymbol(position, currentPlayer.getSymbol());

        if(winningStrategy.checkWinner(board, currentPlayer, position)){
            gameState = GameState.WON;
            winner = currentPlayer;
            return;
        }

        if(board.isBoardFull()){
            gameState = GameState.DRAW;
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public GameState getGameState(){
        return gameState;
    }

    public Player getWinner(){
        return winner;
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public Board getBoard() {
        return board;
    }
}
