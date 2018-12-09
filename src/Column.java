/**
 * Describes a column of tokens. Its size is constant and tokens cannot be
 * removed.
 * @author Eli W. Hunter
 */
public class Column {

    // ERROR MESSAGES
    // I really wish I knew how to define my own errors, so I didn't have to
    // do this hacky stuff.
    /** The error message displayed when the row value is invalid.  */
    public static final String INVALID_ROW_ERROR_MESSAGE =
        "Row is out of bounds.";
    /** The error message displayed when the Column is full. */
    public static final String FULL_COLUMN_ERROR_MESSAGE =
        "Column is full";

    /** The maximum possible number of tokens in the Column. */
    private final int maxTokens;
    /** The array of Token objects that make up this Column. */
    private Token[] tokens;
    /** The number of tokens currently in the Column. */
    private int numberOfTokens;

    /**
     * Generates a Column with the specified height.
     * @param height The height of the column, is associated with the
     *     maximum number of tokens a column can contain.
     */
    public Column(int height) {
        this.maxTokens = height;
        this.tokens = new Token[height];
        this.numberOfTokens = 0;
    }

    /**
     * Accessor Method
     * @return The max number of tokens this Column can contain.
     */
    public int getMaxTokens() {
        return maxTokens;
    }

    /**
     * Accessor Method
     * @param row The row (i.e. index) that the desired token is at.
     * @return The token at the specifed row (index in the tokens
     *     array) of this Column.
     * @throws IllegalArgumentException When the row is not a valid
     *     index for the Token Array.
     */
    public Token getToken(int row) {
        if (row < 0 || row >= maxTokens)
            throw new IllegalArgumentException(INVALID_ROW_ERROR_MESSAGE);

        return tokens[row];
    }

    /**
     * Adds a token to the lowest non-filled row of the Column.
     * The lowest non-filled row is kept track of by the numberOfTokens,
     * and since the array is zero-indexed, numberOfTokens is simply
     * the index of the lowest non-filled index.
     * @param Token The token which is to be added to this Column.
     * @return The row/index to which the Token object is added.
     */
    public int addToken(Token token) {
        if (numberOfTokens >= maxTokens)
            throw new IllegalArgumentException("Column is full");

        tokens[numberOfTokens] = token;
        return numberOfTokens++;
    }

    /**
     * Fills the row with empty tokens (Token.EMPTY objects).
     */
    public void empty() {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = Token.EMPTY;
        }
        numberOfTokens = 0;
    }

}
