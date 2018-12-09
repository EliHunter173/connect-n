public class GameController {

    public static final int MIN_PLAYERS = 2;
    public static final String LOW_PLAYERS_ERROR_MESSAGE =
        String.format("There must be at least %d players", MIN_PLAYERS);

    // 3 is the minimum value possible to not have a guaranteed game
    public static final int MIN_TOKENS_TO_CONNECT = 3;
    // 32 is just absurdly big
    public static final int MAX_TOKENS_TO_CONNECT = 32;
    public static final String HIGH_TOKENS_ERROR_MESSAGE =
        String.format("The number of tokens exceeds the maximum. (%d)", MAX_TOKENS_TO_CONNECT);
    public static final String LOW_TOKENS_ERROR_MESSAGE =
        String.format("The number of tokens cannot be less than the the minimum. (%d)", MIN_TOKENS_TO_CONNECT);

    private GameInterface client;
    private GameBoard game;
    private Player[] players;
    private int playerPointer;
    private boolean isRunning;

    public GameController(int width, int height, int tokensToConnect, Player[] players) {
        if (tokensToConnect < MIN_TOKENS_TO_CONNECT)
            throw new IllegalArgumentException(LOW_TOKENS_ERROR_MESSAGE);
        if (tokensToConnect > MAX_TOKENS_TO_CONNECT)
            throw new IllegalArgumentException(HIGH_TOKENS_ERROR_MESSAGE);
        if (players.length < MIN_PLAYERS)
            throw new IllegalArgumentException(LOW_PLAYERS_ERROR_MESSAGE);

        this.game = new GameBoard(width, height, tokensToConnect);
        this.players = players;
        this.playerPointer = 0;
        this.isRunning = true;
    }

    public Player getCurrentPlayer() {
        return players[playerPointer];
    }

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

    public void setInterface(GameInterface client) {
        this.client = client;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

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

    private void nextPlayer() {
        playerPointer = (playerPointer + 1) % this.getNumberOfPlayers();
    }

}
