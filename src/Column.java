public class Column {

    private final int maxTokens;
    private Token[] tokens;
    private int numberOfTokens;

    public int getMaxTokens() {
        return maxTokens;
    }

    public Column(int height) {
        this.maxTokens = height;
        this.tokens = new Token[height];
        this.numberOfTokens = 0;
    }

    public Token getToken(int row) {
        if (row < 0 || row >= maxTokens) {
            throw new IllegalArgumentException("Invalid row");
        }
        return tokens[row];
    }

    public int addToken(Token token) {
        if (numberOfTokens >= maxTokens) {
            throw new IllegalArgumentException("Column is full");
        }
        tokens[numberOfTokens] = token;
        return numberOfTokens++;
    }

    public void empty() {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = Token.EMPTY;
        }
        numberOfTokens = 0;
    }

}
