public class GameBoard {

    private static final int MIN_TOKENS_TO_CONNECT = 1;

    private final int tokensToConnect;

    private final int width;
    private final int height;

    private Token[][] board;

    public GameBoard(int tokensToConnect, int width, int height) {
        if (tokensToConnect > tokensToConnect) {
            throw new IllegalArgumentException("A board must have a minimum of "
                                               + MIN_TOKENS_TO_CONNECT
                                               + " tokens to connect.")
        }

        if (width < tokensToConnect || height < tokensToConnect) {
            throw new IllegalArgumentException(
                    "The game board must be at least as large as a square with sides"
                    + " as long as the number of tokens to connect.");
        }

        this.tokensToConnect = tokensToConnect;

        this.width = width;
        this.height = height;

        this.board = Token[height][width];
    }

    public void addToken(int col) {

    }

    public Token getToken(int row, int col) {

    }

    public boolean isWinningToken(int row, int col) {
        return checkUp(row, col) && checkDown(row, col) &&
               checkForwardDiagonal(row, col) && checkBackwardDiagonal(row, col);
    }

    public boolean checkUp(int row, int col) {

    }

    public boolean checkDown(int row, int col) {

    }

    public boolean checkForwardDiagonal(int row, int col) {

    }

    public boolean checkBackwardDiagonal(int row, int col) {

    }

}
