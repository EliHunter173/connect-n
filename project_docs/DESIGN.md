# Class Diagrams

These are all the classes required to make the Connect-N program work.

## `Token`

### Attributes
* `-owner`: `Player`

### Methods
* `+getOwner()`: `Player`
* `+equals(other: Object)`: `boolean`

## `Player`

### Attributes
* `-game`: `GameBoard`
* `-name`: `String`
* `-id`: `long`
* `-isAI`: `boolean`

### Methods
* `+getName()`: `String`
* `+equals(other: Object)`: `boolean`
* `+decideColumn()`: `int`

## `Column`

### Attributes
* `-tokens`: `Token[]`
* `-numberOfTokens`: `int`
* `-height`: `int`

### Methods
* `+getToken(row: int)`: `Token`
* `+addToken(token: Token)`: `void`

## `GameBoard`

### Attributes
* `-players`: `Player[]`
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

## `GameInterface`

### Attributes
* `+takeTurn(player: Player)`: `void`
* `+requestUserInput()`: `int`
* `+displayBoard()`: `void`
