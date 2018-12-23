import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Tests AI class.
 * @author Eli W. Hunter
 */
public class AITest extends TestCase {

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
        Player.resetCounter(); // Necessary for consistent player numbering
        alice = new Player("Alice", Player.HUMAN);
        cyborg = new Player("Cyborg", Player.RANDOM_AI);
        aliceToken = new Token(alice);
        cyborgToken = new Token(cyborg);
        board = new GameBoard(WIDTH, HEIGHT, TOKENS_TO_CONNECT);
    }

    @Test
    public void testScoreColumn() {
        board.addToken(aliceToken, 0);

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
        //   CAC
        // AAACA
        // 01234
        assertEquals("Column 0 score for alice", 2, AI.scoreColumn(alice, board, 0));
        assertEquals("Column 0 score for cyborg", 0, AI.scoreColumn(cyborg, board, 0));
        assertEquals("Column 1 score for alice", 4, AI.scoreColumn(alice, board, 1));
        assertEquals("Column 1 score for cyborg", 1, AI.scoreColumn(cyborg, board, 1));
    }

    @Test
    public void testScoreColumns() {
        // ADD TOKENS
        board.addToken(aliceToken,  0);
        board.addToken(cyborgToken, 0);
        board.addToken(aliceToken,  0);
        board.addToken(cyborgToken, 0);

        board.addToken(cyborgToken, 1);

        board.addToken(aliceToken,  2);
        board.addToken(cyborgToken, 2);
        board.addToken(aliceToken,  2);

        board.addToken(aliceToken, 4);
        // RESULT
        // C
        // A A
        // C C
        // ACA A
        // 01234
        int[] aliceColumnScores = {AI.FULL_COLUMN_SCORE, 4, 1, 2, 1};
        assertTrue("Column scores for alice",
                Arrays.equals(aliceColumnScores, AI.scoreColumns(alice, board)));
        int[] cyborgColumnScores = {AI.FULL_COLUMN_SCORE, 3, 0, 1, 0};
        assertTrue("Column scores for cyborg",
                Arrays.equals(cyborgColumnScores, AI.scoreColumns(cyborg, board)));
    }

}
