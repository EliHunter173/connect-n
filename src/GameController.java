public class GameController {

    public static final int MIN_PLAYERS = 2;
    public static final String LOW_PLAYERS_ERROR_MESSAGE =
        String.format("There must be at least %d players", MIN_PLAYERS);

    private GameInterface client;
    private GameBoard game;
    private Player[] players;
    private int playerPointer;
    private boolean isRunning;

    public GameController(GameBoard game, Player[] players) {
        if (players.length < MIN_PLAYERS)
            throw new IllegalArgumentException(LOW_PLAYERS_ERROR_MESSAGE);

        this.game = game;
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
            int row  = game.addToken(currentPlayerToken, col);
            this.isRunning = !game.isWinningPosition(row, col);
            this.nextPlayer(); // We only increment the pointer if we consider the input successful

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
