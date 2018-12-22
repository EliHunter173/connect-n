/**
 * An interface for a generic user interface for Connect-N.
 * @author Eli W. Hunter
 */
public abstract class GameInterface {

    public GameController worker;
    public GameBoard game;

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
     * Sets the associated GameController for this GameInterface.
     * @param controller The GameController to associate
     *     this interface with.
     */
    public void setController(GameController controller) {
        this.worker = controller;
    }

    /**
     * Displays the GameBoard associated with this GameInterface.
     */
    public abstract void displayBoard();

    /**
     * Requests user input and returns that in the form of a String.
     * @return The user's action as a String.
     */
    public abstract String requestUserAction();

    /**
     * Requests a list of players from the user and returns that
     * in the form of a Player array.
     * @return The list of players from the user.
     */
    public abstract Player[] requestPlayers();

    public abstract void displayHelp();

    /**
     * Displays a winning screen to the user.
     */
    public abstract void displayWin(Player winningPlayer);

    /**
     * Displays a game over screen to the user.
     */
    public abstract void displayGameOver();

}
