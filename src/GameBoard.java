import java.util.*; // For Arrays

public class GameBoard {

    // 3 is the minimum value possible to not have a guaranteed game
    public static final int MIN_TOKENS_TO_CONNECT = 3;
    // 32 is just absurdly big
    public static final int MAX_TOKENS_TO_CONNECT = 32;

    public static final String HIGH_TOKENS_ERROR_MESSAGE =
        String.format("The number of tokens exceeds the maximum. (%d)", MAX_TOKENS_TO_CONNECT);
    public static final String LOW_TOKENS_ERROR_MESSAGE =
        String.format("The number of tokens cannot be less than the the minimum. (%d)", MIN_TOKENS_TO_CONNECT);
    public static final String INVALID_HEIGHT_ERROR_MESSAGE =
        "The height of the board must be greater than the number of tokens to connect.";
    public static final String INVALID_WIDTH_ERROR_MESSAGE =
        "The height of the board must be greater than the number of tokens to connect.";

    private final int width;
    private final int height;
    private final int tokensToConnect;
    private Column[] columns;
    private int numberOfTokens;

    public GameBoard(int width, int height, int tokensToConnect) {
        if (width < tokensToConnect )
            throw new IllegalArgumentException(INVALID_WIDTH_ERROR_MESSAGE);
        if (height < tokensToConnect)
            throw new IllegalArgumentException(INVALID_HEIGHT_ERROR_MESSAGE);
        if (tokensToConnect < MIN_TOKENS_TO_CONNECT)
            throw new IllegalArgumentException(LOW_TOKENS_ERROR_MESSAGE);
        if (tokensToConnect > MAX_TOKENS_TO_CONNECT)
            throw new IllegalArgumentException(HIGH_TOKENS_ERROR_MESSAGE);

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

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTokensToConnect() {
        return this.tokensToConnect;
    }

    public int getNumberOfTokens() {
        return numberOfTokens;
    }

    public int getMaxNumberOfTokens() {
        return width * height;
    }
    public Token getToken(int row, int col) {
        // Column takes care of row
        if (col < 0 || col >= width) {
            throw new IllegalArgumentException("That col value is out of bounds");
        }
        return columns[col].getToken(row);
    }

    public void empty() {
        for (int i = 0; i < columns.length; i++) {
            columns[i].empty();
        }
    }

    public int addToken(Token token, int col) {
        if (col < 0 || col > width) {
            throw new IllegalArgumentException("That col value is out of bounds");
        }

        numberOfTokens++;
        return columns[col].addToken(token);
    }

    public boolean isWinningPosition(int row, int col) {
        if (row < 0 || row > height) {
            throw new IllegalArgumentException("That row value is out of bounds");
        }
        if (col < 0 || col > width) {
            throw new IllegalArgumentException("That col value is out of bounds");
        }
        return checkVertical(row, col)
               || checkHorizontal(row, col)
               || checkPositiveDiagonal(row, col)
               || checkNegativeDiagonal(row, col);
    }

    // Submethods of isWinningPosition()
    public boolean checkVertical(int row, int col) {
        // PRECONDITION: Valid row and col values.
        // i.e. |. row is variable. col is constant.
        return checkSequence(row, col, 1, 0, tokensToConnect);
    }

    public boolean checkHorizontal(int row, int col) {
        // PRECONDITION: Valid row and col values.
        // i.e. |. row is constant. col is variable.
        return checkSequence(row, col, 0, 1, tokensToConnect);
    }

    public boolean checkPositiveDiagonal(int row, int col) {
        // PRECONDITION: Valid row and col values.
        // i.e. /. row is increasing, col is increasing.
        return checkSequence(row, col, 1, 1, tokensToConnect);
    }

    public boolean checkNegativeDiagonal(int row, int col) {
        // PRECONDITION: Valid row and col values.
        // i.e. \. row is increasing. col is increasing.
        return checkSequence(row, col, 1, -1, tokensToConnect);
    }

    public boolean checkSequence(int anchorRow, int anchorCol, int rowStepSize, int colStepSize, int numberOfTokens) {
        // PRECONDITION: Valid row and col values.
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
    public static void main(String[] args) {
        GameBoard test = new GameBoard(5, 5, 3);
        Player player = new Player(Player.HUMAN);
        test.addToken(new Token(player), 0);
        test.addToken(new Token(player), 0);
        test.addToken(new Token(player), 0);

        System.out.println("\n" + test.isWinningPosition(0, 1));
    }

}
