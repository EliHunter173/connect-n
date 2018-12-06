import java.util.*; // For Arrays

public class GameBoard {

    // 3 is the minimum value possible to not have a guaranteed game
    public static final int MIN_TOKENS_TO_CONNECT = 3;
    // 32 is just absurdly big
    public static final int MAX_TOKENS_TO_CONNECT = 32;

    private final int width;
    private final int height;
    private final int tokensToConnect;
    private Column[] columns;

    public GameBoard(int width, int height, int tokensToConnect) {
        if (width < tokensToConnect || height < tokensToConnect) {
            throw new IllegalArgumentException("The width and height of the board must be greater"
                                               + " than the number of tokens to connect.");
        }
        if (tokensToConnect < MIN_TOKENS_TO_CONNECT) {
            throw new IllegalArgumentException("The number of tokens to connect is too low.");
        }
        if (tokensToConnect > MAX_TOKENS_TO_CONNECT) {
            throw new IllegalArgumentException( String.format(
                    "The number of tokens greater than the maximum. (%d)", MAX_TOKENS_TO_CONNECT));
        }

        this.width = width;
        this.columns = new Column[width];

        this.height = height;
        for (int i = 0; i < width; i++) {
            columns[i] = new Column(height);
        }

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

    public void empty() {
        for (int i = 0; i < columns.length; i++) {
            columns[i].empty();
        }
    }

    public Token getToken(int row, int col) {
        // Column takes care of row
        if (col < 0 || col >= width) {
            throw new IllegalArgumentException("That col value is out of bounds");
        }
        return columns[col].getToken(row);
    }

    public void addToken(Token token, int col) {
        if (col < 0 || col > width) {
            throw new IllegalArgumentException("That col value is out of bounds");
        }
        columns[col].addToken(token);
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

        System.out.printf("\nNEW CHECK: %d, %d, %d, %d, %d\n", anchorRow, anchorCol, rowStepSize, colStepSize, numberOfTokens);
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

            System.out.printf("Starting (%d,%d)\n", startingRow, startingCol);
            // Now, you start taking steps, each time making sure that the token you're
            // standing on is the same as your anchor token.
            boolean badSequence = false;
            for (int stepsTaken = 0; stepsTaken <= numberOfSteps; stepsTaken++) {

                int row = startingRow + stepsTaken * rowStepSize;
                int col = startingCol + stepsTaken * colStepSize;
                System.out.printf("(%d,%d) steps: %d\n", row, col, stepsTaken);

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
