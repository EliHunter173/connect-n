import java.util.Random;

// I would have liked this to be an interface that declares an abstract
// decideColumn() method. However, the abstract and static keywords cannot
// be used together, so I have to do a simple class implementation.

/**
 * A class which contains all the calculations required to control an AI,
 * based off the specific board state and a given current player.
 * @author Eli W. Hunter
 */
public class AI {

    /**
     * Random number generator used for all necessary random number
     * generation throughout this class.
     */
    public static Random rand = new Random();

    /**
     * Decides which column a player should place their token, given the
     * current board state.
     * @param player The player for whom the AI is determining the "correct"
     *     move. The owner of the tokens the AI is looking for.
     * @param board The game board that the AI is "looking at" to determine
     *     the appropriate move. The game board which the AI look for tokens
     *     with the owner of the given player.
     * @return The column which the AI thinks the player should put their
     *     token.
     */
    public static int decideColumn(Player player, GameBoard board) {
        return randomColumn(board);
    }

    /**
     * Returns a random column in the given game board.
     * @param board The game board which is being used to find the maximum
     *     column.
     * @return A random column in the range of columns of the given game board.
     */
    public static int randomColumn(GameBoard board) {
        int min = 0;
        int max = board.getWidth();
        return rand.nextInt(max) + min;
    }

}
