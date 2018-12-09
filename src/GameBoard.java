import java.util.*; // For Arrays

/**
 * Describes a 2D GameBoard of Tokens through a 1D array of Columns.
 * @author Eli W. Hunter
 */
public class GameBoard {

    // ERROR MESSAGES
    // I really wish I knew how to define my own errors, so I didn't have to
    // do this hacky stuff.
    /**
     * The error message displayed when the width is less than the number
     * of tokens to connect.
     */
    public static final String INVALID_HEIGHT_ERROR_MESSAGE =
        "The height must be greater than the number of tokens to connect.";
    /**
     * The error message displayed when the height is less than the number
     * of tokens to connect.
     */
    public static final String INVALID_WIDTH_ERROR_MESSAGE =
        "The width must be greater than the number of tokens to connect.";
    /** The error message displayed when the column value is invalid.  */
    public static final String INVALID_COL_ERROR_MESSAGE =
        "Column is out of bounds.";

    /** The (permanent) width of the GameBoard. (i.e. The number of columns.) */
    private final int width;
    /**
     * The (permanent) height of the GameBoard. (i.e. The max number of tokens
     * of each Column.)
     */
    private final int height;
    /**
     * The (permanent) number of tokens that must be connected for the board
     * to be won.
     */
    private final int tokensToConnect;
    /** The array of Column objects. Describes the GameBoard. */
    private Column[] columns;
    /** The number of tokens that have been added to the GameBoard. */
    private int numberOfTokens;

    /**
     * Generates a GameBoard object with the given width, height,
     * and number of tokens to connect.
     * @param width The width of the GameBoard. (i.e. The number of columns.)
     * @param height The height of the GameBoard. (i.e. The max number of tokens
     *     in each column).
     * @param tokensToConnect The number of tokens that must be connected for
     *     the board to be considered won.
     * @throws IllegalArgumentException When the specified height or width is
     *     less than the number of tokens to connect.
     */
    public GameBoard(int width, int height, int tokensToConnect) {
        if (width < tokensToConnect)
            throw new IllegalArgumentException(INVALID_WIDTH_ERROR_MESSAGE);
        if (height < tokensToConnect)
            throw new IllegalArgumentException(INVALID_HEIGHT_ERROR_MESSAGE);

        this.width = width;
        this.columns = new Column[width];

        this.height = height;
        for (int i = 0; i < width; i++) {
            columns[i] = new Column(height);
        }

        this.numberOfTokens = 0;
        this.empty();

        this.tokensToConnect = tokensToConnect;
    }

    /**
     * Accessor Method
     * @return The width of the GameBoard. (i.e. The number of columns.)
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Accessor Method
     * @return The height of the GameBoard. (i.e. The max number of tokens
     *     in each of column.)
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Accessor Method
     * @return The number of tokens to connect in the GameBoard for it to be
     *     declared won.
     */
    public int getTokensToConnect() {
        return this.tokensToConnect;
    }

    /**
     * Accessor Method
     * @return The number of tokens to connect in the GameBoard for it to be
     *     declared won.
     */
    public int getNumberOfTokens() {
        return numberOfTokens;
    }

    /**
     * Accessor Method
     * @return The max number of tokens the GameBoard can contain. Determined
     *     by finding the area of the GameBoard. (width * height)
     */
    public int getMaxNumberOfTokens() {
        return width * height;
    }

    /**
     * Finds and returns the Token at the specified row and column.
     * @param row The row (i.e. index in the column) that the desired token is at.
     * @param col The column that the desired token is at.
     * @return The token at the specifed column and row for this GameBoard.
     * @throws IllegalArgumentException When the column or row is not a valid
     *     index.
     */
    public Token getToken(int row, int col) {
        // Column takes care of row
        if (col < 0 || col >= width)
            throw new IllegalArgumentException(INVALID_COL_ERROR_MESSAGE);

        return columns[col].getToken(row);
    }

    /**
     * Adds the specified token to the specified column by calling the addToken()
     * method on that Column.
     * @param token The Token object to be added to the GameBoard.
     * @param col The index of the Column which will receive this Token.
     * @return True if the added token is part of a winning sequence. False
     *     otherwise.
     * @throws IllegalArgumentException When the column is not a valid index.
     */
    public boolean addToken(Token token, int col) {
        if (col < 0 || col > width)
            throw new IllegalArgumentException(INVALID_COL_ERROR_MESSAGE);

        numberOfTokens++;
        int row = columns[col].addToken(token);
        return this.isWinningPosition(row, col);
    }

    /**
     * Fills the GameBoard with empty tokens (Token.EMPTY objects) by filling
     * each column with empty tokens.
     */
    public void empty() {
        for (int i = 0; i < columns.length; i++) {
            columns[i].empty();
        }
    }

    /**
     * Checks if the given row and column is part of any winning sequences. That is,
     * if the position is part of a sequence that is tokensToConnect in length
     * in either the vertical, horizontal, positive diagonal, or negative diagonal
     * direction
     * @param row The row to be checked to see if it is part of any winning
     *     sequences.
     * @param col The column to be checked to see if it is part of any winning
     *     sequences.
     * @return True if the row and column are part of any winning sequences.
     *     False otherwise.
     * @throws IllegalArgumentException When the row or column value is not a valid
     *     value (i.e. less than 0 and greater than the width or height).
     */
    public boolean isWinningPosition(int row, int col) {
        if (row < 0 || row > height)
            throw new IllegalArgumentException(Column.INVALID_ROW_ERROR_MESSAGE);
        if (col < 0 || col > width)
            throw new IllegalArgumentException(INVALID_COL_ERROR_MESSAGE);

        return checkVertical(row, col)
               || checkHorizontal(row, col)
               || checkPositiveDiagonal(row, col)
               || checkNegativeDiagonal(row, col);
    }

