# Comprehensive Exercise Requirements

* **Goal:** Connect Four
* **Stakeholders:** Player, System

## Functional Requirements

* **Formal:** The (stakeholder type) shall [not] be able to (function type).
* **Shorthand:** [not]  (function type).
  > Header shows stakeholder type.

### System

* NOT Remove tokens.
* Check if n tokens of the same type are in a row upon the placement of a token.
* Declare a winner if n tokens of the same type are in a line, horizontally, vertically, or diagonally.
* NOT Add a token outside of bounds.
* Add a token of any type to the lowest non-filled row.
* Set a arbitrary board size at the beginning.
* NOT Set board size below n in any direction.
* NOT Change board size during the game.
* Upon completion of game, display winner and ask if player would like to play again.

### Player

* NOT Change board size during the game.
* Add a token of their type by specifying the column to add it to.
* View the state of the board.
  * Includes all tokens, their type, all empty spaces, and the size of the board.
* NOT Remove a token that isn't of their type.

## User Stories

* Upon executing a program, the number of players is requested, the board size is requested, and the number of tokens to connect is requested. The game starts showing an empty board of the specified size displaying the current player.

* A player is prompted to add a token and chooses a column
  * If that column exists and is not full, the system successfully adds a token of their type to that column at the lowest non-filled row in that column.
  * If that column exists but is full, the system displays a message stating that a token cannot be added to a full column and that same player is reprompted to add another token.
  * If that column does not exist, the system displays a message stating that a token cannot be added to a full column and that same player is reprompted to add another token.

* A player just successfully placed a token on the board, the updated board is displayed and the next player is prompted for a token.
  * If the player is not the last numerical player, the player that is 1 above the current player is the next player.
  * If the player is the last numerical player, the first player is the next player.

* Player 1 wins by getting the specified number of tokens in a row. The system displays a win screen, which states which player won. On that screen, it displays 3 options, quitting, playing again with different settings, playing again with the same settings.
* The board is completely filled. Howver, no player has managed to connect the required number of pieces. A game over screen is displayed. On that screen, it displays 3 options, quitting, playing again with different settings, playing again with the same settings.

* If the players request to replay the game with different settings, the program requests all that it did to start the program. The board is then begun as an empty board with the specified settings.
* If the players request to replay the game with the same settings, the program retains all the settings it had with the last board. The board is then begun as an empty board with the specified settings.
