import java.util.Scanner;
import java.io.PrintStream;

/**
 * An command line interface for Connect-N.
 * @author Eli W. Hunter
 */
public class CLI implements GameInterface {

    /**
     * The usage error if a misunderstood argument is given.
     */
    private static final String USAGE_ERROR =
        "USAGE: java CLI [options]\n" +
        "\n" +
        "OPTIONS:\n" +
        "  --no-color      Use if your terminal does not support ANSI escape sequences\n" +
        "                  because otherwise tokens will display wrongly.";

    /**
     * A list of all the command line arguments which are understood
     * by CLI.java.
     */
    private static final String[] REGISTERED_ARGS = {
        "--no-color",
    };

    /**
     * The ANSI escape sequence representing the normal color
     * to be displayed.
     */
    private static final String NORMAL_COLOR = Color.WHITE;
    /**
     * The ANSI escape sequence representing the bold version
     * of the normal color to be displayed.
     */
    private static final String BOLD_NORMAL_COLOR = Color.BOLD_WHITE;
    /**
     * The character to be displayed for an empty token.
     */
    private static final char EMPTY_TOKEN_SYMBOL = 'O';

    /**
     * The index to take a character from a player's. This character
     * represents their symbol or their token.
     */
    private static final int NAME_SYMBOL_INDEX = 0;
    /**
     * An array of ANSI escape sequences representing all the possible
     * colors a player can have and the order in which they will be cycled
     * through.
     */
    private static final String[] PLAYER_COLORS = {
        Color.BOLD_RED,
        Color.BOLD_GREEN,
        Color.BOLD_BLUE,
        Color.BOLD_MAGENTA,
        Color.BOLD_YELLOW,
        Color.BOLD_CYAN,
    };

    /**
     * The String which will be put before every displayed
     * column of a GameBoard.
     * */
    private static final String EXTERNAL_PADDING = Utils.repeatString(" ", 3);
    /**
     * The String which will be put between every displayed
     * token of a GameBoard.
     */
    private static final String INTERNAL_PADDING = Utils.repeatString(" ", 1);
    /**
     * The String which will be put before and after the GameBoard
     * every time it is displayed.
     */
    private static final String VERTICAL_PADDING = Utils.repeatString("\n", 1);

    /**
     * The String which represents a horizontal rule. This is displayed
     * whenever appropriate to show a distinct action has occured, normally
     * after a player inputs as action.
     */
    private static final String HORIZONTAL_RULE = Utils.repeatString("=", 32);

    /**
     * The format String which will be displayed to prompt a player. This is
     * formatted with the following args in said order: player name.  */
    private static final String PLAYER_PROMPT = "%s's Turn!";

    /**
     * The format String which will be displayed when a player wins. This is
     * formatted with the following args in said order: winning player name.
     */
    private static final String WIN_TEXT = "Congratulations, %s! You win!";

    /**
     * The String which will be displayed when a game over occurs.
     */
    private static final String GAME_OVER_TEXT = "Congrat--. Oh, nobody won.";

    /**
     * The number representing the first player. All subsequent players will
     * simply have a number incremented once per player from this.
     */
    private static final int FIRST_PLAYER_NUMBER = 1;

    /**
     * Returns a color based off of the modulus of the inputted number
     * and the length of the player colors array.
     * @param number An arbitrary number.
     * @return The ANSI control sequence representing the given color
     *     from the player colors array.
     */
    public static String getColor(int number) {
        return PLAYER_COLORS[number % PLAYER_COLORS.length];
    }

    /** The GameController associated with this CLI. */
    private GameController worker;
    /** The GameBoard this CLI is displaying. */
    private GameBoard game;
    /** The Scanner object which is used to receive user input. */
    private Scanner input;
    /** The PrintStream object which is used to output. */
    private PrintStream output;
    /** Determines whether the CLI should use color or not. */
    private boolean inColor;

    /**
     * Generates a CLI object with System.out as its output, System.in
     * as its input, and with inColor as the given boolean.
     * @param inColor Whether or not this CLI object should display in
     *     color or not.
     */
    public CLI(boolean inColor) {
        this.input = new Scanner(System.in);
        this.input.useDelimiter("\n");

        this.output = new PrintStream(System.out);
        this.setColor(NORMAL_COLOR);

        this.inColor = inColor;
    }

