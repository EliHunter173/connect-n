import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Tests GameBoard class.
 * @author Eli W. Hunter
 */
public class GameBoardTest extends TestCase {

    /** The width for the testing game board */
    private static int WIDTH = 5;
    /** The height for the testing game board */
    private static int HEIGHT = 4;
    /** The number of tokens to connect for the testing game board */
    private static int TOKENS_TO_CONNECT = 4;

    /** Player Alice for testing */
    private Player alice;
    /** Player Cyborg for testing */
    private Player cyborg;
    /** Alice's Token for testing */
    private Token aliceToken;
    /** Cyborg's Token for testing */
    private Token cyborgToken;
    /** Testing game board */
    private GameBoard board;

    @Before
    public void setUp() {
        Player.resetCounter();
        alice = new Player("Alice", Player.HUMAN);
        cyborg = new Player("Cyborg", Player.RANDOM_AI);
        aliceToken = new Token(alice);
        cyborgToken = new Token(cyborg);
        board = new GameBoard(WIDTH, HEIGHT, TOKENS_TO_CONNECT);
    }

    @Test
    public void testGetWidth() {
        assertTrue("Board's assigned width", WIDTH == board.getWidth());
        assertTrue("Board's true width", 5 == board.getWidth());
        assertFalse("Board's false width", 62 == board.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertTrue("Board's assigned height", HEIGHT == board.getHeight());
        assertTrue("Board's true height", 4 == board.getHeight());
        assertFalse("Board's false height", 516 == board.getHeight());
    }

    @Test
    public void testGetTokensToConnect() {
        assertTrue("Board's assigned tokens to connect", TOKENS_TO_CONNECT == board.getTokensToConnect());
        assertTrue("Board's true tokens to connect", 4 == board.getTokensToConnect());
        assertFalse("Board's false tokens to connect", 920 == board.getTokensToConnect());
    }

    @Test
    public void testGetToken() {
        board.addToken(aliceToken, 1);
        assertTrue("Board's true first token (alice)", aliceToken.equals(board.getToken(0, 1)));
        board.addToken(cyborgToken, 1);
        assertFalse("Board's second token (cybog) with first token (alice)", cyborgToken.equals(board.getToken(0, 1)));
        assertTrue("Board's true second token (cyborg)", cyborgToken.equals(board.getToken(1, 1)));
        board.addToken(cyborgToken, 2);
        assertFalse("Board's third token (cyborg) with position swapped", cyborgToken.equals(board.getToken(2, 0)));
        assertTrue("Board's true third token (cyborg)", cyborgToken.equals(board.getToken(0, 2)));
    }

    @Test
    public void testCheckVertical() {
        assertFalse("Row 0 empty position", board.checkVertical(0,0));
        assertFalse("Row 1 empty position", board.checkVertical(1,0));
        assertFalse("Row 2 empty position", board.checkVertical(2,0));
        assertFalse("Row 3 empty position", board.checkVertical(3,0));
        board.addToken(aliceToken, 1);
        board.addToken(aliceToken, 1);
        board.addToken(aliceToken, 1);
        board.addToken(aliceToken, 1);
        assertTrue("Row 0 winning position", board.checkVertical(0,1));
        assertTrue("Row 1 winning position", board.checkVertical(1,1));
        assertTrue("Row 2 winning position", board.checkVertical(2,1));
        assertTrue("Row 3 winning position", board.checkVertical(3,1));
        board.addToken(aliceToken,  2);
        board.addToken(cyborgToken, 2);
        board.addToken(cyborgToken, 2);
        board.addToken(cyborgToken, 2);
        assertFalse("Row 0 not-winning position", board.checkVertical(0,2));
        assertFalse("Row 1 not-winning position", board.checkVertical(1,2));
        assertFalse("Row 2 not-winning position", board.checkVertical(2,2));
        assertFalse("Row 3 not-winning position", board.checkVertical(3,2));
    }

    @Test
    public void testCheckHorizontal() {
        assertFalse("Column 0 empty position", board.checkHorizontal(0,0));
        assertFalse("Column 1 empty position", board.checkHorizontal(0,1));
        assertFalse("Column 2 empty position", board.checkHorizontal(0,2));
        assertFalse("Column 3 empty position", board.checkHorizontal(0,3));
        board.addToken(aliceToken, 0);
        board.addToken(aliceToken, 1);
        board.addToken(aliceToken, 2);
        board.addToken(aliceToken, 3);
        assertTrue("Column 0 winning position", board.checkHorizontal(0,0));
        assertTrue("Column 1 winning position", board.checkHorizontal(0,1));
        assertTrue("Column 2 winning position", board.checkHorizontal(0,2));
        assertTrue("Column 3 winning position", board.checkHorizontal(0,3));
        board.addToken(aliceToken,  0);
        board.addToken(cyborgToken, 1);
        board.addToken(cyborgToken, 2);
        board.addToken(cyborgToken, 3);
        assertFalse("Column 0 not-winning position", board.checkHorizontal(1,0));
        assertFalse("Column 1 not-winning position", board.checkHorizontal(1,1));
        assertFalse("Column 2 not-winning position", board.checkHorizontal(1,2));
        assertFalse("Column 3 not-winning position", board.checkHorizontal(1,3));
    }

    @Test
    public void testCheckPositiveDiagonal() {
        assertFalse("Token (0,0) empty position", board.checkPositiveDiagonal(0,0));
        assertFalse("Token (1,1) empty position", board.checkPositiveDiagonal(1,1));
        assertFalse("Token (2,2) empty position", board.checkPositiveDiagonal(2,2));
        assertFalse("Token (3,3) empty position", board.checkPositiveDiagonal(3,3));

        // ADD TOKENS
        board.addToken(aliceToken, 0);

        board.addToken(aliceToken, 1);
        board.addToken(aliceToken, 1);

        board.addToken(aliceToken,  2);
        board.addToken(cyborgToken, 2);
        board.addToken(aliceToken,  2);

        board.addToken(cyborgToken, 3);
        board.addToken(aliceToken,  3);
        board.addToken(cyborgToken, 3);
        board.addToken(aliceToken,  3);

        board.addToken(aliceToken,  4);
        board.addToken(cyborgToken, 4);
        board.addToken(aliceToken,  4);
        board.addToken(cyborgToken, 4);
        // RESULT
        //    AC
        //   ACA
        //  ACAC
        // AAACA
        assertTrue("Token (0,0) winning position", board.checkPositiveDiagonal(0,0));
        assertTrue("Token (1,1) winning position", board.checkPositiveDiagonal(1,1));
        assertTrue("Token (2,2) winning position", board.checkPositiveDiagonal(2,2));
        assertTrue("Token (3,3) winning position", board.checkPositiveDiagonal(3,3));

        assertFalse("Token (0,1) non-winning position", board.checkPositiveDiagonal(0,1));
        assertFalse("Token (1,2) non-winning position", board.checkPositiveDiagonal(1,2));
        assertFalse("Token (2,3) non-winning position", board.checkPositiveDiagonal(2,3));
        assertFalse("Token (3,4) non-winning position", board.checkPositiveDiagonal(3,4));
    }

    @Test
    public void testCheckNegativeDiagonal() {
        assertFalse("Token (3,0) empty position", board.checkNegativeDiagonal(3,1));
        assertFalse("Token (2,1) empty position", board.checkNegativeDiagonal(2,2));
        assertFalse("Token (1,2) empty position", board.checkNegativeDiagonal(1,3));
        assertFalse("Token (0,3) empty position", board.checkNegativeDiagonal(0,4));

        // ADD TOKENS
        board.addToken(aliceToken,  0);
        board.addToken(cyborgToken, 0);
        board.addToken(aliceToken,  0);
        board.addToken(cyborgToken, 0);

        board.addToken(cyborgToken, 1);
        board.addToken(aliceToken,  1);
        board.addToken(cyborgToken, 1);
        board.addToken(aliceToken,  1);

        board.addToken(aliceToken,  2);
        board.addToken(cyborgToken, 2);
        board.addToken(aliceToken,  2);

        board.addToken(aliceToken, 3);
        board.addToken(aliceToken, 3);

        board.addToken(aliceToken, 4);
        // RESULT
        // CA
        // ACA
        // CACA
        // ACAAA
        assertTrue("Token (3,0) winning position", board.checkNegativeDiagonal(3,1));
        assertTrue("Token (2,1) winning position", board.checkNegativeDiagonal(2,2));
        assertTrue("Token (1,2) winning position", board.checkNegativeDiagonal(1,3));
        assertTrue("Token (0,3) winning position", board.checkNegativeDiagonal(0,4));

        assertFalse("Token (3,0) non-winning position", board.checkNegativeDiagonal(3,0));
        assertFalse("Token (2,1) non-winning position", board.checkNegativeDiagonal(2,1));
        assertFalse("Token (1,2) non-winning position", board.checkNegativeDiagonal(1,2));
        assertFalse("Token (0,3) non-winning position", board.checkNegativeDiagonal(0,3));
    }

    @Test
    public void testIsWinningPosition() {
        assertFalse("Token (0,0) empty board", board.isWinningPosition(3,1));

        board.empty();
        board.addToken(aliceToken, 0);
        board.addToken(aliceToken, 1);
        board.addToken(aliceToken, 2);
        board.addToken(aliceToken, 3);
        assertTrue("Token (0,0) horizontal match", board.isWinningPosition(0,0));
        assertFalse("Token (0,4) almost-horizontal match", board.isWinningPosition(0,4));

        board.empty();
        board.addToken(cyborgToken, 0);
        board.addToken(cyborgToken, 0);
        board.addToken(cyborgToken, 0);
        board.addToken(cyborgToken, 0);
        assertTrue("Token (3,0) vertical match", board.isWinningPosition(3,0));
        assertFalse("Token (0,3) no vertical match", board.isWinningPosition(0,3));

        board.empty();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board.addToken(cyborgToken, col);
            }
        }
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                assertTrue(String.format("Token (%d,%d) full, winning board", row, col),
                           board.isWinningPosition(row,col));
            }
        }

        // This board will never have a 4-way match
        board.empty();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                int var = (row % 4) / 2;
                if (col % 2 == var) {
                    board.addToken(cyborgToken, col);
                } else {
                    board.addToken(aliceToken, col);
                }
            }
        }
        // RESULT
        // ACACA
        // ACACA
        // CACAC
        // CACAC
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                System.out.println("HERE");
                assertFalse(String.format("Token (%d,%d) full, imperfect board", row, col),
                            board.isWinningPosition(row,col));
            }
        }

    }

}
