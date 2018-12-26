import java.util.Random; // For RandomAI

/**
 * A class which contains and calls upon all of its children AI classes
 * as required to control a player on a specific game board, based off
 * the player's AI difficulty.
 * @author Eli W. Hunter
 */
public class AI {

    /**
     * The associated random AI object with this AI object. It is associated
     * with the same board.
     */
    public RandomAI randomAI;
    /**
     * The associated simple AI object with this AI object. It is associated
     * with the same board.
     */
    public SimpleAI simpleAI;
    /**
     * The associated intelligent AI object with this AI object. It is associated
     * with the same board.
     */
    public IntelligentAI intelligentAI;

    /**
     * Creates this AI object that simply contains its specific children AI
     * objects.
     */
    public AI(GameBoard board) {
        this.randomAI = new RandomAI(board);
        this.simpleAI = new SimpleAI(board);
        this.intelligentAI = new IntelligentAI(board);
    }

    /**
     * Decides what action the given player should do, assuming they are
     * playing on the AI's associated game board. It does this based of the
     * player's type.
     * @param player The player that the AI is deciding the action for.
     * @throws IllegalArgumentException When the given player does not have a
     *     recognized player type. (e.g. human)
     */
    public String decideAction(Player player) {
        if (player.getPlayerType() == Player.RANDOM_AI) {
            return randomAI.decideAction();
        } else if (player.getPlayerType() == Player.SIMPLE_AI) {
            return simpleAI.decideAction(player);
        } else if (player.getPlayerType() == Player.INTELLIGENT_AI) {
            return intelligentAI.decideAction(player);
        } else {
            throw new IllegalArgumentException("Invalid player type");
        }
    }

    /**
     * The AI for players with the Player.RANDOM_AI type.
     * @author Eli W. Hunter
     */
    private class RandomAI {

        /**
         * The game board that the AI uses to determine the bounds for the
         * decideAction() method.
         */
        public GameBoard board;
        /**
         * Random number generator used for the random number generation in this AI.
         */
        public Random rand;

        /**
         * Creates a random AI that is associated with a given game board.
         * @param board The game board that this AI is associated with.
         */
        public RandomAI(GameBoard board) {
            this.board = board;
            this.rand = new Random();
        }

        /**
         * Returns a random column in the given game board.
         * @return A random column in the range of columns of the given game board.
         */
        public String decideAction() {
            int max = board.getWidth();
            int col = rand.nextInt(max);
            return Integer.toString(col);
        }

    }

    private class SimpleAI {
        /**
         * How far to the left and right and up and down the AI should check when
         * scoring columns.
         */
        private final int RADIUS = 1;
        /**
         * Determines how many points a match with a distance given by the index
         * is worth. This must have RADIUS + 1 ({@value #RADIUS + 1}) entries.
         */
        private final int[] DISTANCE_POINTS = {0, 1};
        /**
         * Determines how many points a full column should be given.
         */
        private final int FULL_COLUMN_SCORE = -1;

        /**
         * The game board that the AI uses to determine the bounds for the
         * randomColumn() method.
         */
        public GameBoard board;

        /**
         * Creates a simple AI that is associated with a given game board.
         * @param board The game board that this AI is associated with.
         */
        public SimpleAI(GameBoard board) {
            this.board = board;
        }

        /**
         * Scores each column by how many tokens of the given player's type are
         * in the constant radius ({@value #RADIUS}) and then randomly chooses
         * from the highest scored column.
         * @return The highest score column if there is a single column with the
         *     highest score. If there is tie, a randomly chosen one.
         */
        public String decideAction(Player player) {
            int[] scores = scoreColumns(player);
            int highestScore = Utils.max(scores);
            int[] highestScoreIndexes = Utils.indexesOf(scores, highestScore);
            int randomIndex = Utils.randomPick(highestScoreIndexes);
            return Integer.toString(randomIndex);
        }

        /**
         * Scores each column by counting the number of adjacent tokens to the
         * position where the next token in that row would be added.
         * @param player The player for whom the columns are being scored.
         * @return An integer array of the scores of each column, with the index
         *     corresponding to the number of that column.
         */
        private int[] scoreColumns(Player player) {
            int[] scores = new int[board.getWidth()];
            for (int col = 0; col < scores.length; col++) {
                scores[col] = scoreColumn(player, col);
            }
            return scores;
        }

        /**
         * Scores an individual column by counting the number of adjacent
         * tokens to the position where the next token in that row would be
         * added.
         * @param player The player for whom the columns are being scored.
         * @param col The index of the column which is currently being scored.
         * @return The score of the column, determined by the weightings in
         *     DISTANCE_WEIGHTS.
         */
        private int scoreColumn(Player player, int col) {
            Token playerToken = new Token(player);

            Column scoringColumn = board.getColumn(col);
            int row = scoringColumn.getNextRow();
            if (scoringColumn.isFull()) {
                return FULL_COLUMN_SCORE;
            }

            int score = 0;
            for (int deltaRow = -1 * RADIUS; deltaRow <= RADIUS; deltaRow++) {
                for (int deltaCol = -1 * RADIUS; deltaCol <= RADIUS; deltaCol++) {
                    try {
                        Token currentToken = board.getToken(row + deltaRow, col + deltaCol);
                        if (playerToken.equals(currentToken)) {
                            score += findPointValue(deltaRow, deltaCol);
                        }
                    } catch (IllegalArgumentException e) {
                        continue; // Ignore all bad locations
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
        private int findPointValue(int deltaRow, int deltaCol) {
            // This method cannot be static, even though it would make sense
            // to, because there is no way to safely access a static method
            // inside a non-static inner class. This is because the static
            // method can reference non-static parts of the outer class, since
            // an inner class is inherently associated with an instance of the
            // outer class.
            int rowDistance = Math.abs(deltaRow);
            int colDistance = Math.abs(deltaCol);
            int distance = Math.max(rowDistance, colDistance);
            return DISTANCE_POINTS[distance];
        }

    }

    private class IntelligentAI {

        /** What checking sequence length the AI should start from. */
        private static final int STARTING_CHECK_LENGTH = 2;
        /**
         * The game board that the AI uses to determine the bounds for the
         * decideAction() method.
         */
        public GameBoard board;

        /**
         * Creates an intelligent AI that is associated with a given game board.
         * @param board The game board that this AI is associated with.
         */
        public IntelligentAI(GameBoard board) {
            this.board = board;
        }

        public String decideAction(Player player) {
            int[] columns = Utils.range(board.getWidth());
            columns = decideColumns(player, columns, STARTING_CHECK_LENGTH);
            int chosenColumn = Utils.randomPick(columns);

            return Integer.toString(chosenColumn);
        }

        public int[] decideColumns(Player player, int[] columns, int checkLength) {
            Token playerToken = new Token(player);

            // Filter by finding which of the given columns pass the check
            // length
            boolean[] hasSequenceLength = new boolean[columns.length];
            for (int i = 0; i < columns.length; i++) {
                int col = columns[i];
                int row = board.getColumn(col).getNextRow();

                try {
                    board.addToken(playerToken, col);
                    hasSequenceLength[i] = board.hasSequence(row, col, checkLength);
                    board.removeToken(col);
                } catch (IllegalArgumentException e) {
                    // If anything fails, it's a bad column
                    hasSequenceLength[i] = false;
                }
            }
            int[] goodColumns = Utils.filter(columns, hasSequenceLength);

            if (goodColumns.length == 0) {
                // If this round gave nothing, the last round was good
                return columns;
            } else {
                return decideColumns(player, goodColumns, checkLength + 1);
            }
        }

    }

}
