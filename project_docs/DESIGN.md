# Class Diagrams

These are all the classes required to make the Connect-N program work.

## Token
This identifies a single token, which must be owned by a player.
### Attributes
* `-owner`: `Player`
### Methods
* `+getOwner()`: `Player`
* `+equals(other: Object)`: `boolean`

## Player
A player can either be human-controller or computer-controlled, which is used
to determine whether the controller will make a move or will request a move from
the user.
### Attributes
* `-name`: `String`
* `-id`: `int`
* `-isHuman`: `boolean`
### Methods
* `+getName()`: `String`
* `+equals(other: Object)`: `boolean`

## Column
A column is composed of a token array. All tokens must be added to the
lowest non-empty position in the array. The number of tokens in the column is
kept track of to simplify this process. An empty position is signified by a
`null` token.
### Attributes
* `-tokens`: `Token[]`
* `-numberOfTokens`: `int`
* `-height`: `int`
### Methods
* `+getToken(row: int)`: `Token`
* `+addToken(token: Token)`: `void`

## GameBoard
A game board is composed of an array of columns. A game board can also check
whether or not a positition is a "winning position," by calling on four methods
(one for each direction) to determine if there is a `tokensToConnect` in a row
in any direction. This can be done at any time by the controller, but makes most
sense to do after every time a token is added.
### Attributes
* `-board`: `Column[]`
* `-width`: `int`
* `-tokensToConnect`: `int`
* `-MIN_TOKENS_TO_CONNECT`: `int`
* `-MAX_TOKENS_TO_CONNECT`: `int`
### Methods
* `+addToken(token: Token, col: int)`: `void`
* `+isWinningPosition(row: int, col: int)`: `boolean`
* `-checkVertical(row: int, col: int)`: `boolean`
* `-checkHorizontal(row: int, col: int)`: `boolean`
* `-checkBackwardDiagonal(row: int, col: int)`: `boolean`
* `-checkForwardDiagonal(row: int, col: int)`: `boolean`

## GameInterface
The `GameInterface` is an interface that must have a specific implementation.
This interface is responsible for displaying the game board to the user.
This interface also collects all user input required by the game controller.
## Attributes
* `-controller`: `Controller`
* `-game`: `GameBoard`
### Methods
* `-requestUserInput()`: `int`
* `-displayBoard()`: `void`

## GameController
The `GameController` is an interface that must have a specific implementation.
This interface is responsible for modifying the game board by adding tokens.
This interface also keeps track of all current players and uses that information
to "take a turn" for a player by either deciding their move, for computer-controlled
players, or requesting user input from the game interface for human-controlled
players.
## Attributes
* `-interface`: `GameInterface`
* `-game`: `GameBoard`
* `-players`: `Player[]`
### Methods
* `-takeTurn()`: `void`
* `-decideColumn(player Player)`: `int`
