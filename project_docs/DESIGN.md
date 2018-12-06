# Design
This document details the implementation of all current classes and thier methods.
The constructors for each class are left out as they are not the primary scope
of this document and will be largely standard in terms of initializing all fields.

## Player
A player can either be human-controller or computer-controlled, which is used
to determine whether the controller will make a move or will request a move from
the user.
### Attributes
* `+NONE`: `Player`
* `-name`: `String`
* `-id`: `int`
* `-playerType`: `byte` (defined with class constants, not actual bytes)
### Constructors
* `Player()`: Generates invalid `NONE` player.
* `Player(name: String, playerType: byte)`: Generates player object with the given name and player type.
* `Player(playerType: byte)`: Generates player object with the given player type and a default name (Player #).
### Methods
* `+resetCounter()`: `void`
* `+getId()`: `Int`
* `+getName()`: `String`
* `+getPlayerType`: `byte`
* `+equals(other: Object)`: `boolean`

## Token
This identifies a single token, which must be owned by a player.
### Attributes
* `+EMPTY`: `Token`: Creates a token with `owner` `Player.NONE`.
* `-owner`: `Player`
### Constructors
* `Token(owner: Player)`: Generates a token with `owner` owner.
### Methods
* `+getOwner()`: `Player`
* `+equals(other: Object)`: `boolean`

## Column
A column is composed of a token array. All tokens must be added to the
lowest non-empty position in the array. The number of tokens in the column is
kept track of to simplify this process. An empty position is signified by a
`null` token.
### Attributes
* `-maxTokens`: `int`
* `-tokens`: `Token[]`
* `-numberOfTokens`: `int`
### Constructors
* `Column(height: int)`: Generates a column with height `height`.
### Methods
* `+getMaxTokens()`: `int`
* `+getToken(row: int)`: `Token`
* `+addToken(token: Token)`: `void`
* `+empty()`: `void`

## GameBoard
A game board is composed of an array of columns. A game board can also check
whether or not a position is a "winning position," by calling on four methods
(one for each direction) to determine if there is a `tokensToConnect` in a row
in any direction. This can be done at any time by the controller, but makes most
sense to do after every time a token is added.
### Attributes
* `+MIN_TOKENS_TO_CONNECT`: `int`
* `+MAX_TOKENS_TO_CONNECT`: `int`
* `+HIGH_TOKENS_ERROR_MESSAGE`: `String`
* `+LOW_TOKENS_ERROR_MESSAGE`: `String`
* `+INVALID_HEIGHT_ERROR_MESSAGE`: `String`
* `+INVALID_WIDTH_ERROR_MESSAGE`: `String`
* `-width`: `int`
* `-height`: `int`
* `-tokensToConnect`: `int`
* `-columns`: `Column[]`
### Constructors
* `GameBoard(width: int, height: int, tokensToConnect: int)`: Generates game board with width `width`, height `height`, and number of tokens to connect `tokensToConnect`.
### Methods
* `+getWidth()`: `int`
* `+getHeight()`: `int`
* `+getTokensToConnect()`: `int`
* `+getToken(row: int, col: int)`: `Token`
* `+empty()`: `void`
* `+addToken(token: Token, col: int)`: `void`
* `+isWinningPosition(row: int, col: int)`: `boolean`
  * `+checkVertical(row: int, col: int)`: `boolean`
  * `+checkHorizontal(row: int, col: int)`: `boolean`
  * `+checkForwardDiagonal(row: int, col: int)`: `boolean`
  * `+checkBackwardDiagonal(row: int, col: int)`: `boolean`
* `+checkSequence(anchorRow: int, anchorCol: int, rowStepSize: int, colStepSize: int, numberOfTokens: int)`: `boolean`

## GameController
This game controller is responsible for modifying the game board by adding tokens.
It also keeps track of all current players and uses that information to "take a turn"
for a player by either deciding their move, for computer-controlled players, or
requesting user input from the game interface for human-controlled players.
## Attributes
* `+MIN_PLAYERS`: `int`
* `+LOW_PLAYERS_ERROR_MESSAGE`: `String`
* `-client`: `GameInterface`
* `-game`: `GameBoard`
* `-players`: `Player[]`
* `-playerPointer`: `int`
* `-isRunning`: `boolean`
### Constructors
* `GameController(game: GameBoard, players: Player[])`: Generates controller with game `game` and players `player`.
### Methods
* `+getCurrentPlayer()`: `Player`
* `+getNumberOfPlayers()`: `int`
* `+setInterface(viewer: GameInterface)`: `void`
* `+isRunning()`: `boolean`
* `+takeTurn()`: `void`
* `-nextPlayer()`: `void`

## GameInterface
The game interface is an interface that must have a specific implementation.
This interface is responsible for displaying the game board to the user.
This interface also collects all user input required by the game controller.
### Methods
* `+setController(controller: GameController)`: `void`
* `+displayBoard()`: `void`
* `+requestUserAction()`: `String`
* `+requstPlayers()`: `Player[]`

## CLI
This is a specific implementation of `GameInterface` which displays all things
and prompts the user over the terminal.
### Constructors
* `CLI()`: Generates a game board by prompting a user for the number of tokens to connect, width, height, number of players, and then all of those player's names.
### Fields
* `-NORMAL_COLOR`: `String`
* `-NONE_PLAYER_SYMBOL`: `char`
* `-NAME_SYMBOL_INDEX`: `int`
* `-PLAYER_COLORS`: `String[]`
* `-HORIZONTAL_PADDING_STRING`: `String`
* `-VERTICAL_PADDING_STRING`: `String`
* `-EXTERNAL_PADDING`: `int`
* `-INTERNAL_PADDING`: `int`
* `-PLAYER_PROMPT`: `(format) String`
* `-HELP_MESSAGE`: `String`