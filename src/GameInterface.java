// NOTE: This would actually make more sense as an abstract class,
// but we haven't been taught those yet, and I'm not sure if it
// would be okay for me to use it for this assignment because of that.
/**
 * An interface for a generic user interface for Connect-N.
 * @author Eli W. Hunter
 */
public interface GameInterface {

    /**
     * Sets the associated GameController for this GameInterface.
     * @param controller The GameController to associate
     *     this interface with.
     */
    public void setController(GameController controller);

    /**
     * Displays the GameBoard associated with this GameInterface.
     */
    public void displayBoard();

    /**
     * Requests user input and returns that in the form of a String.
     * @return The user's action as a String.
     */
    public String requestUserAction();

    /**
     * Requests a list of players from the user and returns that
     * in the form of a Player array.
     * @return The list of players from the user.
     */
    public Player[] requestPlayers();

    /**
     * Displays a winning screen to the user.
     */
    public void displayWin(Player winningPlayer);

    /**
     * Displays a game over screen to the user.
     */
    public void displayGameOver();

}
