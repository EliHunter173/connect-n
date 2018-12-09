/**
 * Defines a GameController, what actually modifies the GameBoard's information.
 * @author Eli W. Hunter
 */
public class GameController {

    /** Defines the minimum amount of players allowed. */
    public static final int MIN_PLAYERS = 2;
    /**
     * The error message that is displayed when the number of
     * players specified is too low.
     */
    public static final String LOW_PLAYERS_ERROR_MESSAGE =
        String.format("There must be at least %d players", MIN_PLAYERS);

    /** The minimum amount of tokens that the player can set to connect. */
    public static final int MIN_TOKENS_TO_CONNECT = 3;
    /**
     * The error message that is displayed when the specified number of tokens
     * to connect is too low.
     */
    public static final String LOW_TOKENS_ERROR_MESSAGE =
        String.format("The number of tokens cannot be less than the the minimum. (%d)", MIN_TOKENS_TO_CONNECT);

    /** The GameInterface object connected to this GameController. */
    private GameInterface client;
    /** The GameBoard object connected to this GameController. */
    private GameBoard game;
    /**
     * The list of the players that this GameController is in charge of
     * controlling and managing.
     */
    private Player[] players;
    /** The index of / pointer to the current player in the players array. */
    private int playerPointer;
    /** Determines whether or not the GameController is running. */
    private boolean isRunning;

    /**
     * Creates a GameController object with an associated GameBoard and
     * of players.
     * @param game The GameBoard object that this GameController controls.
     * @param players[] The array of players that this GameController controls.
     */
    public GameController(GameBoard game, Player[] players) {
        if (players.length < MIN_PLAYERS)
            throw new IllegalArgumentException(LOW_PLAYERS_ERROR_MESSAGE);

        this.game = game;
        this.players = players;
        this.playerPointer = 0;
        this.isRunning = true;
    }

    /**
     * Accessor Method
     * @return The current player in the players array, as determined by the
     *     player pointer.
     */
    public Player getCurrentPlayer() {
        return players[playerPointer];
    }

    /**
     * Accessor Method
     * @return The number of players in this game controller's player array,
     *     as determined by the length of the players.
     */
    public int getNumberOfPlayers() {
        return players.length;
    }

    /**
     * Accessor Method
     * @return The GameBoard object associated with this GameController.
     */
    public GameBoard getGame() {
        return game;
    }

    /**
     * Accessor Method
     * @return Whether the GameController is running or not.
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Sets the associated GameInterface to the given GameInterface.
     * @param client The GameInterface to associate with this GameController.
     */
    public void setInterface(GameInterface client) {
        this.client = client;
    }

    /**
     * Iterates to the next player, incrementing the player pointer by one
     * or restarting at 0 if the current player is the last player.
     */
    private void nextPlayer() {
        playerPointer = (playerPointer + 1) % this.getNumberOfPlayers();
    }

    /**
     * Iterates through a single turn by receiving user input and then acting
     * on that.
     */
    public void takeTurn() {

        Player currentPlayer = players[playerPointer];
        Token currentPlayerToken = new Token(currentPlayer);

        String action = "";
        // Currently, no consideration of player type is given.
        action = client.requestUserAction();

        if (Utils.isInt(action)) {
            int col = Integer.parseInt(action);
            boolean isWinningMove = game.addToken(currentPlayerToken, col);

            if (isWinningMove) {
                this.isRunning = false;
                client.displayWin(currentPlayer);

            } else if (game.getNumberOfTokens() >= game.getMaxNumberOfTokens()) {
                this.isRunning = false;
                client.displayGameOver();

            } else {
                this.nextPlayer(); // We only increment the pointer if we consider the input successful
            }

        } else {
            action = action.toUpperCase();
            switch(action) {
                case "Q":
                    System.exit(0);
                    break;
                case "H":
                    throw new IllegalArgumentException("");
                default:
                    // I wish I knew how to create my own errors.
                    throw new IllegalArgumentException("Misunderstood input");

            }
        }
    }

}
