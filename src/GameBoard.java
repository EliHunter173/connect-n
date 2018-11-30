public class GameBoard {

    private Column[] columns;

    private final int width;
    private final int height;

    private final int tokensToConnect;

    // TODO: Decide on these values
    private static final int MIN_TOKENS_TO_CONNECT = null;
    private static final int MAX_TOKENS_TO_CONNECT = null;

    public int getWidth() {

    }

    public int getHeight() {

    }

    public int getTokensToConnect() {

    }

    public Token getToken(int row, int col) {

    }

    public void addToken(Token token, int col) {

    }

    public boolean isWinningPosition(int row, int col) {

    }

    // Submethods of isWinningToken()
    public boolean checkVertical(int row, int col) {

    }

    public boolean checkHorizontal(int row, int col) {

    }

    public boolean checkForwardDiagonal(int row, int col) {

    }

    public boolean checkBackwardDiagonal(int row, int col) {

    }
    // End submethods of isWinningToken()

}
