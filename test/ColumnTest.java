import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Tests Column class.
 * @author Eli W. Hunter
 */
public class ColumnTest extends TestCase {

    /** The height for the testing column */
    private static int COLUMN_HEIGHT = 4;

    /** Player Alice for testing */
    private Player alice;
    /** Player Cyborg for testing */
    private Player cyborg;
    /** Alice's Token for testing */
    private Token aliceToken;
    /** Cyborg's Token for testing */
    private Token cyborgToken;
    /** Testing Column for holding tokens created from Player objects */
    private Column column;

    @Before
    public void setUp() {
        Player.resetCounter();
        alice = new Player("Alice", Player.HUMAN);
        cyborg = new Player("Cyborg", Player.RANDOM_AI);
        aliceToken = new Token(alice);
        cyborgToken = new Token(cyborg);
        column = new Column(COLUMN_HEIGHT);
    }

    @Test
    public void testGetMaxTokens() {
        assertEquals("Column's maxTokens", COLUMN_HEIGHT, column.getMaxTokens());
    }

    @Test
    public void testAddTokenAndGetToken() {
        column.addToken(aliceToken);
        column.addToken(cyborgToken);
        assertEquals("First added token (alice)", aliceToken, column.getToken(0));
        assertEquals("First added token (cyborg)", cyborgToken, column.getToken(1));
    }

    @Test
    public void testAddTokenError() {
        column.addToken(aliceToken);
        column.addToken(aliceToken);
        column.addToken(cyborgToken);
        column.addToken(aliceToken);
        try {
            column.addToken(cyborgToken);
            fail(); // The test failed/was incomplete
        } catch (IllegalArgumentException e) {
            assertEquals("Correct add token error message",
                    "Column is full", e.getMessage());
        }
    }

    @Test
    public void testGetTokenError() {
        try {
            column.getToken(-1);
            fail(); // The test failed/was incomplete
        } catch (IllegalArgumentException e) {
            assertEquals("Correct get token error message",
                    "Invalid row", e.getMessage());
        }
        try {
            column.getToken(5);
            fail(); // The test failed/was incomplete
        } catch (IllegalArgumentException e) {
            assertEquals("Correct get token error message",
                    "Invalid row", e.getMessage());
        }
    }
}