    // The following methods are segmented to allow immediate error checking, even if
    // it is somewhat redundant.
    /**
     * Requests the number of tokens to be connected for the game board
     * from the user.
     * @return The number of tokens to connect that the user specified.
     */
    public int requestTokensToConnect() {
        int tokensToConnect;
        do {
            tokensToConnect = Utils.getIntReprompting("Number of tokens to connect: ",
                    "The number of tokens must be an integer.", input, output);

            if (tokensToConnect < GameController.MIN_TOKENS_TO_CONNECT)
                output.println(GameController.LOW_TOKENS_ERROR_MESSAGE);
        } while (tokensToConnect < GameController.MIN_TOKENS_TO_CONNECT);
        return tokensToConnect;
    }

    /**
     * Requests the width of game board from the user.
     * @param minWidth The minimum width the user can specify.
     */
    public int requestWidth(int minWidth) {
        int width;
        do {
            width = Utils.getIntReprompting("Width of board: ",
                    "The width must be an integer.", input, output);
            if (width < minWidth)
                output.println(GameBoard.INVALID_WIDTH_ERROR_MESSAGE);
        } while (width < minWidth);
        return width;
    }

    /**
     * Requests the height of game board from the user.
     * @param minHeight The minimum height the user can specify.
     */
    public int requestHeight(int minHeight) {
        int height;
        do {
            height = Utils.getIntReprompting("Height of board: ",
                    "The height must be an integer.", input, output);
            if (height < minHeight)
                output.println(GameBoard.INVALID_HEIGHT_ERROR_MESSAGE);
        } while (height < minHeight);
        return height;
    }

    /**
     * Requests an action from the user and returns that as a String.
     * @return The user-inputted action as a String.
     */
    public String requestUserAction() {
        output.print("Enter action: ");
        return input.next();
    }

    /**
     * Requests a single player from the user and returns that.
     * @return The user-inputted Player object.
     */
    public Player requestPlayer(int playerNumber) {
        output.printf("Player %d's Name: ", playerNumber);
        String name = input.next();
        // For now, the only player type is human
        byte playerType = Player.HUMAN;

        try {
            return new Player(name, playerType);
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
            return requestPlayer(playerNumber); // try again
        }
    }

    /**
     * Requests a list of players form the user and returns that as an
     * array of Player objects.
     * @return The user-inputted list of players as a Player array.
     */
    public Player[] requestPlayers() {
        int numberOfPlayers;
        do {
            numberOfPlayers = Utils.getIntReprompting(
                    "Number of players: ", "The number of players must be an integer", input, output);
            if (numberOfPlayers < GameController.MIN_PLAYERS)
                output.println(GameController.LOW_PLAYERS_ERROR_MESSAGE);
        } while (numberOfPlayers < GameController.MIN_PLAYERS);

        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = requestPlayer(i + FIRST_PLAYER_NUMBER);
        }

