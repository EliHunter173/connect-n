# Design
This document details the implementation of all current classes and thier methods.
The constructors for each class are left out as they are not the primary scope
of this document and will be largely standard in terms of initializing all fields.

## Player
A player can either be human-controller or computer-controlled, which is used
to determine whether the controller will make a move or will request a move from
the user.
### Attributes
* `-name`: `String`
* `-id`: `int`
* `-playerType`: `byte` (defined with class constants, not actual bytes)
### Methods
* `+resetCounter()`: `void`
* `+getName()`: `String`
* `+getPlayerType`: `byte`
* `+equals(other: Object)`: `boolean`

## Token
This identifies a single token, which must be owned by a player.
### Attributes
* `-owner`: `Player`
### Methods
* `+getOwner()`: `Player`
* `+equals(other: Object)`: `boolean`

## Column
A column is composed of a token array. All tokens must be added to the
lowest non-empty position in the array. The number of tokens in the column is
kept track of to simplify this process. An empty position is signified by a
`null` token.
### Attributes
* `-tokens`: `Token[]`
* `-numberOfTokens`: `int`
* `-maxTokens`: `int`
### Methods
* `+getMaxTokens()`: `int`
* `+getToken(row: int)`: `Token`
* `+addToken(token: Token)`: `void`

## GameBoard
A game board is composed of an array of columns. A game board can also check
whether or not a positition is a "winning position," by calling on four methods
(one for each direction) to determine if there is a `tokensToConnect` in a row
in any direction. This can be done at any time by the controller, but makes most
sense to do after every time a token is added.
### Attributes
* `-columns`: `Column[]`
* `-width`: `int`
* `-height`: `int`
* `-tokensToConnect`: `int`
* `-MIN_TOKENS_TO_CONNECT`: `int`
* `-MAX_TOKENS_TO_CONNECT`: `int`
### Methods
* `+getWidth()`: `int`
* `+getHeight()`: `int`
* `+getTokensToConnect()`: `int`
* `+getToken(row: int, col: int)`: `Token`
* `+addToken(token: Token, col: int)`: `void`
* `+isWinningPosition(row: int, col: int)`: `boolean`
* `-checkVertical(row: int, col: int)`: `boolean`
* `-checkHorizontal(row: int, col: int)`: `boolean`
* `-checkForwardDiagonal(row: int, col: int)`: `boolean`
* `-checkBackwardDiagonal(row: int, col: int)`: `boolean`

## GameInterface
The game interface is an interface that must have a specific implementation.
This interface is responsible for displaying the game board to the user.
This interface also collects all user input required by the game controller.
## Attributes
* `-controller`: `GameController`
* `-game`: `GameBoard`
* `-MAX_WIDTH`: `int`
* `-MAX_HEIGHT`: `int`
### Methods
* `+setController()`: `void`
* `+requestUserInput()`: `String`
* `-displayBoard()`: `void`
* `-requstPlayers()`: `Player[]`

## GameController
This game controller is responsible for modifying the game board by adding tokens.
It also keeps track of all current players and uses that information to "take a turn"
for a player by either deciding their move, for computer-controlled players, or
requesting user input from the game interface for human-controlled players.
## Attributes
* `-interface`: `GameInterface`
* `-game`: `GameBoard`
* `-players`: `Player[]`
### Methods
* `+getCurrentPlayer()`: `Player`
* `+setInterface(interface: GameInterface)`: `void`
* `+takeTurn()`: `void`

## AI
This interface is simply full of static methods that are used by the controller (or
really anything) to control how the AI plays.
This interface is separated from the core GameController class so the AI is more
plug and play.
### Methods
* `+decideColumn(player: Player, board: GameBoard)`: `int`
