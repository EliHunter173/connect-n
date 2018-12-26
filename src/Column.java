/**
 * Describes a column of tokens. Its size is constant and tokens cannot be
 * removed.
 * @author Eli W. Hunter
 */
public class Column {
    //TODO: Implement isFull() to determine if row is full

    // ERROR MESSAGES
    // TODO: Investigate custom error messages.
    /** The error message displayed when the row value is invalid.  */
    public static final String INVALID_ROW_ERROR_MESSAGE =
        "Row is out of bounds.";
    /** The error message displayed when the Column is full. */
    public static final String FULL_COLUMN_ERROR_MESSAGE =
        "Column is full";

    /** The maximum possible number of tokens in the Column. */
    private final int height;
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
        this.height = height;
        this.tokens = new Token[height];
        this.numberOfTokens = 0;
    }

    /**
     * Accessor Method
     * @return The max number of tokens this Column can contain.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Accessor Method
     * @return The row of the next token to be added.
     */
    public int getNextRow() {
        return numberOfTokens;
    }

    /**
     * Determines if the Column is full, i.e. whether it can contain any
     * more tokens.
     * @return True if the column is full. False otherwise.
     */
    public boolean isFull() {
        return numberOfTokens >= height;
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
        if (row < 0 || row >= height)
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
        if (this.isFull())
            throw new IllegalArgumentException("Column is full");

        // TODO: Refactor this so that getNextRow() is used by the gamecontroller.
        tokens[numberOfTokens] = token;
        return numberOfTokens++;
    }

    /**
     * Remove a token from the highest filled row of the Column.
     */
    public void removeToken() {
        if (numberOfTokens <= 0) {
            throw new IllegalArgumentException("An empty column cannot have tokens removed");
        }

        tokens[--numberOfTokens] = Token.EMPTY;
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