        return players;
    }

    /**
     * Accessor Method
     * @return The GameBoard object associated with this CLI.
     */
    public GameBoard getGame() {
        return game;
    }

    /**
     * Sets the GameBoard associated with this CLI to the given GameBoard.
     * @param worker The GameBoard object to be associated with this CLI.
     */
    public void setGame(GameBoard game) {
        this.game = game;
    }

    /**
     * Sets the GameController associated with this CLI to the given
     * GameController.
     * @param controller The GameController object to be associated with
     *     this CLI.
     */
    public void setController(GameController controller) {
        this.worker = controller;
    }

    /**
     * Sets the color of the output object. This is done by printing an ANSI
     * escape sequence for the desired color, but only if the CLI object is
     * in color.
     * @param color The ANSI escape sequence for the desired color.
     */
    public void setColor(String color) {
        if (inColor)
            output.print(color);
    }

    /**
     * Displays the given token by displaying its player's associated symbol
     * and color (if the CLI is in color).
     * @param token The Token objec to be displayed.
     */
    public void displayToken(Token token) {
        Player owner = token.getOwner();

        if (owner.equals(Player.NONE)) {
            this.setColor(NORMAL_COLOR);
            output.print(EMPTY_TOKEN_SYMBOL);

        } else {
            String playerColor = getColor(owner.getId());
            this.setColor(playerColor);

            char playerSymbol = owner.getName().charAt(NAME_SYMBOL_INDEX);
            output.print(playerSymbol);
        }
    }

    /**
     * Displays the entire GameBoard that is associated with this CLI.
     * It does this by displaying all individual tokens in their appropriate
     * location.
     */
    public void displayBoard() {
        this.setColor(NORMAL_COLOR);
        output.println(HORIZONTAL_RULE);
        output.print(VERTICAL_PADDING);

        // Display Main Board
        for (int row = game.getHeight() - 1; row >= 0; row--) {
            output.print(EXTERNAL_PADDING);
            for (int col = 0; col < game.getWidth(); col++) {
                displayToken(game.getToken(row, col));
                output.print(INTERNAL_PADDING);
            }
            output.println();
        }

        output.println();

        // Display Column Numbers
        int maxCol = game.getWidth() - 1;
        int maxDigits = Utils.numberOfDigits(maxCol);

        this.setColor(BOLD_NORMAL_COLOR);
        for (int digitIndex = 0; digitIndex < maxDigits; digitIndex++) {
            output.print(EXTERNAL_PADDING);
            for (int col = 0; col < game.getWidth(); col++) {
                String colString = Integer.toString(col);
                try {
                    output.print(colString.charAt(digitIndex)); // print the digit
                } catch (StringIndexOutOfBoundsException e) {
                    output.print(" "); // if can't print the digit, print a space
                }
                output.print(INTERNAL_PADDING);
            }
            output.println();
        }
        output.print(VERTICAL_PADDING);
    }

    /**
     * Runs through a single human player's turn by requesting action.
     */
    public void nextTurn() {
        this.setColor(NORMAL_COLOR);

        Player currentPlayer = worker.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();
        output.println(String.format(PLAYER_PROMPT, currentPlayerName));

        try {
            worker.takeTurn();

        } catch (Exception e) {
            output.println(e.getMessage());
            this.nextTurn();
        }
    }

    /**
     * Displays the help screen.
     */
    public void displayHelp() {
        this.setColor(NORMAL_COLOR);
        output.println(HORIZONTAL_RULE);
        output.println(GameController.HELP_MESSAGE);
    }

    /**
     * Displays the win screen for the given player.
     * @param winningPlayer The player to display the win screen for.
     */
    public void displayWin(Player winningPlayer) {
        this.displayBoard();
        this.setColor(BOLD_NORMAL_COLOR);
        output.println(String.format(WIN_TEXT, winningPlayer.getName()));
    }

    /**
     * Displays the game over screen.
     */
    public void displayGameOver() {
        this.displayBoard();
        this.setColor(BOLD_NORMAL_COLOR);
        output.println(GAME_OVER_TEXT);
    }

    /**
     * Begins a game and run through an entire game of Connect-N using a CLI
     * and the default (and currently only) game controller.<br>
     * Whether or not the CLI is in color is determined by command line args.
     * @param args[] A String array that contains all options to run the CLI
     *     with. All known arguments are in REGISTERED_ARGS, and
     *     unregistred must not be provided for the program to successfully run.
     */
    public static void main(String[] args) {
        // make sure the only inputted args are registered args
        for (String arg : args) {
            if (!Utils.containsString(REGISTERED_ARGS, arg)) {
                System.out.println(USAGE_ERROR);
                System.exit(1);
            }
        }
        // parse args
        boolean inColor = !Utils.containsString(args, "--no-color");

        CLI viewer = new CLI(inColor);
        // These methods are segmented to allow immediate error checking, even if
        // it is somewhat redundant.
        int tokensToConnect = viewer.requestTokensToConnect();
        int width = viewer.requestWidth(tokensToConnect);
        int height = viewer.requestHeight(tokensToConnect);
        GameBoard game = new GameBoard(width, height, tokensToConnect);
        GameController controller = new GameController(game, viewer.requestPlayers());
        viewer.setGame(controller.getGame());

        viewer.setController(controller);
        controller.setInterface(viewer);

        while (controller.isRunning()) {
            viewer.displayBoard();
            viewer.nextTurn();
        }
    }

}
