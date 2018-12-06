import java.util.Scanner;
import java.io.PrintStream;

public class CLI implements GameInterface {

    private static String NORMAL_COLOR = "\u001B[0m"; // ANSI reset code
    private static char NONE_PLAYER_SYMBOL = 'O'; // NOTE: This should always be a single character

    //private static String[] PLAYER_COLORS = { }
    private static int NAME_SYMBOL_INDEX = 0;
    private static String[] PLAYER_COLORS = {
        "\u001B[31m", // red
        "\u001B[32m", // green
        "\u001B[33m", // yellow
        "\u001B[34m", // blue
        "\u001B[35m", // purple
        "\u001B[36m", // cyan
    };


    private static String HORIZONTAL_PADDING_STRING = " ";
    private static String VERTICAL_PADDING_STRING = " ";
    private static int EXTERNAL_PADDING = 3;
    private static int INTERNAL_PADDING = 1;

    private static String PLAYER_PROMPT = "%s's Turn!\n";

    public static String HELP_MESSAGE =
        "Actions: H: Displays this help message.\n" +
        "         #: Adds a token of your type to the column.\n" +
        "         Q: Exits the program.\n";

    public static String getColor(int number) {
        return PLAYER_COLORS[number % PLAYER_COLORS.length];
    }

    public static String displayToken(Token token) { // TODO: Implement no-color
        Player owner = token.getOwner();
        String displayString;

        if (owner.equals(Player.NONE)) {
            displayString = NORMAL_COLOR + NONE_PLAYER_SYMBOL;

        } else {
            String playerColor = getColor(owner.getId());
            String ownerName = owner.getName();
            char playerSymbol = ownerName.charAt(NAME_SYMBOL_INDEX);
            displayString = playerColor + playerSymbol;
        }

        return displayString + Utils.repeatString(HORIZONTAL_PADDING_STRING, INTERNAL_PADDING);
    }

    private GameController controller;
    private GameBoard game;
    private Scanner input;
    private PrintStream output;

    public CLI() {
        this.input = new Scanner(System.in);
        this.input.useDelimiter("\n");

        this.output = new PrintStream(System.out);

        int tokensToConnect;
        do {
            tokensToConnect = Utils.getInt("Number of tokens to connect: ", input, output);

            if (tokensToConnect < GameBoard.MIN_TOKENS_TO_CONNECT)
                output.println(GameBoard.LOW_TOKENS_ERROR_MESSAGE);
            if (tokensToConnect > GameBoard.MAX_TOKENS_TO_CONNECT)
                output.println(GameBoard.HIGH_TOKENS_ERROR_MESSAGE);

        } while (tokensToConnect < GameBoard.MIN_TOKENS_TO_CONNECT ||
                 tokensToConnect > GameBoard.MAX_TOKENS_TO_CONNECT);

        int width;
        do {
            width = Utils.getInt("Width of board: ", input, output);
            if (width < tokensToConnect)
                output.println(GameBoard.INVALID_WIDTH_ERROR_MESSAGE);
        } while (width < tokensToConnect);

        int height;
        do {
            height = Utils.getInt("Height of board: ", input, output);
            if (height < tokensToConnect)
                output.println(GameBoard.INVALID_HEIGHT_ERROR_MESSAGE);
        } while (height < tokensToConnect);

        this.game = new GameBoard(width, height, tokensToConnect);
    }

    public GameBoard getGame() {
        return game;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void displayBoard() {
        output.print(NORMAL_COLOR +
                Utils.repeatString(VERTICAL_PADDING_STRING, EXTERNAL_PADDING)); // resets the color

        for (int row = game.getHeight() - 1; row >= 0; row--) {

            String line = Utils.repeatString(HORIZONTAL_PADDING_STRING, EXTERNAL_PADDING);

            for (int col = 0; col < game.getWidth(); col++) {
                Token currentToken = game.getToken(row, col);
                line += displayToken(currentToken);
            }
            output.println(line);
        }

        output.print(NORMAL_COLOR +
                Utils.repeatString(VERTICAL_PADDING_STRING, EXTERNAL_PADDING)); // resets the color
    }

    public String requestUserAction() {
        output.print("Enter action: ");
        return input.next();
    }

    public Player[] requestPlayers() {
        int numberOfPlayers;
        do {
            numberOfPlayers = Utils.getInt("Number of players: ", input, output);
            if (numberOfPlayers < GameController.MIN_PLAYERS)
                output.println(GameController.LOW_PLAYERS_ERROR_MESSAGE);
        } while (numberOfPlayers < GameController.MIN_PLAYERS);

        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = requestPlayer();
        }

        return players;
    }

    public Player requestPlayer() {
        output.print("Player Name: ");
        String name = input.next();
        // For now, the only player type is human
        byte playerType = Player.HUMAN;

        return new Player(name, playerType);
    }

    public void nextTurn() {
        Player currentPlayer = controller.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();
        output.printf(PLAYER_PROMPT, currentPlayerName);

        try {
            controller.takeTurn();

        } catch (Exception e) {
            output.println();
            output.println(e.getMessage());
            output.print(HELP_MESSAGE);
            this.nextTurn();
        }
    }

    public void print(String message) {
        output.print(message);
    }

    public static void main(String[] args) {
        CLI viewer = new CLI();
        GameController controller = new GameController(viewer.getGame(), viewer.requestPlayers());

        viewer.setController(controller);
        controller.setInterface(viewer);

        viewer.print(HELP_MESSAGE);
        while (controller.isRunning()) {
            viewer.displayBoard();
            viewer.nextTurn();
        }
    }
}
