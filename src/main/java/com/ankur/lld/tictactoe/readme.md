# Tic-Tac-Toe Game

---

## Functional Requirements

- **Players**: Two players
- **Board**: 3 × 3 grid
- **Symbols**:
    - Player 1 uses **X**
    - Player 2 uses **O**
- **Turn Order**: Players take turns alternately

### Win Conditions

A player wins by placing three of their symbols in:

- A **row**
- A **column**
- A **diagonal** (either main or anti-diagonal)

### Draw Condition

- The game ends in a **draw** if the board becomes full and no player has achieved three in a row

### Rules

- A player **cannot** place a symbol on an already occupied cell
- The game must **expose the current state** (board position, whose turn it is, winner/draw status, etc.)

### Game State

The game exposes the following state information:

- Current board configuration (3×3 grid)
- Whose turn it is (X or O)
- Game status (ongoing, X wins, O wins, or draw)
- Available moves (empty cells)

### How to Play

1. The game starts with an empty 3×3 board
2. Player X makes the first move
3. Players alternate placing their symbols
4. The game checks for a win after each move
5. If no win is possible and the board is full, the game ends in a draw

### Technical Details

- **Input**: Cell coordinates (row, column) or cell number (1-9)
- **Output**: Current board state
- **Validation**: Invalid moves are rejected (occupied cells, out-of-bounds)

---