    // Submethods of isWinningPosition()
    /**
     * Checks if the given row and column is part of any winning sequence in
     * the vertical direction.<br>
     * Precondition: The row and column are valid.
     * @param row The row to be checked to see if it is part of a vertical winning
     *     sequence.
     * @param col The column to be checked to see if it is part of a vertical
     *     winning sequence.
     * @return True if the row and column are part of a vertical winning sequence.
     *     False otherwise.
     */
    public boolean checkVertical(int row, int col) {
        // i.e. |. row is variable. col is constant.
        return checkSequence(row, col, 1, 0, tokensToConnect);
    }

    /**
     * Checks if the given row and column is part of any winning sequence in
     * the horizontal direction.<br>
     * Precondition: The row and column are valid.
     * @param row The row to be checked to see if it is part of a horizontal winning
     *     sequence.
     * @param col The column to be checked to see if it is part of a horizontal
     *     winning sequence.
     * @return True if the row and column are part of a horizontal winning sequence.
     *     False otherwise.
     */
    public boolean checkHorizontal(int row, int col) {
        // i.e. |. row is constant. col is variable.
        return checkSequence(row, col, 0, 1, tokensToConnect);
    }

    /**
     * Checks if the given row and column is part of any winning sequence in
     * the postitive diagonal direction.<br>
     * Precondition: The row and column are valid.
     * @param row The row to be checked to see if it is part of a postitive diagonal
     *     winning sequence.
     * @param col The column to be checked to see if it is part of a positive diagonal
     *     winning sequence.
     * @return True if the row and column are part of a positive diagonal winning sequence.
     *     False otherwise.
     */
    public boolean checkPositiveDiagonal(int row, int col) {
        // i.e. /. row is increasing, col is increasing.
        return checkSequence(row, col, 1, 1, tokensToConnect);
    }

    /**
     * Checks if the given row and column is part of any winning sequence in
     * the negative diagonal direction.<br>
     * Precondition: The row and column are valid.
     * @param row The row to be checked to see if it is part of a negative diagonal
     *     winning sequence.
     * @param col The column to be checked to see if it is part of a negative diagonal
     *     winning sequence.
     * @return True if the row and column are part of a negative diagonal winning sequence.
     *     False otherwise.
     */
    public boolean checkNegativeDiagonal(int row, int col) {
        // i.e. \. row is increasing. col is increasing.
        return checkSequence(row, col, 1, -1, tokensToConnect);
    }

    /**
     * Checks if the given row and column is part of the the specified
     * arbitrary sequence.<br>
     * Sequences are specified with a center row and column, a row and column
     * step size, and a number of tokens to check (which is used to determine
     * the number of steps to take in both directions.)<br>
     * Precondition: The row and column are valid.
     * @param anchorRow The center row of the sequence. A specific number of steps
     *     (from the number of tokens), is taken up and down from the row.
     * @param anchorCol The center column of the sequence. A specific number
     *     of steps (from the number of tokens), is taken left and right from
     *     the column.
     * @param rowStepSize The size of each individual step taken from row to
     *     row. This can be also thought of as the difference in row values between
     *     each adjacent cell in the sequence.
     * @param colStepSize The size of each individual step taken from column to
     *     column.  This can be also thought of as the difference in column values
     *     between each adjacent cell in the sequence.
     * @param numberOfTokens The number of tokens to be checked. This is used
     *     to determine the number of steps that are taken in both directions
     *     from the anchor row and anchor column.
     * @return True if the anchor row and anchor column are part of a winning
     *     sequence in the specified sequence.  False otherwise.
     */
    public boolean checkSequence(int anchorRow, int anchorCol, int rowStepSize, int colStepSize, int numberOfTokens) {
        // You want to find out if you have numberOfTokens in a row at least once for any linear
        // sequence with row step-size rowStepSize and column step-size colStepSize that contains your
        // current row (anchorRow) and current column (anchorCol).

        // You look at the token you're standing on and memorize it.
        Token anchorToken = this.getToken(anchorRow, anchorCol);
        // Empty tokens don't count
        if (anchorToken.equals(Token.EMPTY)) {
            return false;
        }

        int numberOfSteps = numberOfTokens - 1;
        // You decide to start out by going to the farthest back token and take one less
        // step backwards each time until you start on your anchor position.
        for (int stepsBack = numberOfSteps; stepsBack >= 0; stepsBack--) {

            int startingRow = anchorRow - rowStepSize * stepsBack;
            int startingCol = anchorCol - colStepSize * stepsBack;

            // Now, you start taking steps, each time making sure that the token you're
            // standing on is the same as your anchor token.
            boolean badSequence = false;
            for (int stepsTaken = 0; stepsTaken <= numberOfSteps; stepsTaken++) {

                int row = startingRow + stepsTaken * rowStepSize;
                int col = startingCol + stepsTaken * colStepSize;

                try {
                    Token currentToken = this.getToken(row, col);
                    if (!anchorToken.equals(currentToken))
                        badSequence = true;

                } catch (IllegalArgumentException e) {
                    badSequence = true;
                }

                if (badSequence)
                    break; // give up on this sequence

            }
            if (!badSequence) {
                return true;
            }

        }
        return false; // a good sequence was never found
    }
    // End submethods of isWinningPosition()

}
