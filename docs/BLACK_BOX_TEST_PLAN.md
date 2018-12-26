# Black Box Test Plan

This is the black box test plan for the Connect N piece of software.

The format follows as such:
* Main Heading Title: Test ID
* Inputs Section: A description or list of the inputs given to the software.
* Expected Outputs Section: A description or list of the expected outputs of the software.
* Actual Outputs Section: A description or list of the actual outputs of the software,
  after testing.

## `testBasicDisplay`

### Inputs

```shell
java -cp bin CLI

Number of tokens to connect: 3
Width of board: 4
Height of board: 5
Number of players: 2
Player 1’s Name: Alice
Player 2’s Name: Bob
```

### Expected Outputs

A help message is displayed and then a 4-wide, 5-tall empty board (signified by O’s) is displayed
prompting the player 1 for input with their name (Alice).

### Actual Outputs

A help message is displayed and then a 4-wide, 5-tall empty board (signified by O’s) is displayed
prompting the player 1 for input with their name (Alice).

## `testColoredToken`

### Inputs

Use the setup described in testBasicDisplay.

```shell
Alice’s Turn!
Enter action: 2
```

### Expected Outputs

A bold colored “A” is added to the bottom of column 2 (the third column).
Bob is then prompted for action like Alice was.

### Actual Outputs

A bold colored “A” is added to the bottom of column 2 (the third column).
Bob is then prompted for action like Alice was.

## `testInvalidToken`

### Inputs

Use the setup described in testBasicDisplay.

```shell
Alice’s Turn!
Enter action: -1
```

### Expected Outputs

An error message is displayed and the same player is prompted.

### Actual Outputs

An error message is displayed and the same player is prompted.

## `testQuit`

### Inputs

Use the setup described in testBasicDisplay.

```shell
Alice’s Turn!
Enter action: Q
```

### Expected Outputs

Program exits.

### Actual Outputs

Program exits.

## `testWinningSequence`

### Inputs

Use the setup described in testBasicDisplay.

Add tokens in such a way that Bob gets a negative diagonal sequence of 3 and no other player gets
anything.

### Expected Outputs

A win screen displaying Bob’s name is displayed.

### Actual Outputs

A win screen displaying Bob’s name is displayed.

## `testFullBoard`

### Inputs

Use setup described in testBasicDisplay

Add tokens in such a way that no player gets a sequence of 3.

### Expected Outputs

A game over screen is displayed.

### Expected Outputs

A game over screen is displayed.

## `testManyPlayers`

### Inputs

```shell
java -cp bin CLI

Number of tokens to connect: 3
Width of board: 9
Height of board: 8
Number of players: 7
Player 1’s Name: Alison
Player 2’s Name: Bobert
Player 3’s Name: Carlos
Player 4’s Name: Darius
Player 5’s Name: Elijah
Player 6’s Name: Franky
Player 7’s Name: Gloria
```

Add one token for each player.

### Expected Outputs

A help message is displayed and then a 9-wide, 8-tall empty board (signified by `O`’s).

As each token is added, that player’s token, show with a unique color and the first letter of their
name, is added with to the correct, specified column. The board is redisplayed each time. And the
next player is appropriately prompted with their name.

Each player has a unique color, except for Alice and Gharim who both share a color.

### Actual Outputs

A help message is displayed and then a 9-wide, 8-tall empty board (signified by `O`’s).

As each token is added, that player’s token, show with a unique color and the first letter of their
name, is added with to the correct, specified column. The board is redisplayed each time. And the
next player is appropriately prompted with their name.

Each player has a unique color, except for Alice and Gharim who both share a color.

## `testNoColor`

### Inputs

```shell
java -cp bin CLI --no-color

Number of tokens to connect: 3
Width of board: 9
Height of board: 8
Number of players: 7
Player 1’s Name: Alison
Player 2’s Name: Bobert
Player 3’s Name: Carlos
Player 4’s Name: Darius
Player 5’s Name: Elijah
Player 6’s Name: Franky
Player 7’s Name: Gloria
```

Add one token for each player.

### Expected Outputs

The same results for testManyPlayers; however, this time there are no colors displayed.

### Actual Outputs

The same results for testManyPlayers; however, this time there are no colors displayed.

## `testInvalidTokensToConnect`

### Inputs

```shell
java -cp bin CLI

Number of tokens to connect: 1
...
Number of tokens to connect: 3.14
...
Number of tokens to connect: Four
...
```

### Expected Outputs

Each time an invalid number of tokens to connect is specified, a specific, unique error message is
displayed describing why the given numbers of token to connect is invalid.

### Actual Outputs

Each time an invalid number of tokens to connect is specified, a specific, unique error message is
displayed describing why the given numbers of token to connect is invalid.

## `testInvalidHeight`

### Inputs

```shell
java -cp bin CLI

Number of tokens to connect: 5
Width of board: 7
Height of board: 2
...
Height of board: 2.73
…
Height of board: Six
```

### Expected Outputs

Each time an invalid height is specified, a specific, unique error message is displayed describing
why the given height is invalid.

### Actual Outputs

Each time an invalid height is specified, a specific, unique error message is displayed describing
why the given height is invalid.
