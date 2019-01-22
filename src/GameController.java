/**
 * Defines a GameController, what actually modifies the GameBoard's information.
 * @author Eli W. Hunter
 */
public class GameController {

    /**
     * The help message that will be displayed whenever the player
     * asks or when input isn't understood.
     */
    public static final String HELP_MESSAGE =
        "Actions: #: Adds a token to the specified column.\n" +
        "         H: Displays this help message.\n" +
        "         D: Displays the current state of the board.\n" +
        "         Q: Exits the program.";

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
    private GameBoard board;
    /** The AI object connected to this GameController. */
    private AI ai;
    /** The list of the players that this GameController is in charge of controlling and
     * managing. */
    private Player[] players;
    /** The index of / pointer to the current player in the players array. */
    private int playerPointer;
    /** Determines whether or not the GameController is running. */
    private boolean isRunning;

    /**
     * Creates a GameController object with an associated GameBoard and
     * of players.
     * @param board The GameBoard object that this GameController controls.
     * @param players[] The array of players that this GameController controls.
     */
    public GameController(GameBoard board, Player[] players) {
        if (players.length < MIN_PLAYERS)
            throw new IllegalArgumentException(LOW_PLAYERS_ERROR_MESSAGE);

        this.board = board;

        this.ai = new AI(board, players);

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
        return board;
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
     * Adds a token to the given column. The token is owned by the current
     * player.
     * @param col The column to which the token should be added.
     * @throws IllegalArgumentException If the given column is invalid for the
     *      associated game board or if the column is full.
     */
    public void addToken(int col) {
        Player currentPlayer = players[playerPointer];
        Token currentPlayerToken = new Token(currentPlayer);

        boolean isWinningMove = board.addToken(currentPlayerToken, col);

        if (isWinningMove) {
            this.isRunning = false;
            client.displayWin(currentPlayer);

        } else if (board.getNumberOfTokens() >= board.getMaxNumberOfTokens()) {
            this.isRunning = false;
            client.displayGameOver();

        } else {
            this.nextPlayer(); // We only increment the pointer if we consider the input successful
        }
    }

    /**
     * Parses the given string action, adding a column if the action is parsable
     * as an integer, and otherwise doing an action based off of the string.
     * @param action The action string to be parsed and had action taken based
     *     off of it.
     * @throws IllegalArgumentException
     *     If the action is a an integer (and therefore a column) and that
     *     integer is not a valid column in the associated GameBoard.<br>
     *     If the given action is not a valid, understood action.
     */
    private void parseAction(String action) {
        if (Utils.isInt(action)) {
            int col = Integer.parseInt(action);
            this.addToken(col);

        } else {
            action = action.toUpperCase();
            switch(action) {
                case "Q":
                    System.exit(0);
                    break;
                case "D":
                    client.displayBoard();
                    break;
                case "H":
                    client.displayHelp();
                    break;
                default:
                    throw new IllegalArgumentException("Misunderstood input");
            }
        }
    }

    /**
     * Iterates through a single turn by receiving user input and then acting
     * on that.
     */
    public void takeTurn() {
        String action;

        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer.getPlayerType() == Player.HUMAN) {
            action = client.requestUserAction();
        } else {
            action = ai.decideAction(currentPlayer);
        }

        this.parseAction(action);
    }

}
