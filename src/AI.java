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
    public static final Random RAND = new Random();
    /**
     * How far to the left and right and up and down the AI should check when
     * scoring columns.
     */
    public static final int RADIUS = 1;
    /**
     * Determines how many points a match with a distance given by the index
     * is worth.
     */
    public static final int[] DISTANCE_POINTS = {0, 1};
    /**
     * Determines how many points a full column should be given.
     */
    public static final int FULL_COLUMN_SCORE = -1;

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
        if (player.getPlayerType() == Player.RANDOM_AI) {
            return randomColumn(board);

        } else if (player.getPlayerType() == Player.SIMPLE_AI) {
            int[] scores = scoreColumns(player, board);
            int highestScore = Utils.max(scores);
            int[] highestScoreIndexes = Utils.indexesOf(scores, highestScore);
            // This must have a length of at least one because highestScore
            // is in scores.
            return Utils.randomPick(highestScoreIndexes);

        } else {
            throw new IllegalArgumentException("Invalid player type");
        }
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
        return RAND.nextInt(max) + min;
    }

    /**
     * Scores each column by counting the number of adjacent tokens to the
     * position where the next token in that row would be added.
     * @param player The player for whom the columns are being scored.
     * @param board The game board that the AI is "looking at" to determine
     *     the scores.
     * @return An integer array of the scores of each column, with the index
     *     corresponding to the number of that column.
     */
    public static int[] scoreColumns(Player player, GameBoard board) {
        int[] scores = new int[board.getWidth()];
        for (int col = 0; col < scores.length; col++) {
            scores[col] = scoreColumn(player, board, col);
        }
        return scores;
    }

    /**
     * Scores an indiidual column by counting the number of adjacent tokens to the
     * position where the next token in that row would be added.
     * @param player The player for whom the columns are being scored.
     * @param board The game board that the AI is "looking at" to determine
     *     the scores.
     * @param col The column which is currently being scored.
     * @return The score of the column, determined by the weightings in
     *     DISTANCE_WEIGHTS.
     */
    public static int scoreColumn(Player player, GameBoard board, int col) {
        Token playerToken = new Token(player);

        Column scoringColumn = board.getColumn(col);
        int row = scoringColumn.getNextRow();
        if (scoringColumn.isFull())
            return FULL_COLUMN_SCORE;

        int score = 0;
        for (int deltaRow = -1 * RADIUS; deltaRow <= RADIUS; deltaRow++) {
            for (int deltaCol = -1 * RADIUS; deltaCol <= RADIUS; deltaCol++) {
                try {
                    Token currentToken = board.getToken(row + deltaRow, col + deltaCol);
                    if (playerToken.equals(currentToken)) {
                        score += findPointValue(deltaRow, deltaCol);
                    }
                } catch (IllegalArgumentException e) {
                    continue; // Ignore all bad rows
                }
            }
        }

        return score;
    }

    /**
     * Finds how many points a certain match should be worth given the
     * difference in the two tokens locations.
     * @param deltaRow How many rows apart the two token locations are.
     * @param deltaCol How many columns apart the two token locations are.
     * @return The number of points the match is worth
     */
    private static int findPointValue(int deltaRow, int deltaCol) {
        int rowDistance = Math.abs(deltaRow);
        int colDistance = Math.abs(deltaCol);
        int distance = Math.max(rowDistance, colDistance);
        return DISTANCE_POINTS[distance];
    }
}
