package com.ankur.lld.tictactoe;

public interface WinningStrategy {
    boolean checkWinner(Board board, Player player, Position position);
}
