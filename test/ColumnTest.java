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
    /** Testing Column for holding tokens created from Player objects */
    private Column column;

    @Before
    public void setUp() {
        Player.resetCounter();
        alice = new Player("Alice", Player.HUMAN);
        cyborg = new Player("Cyborg", Player.RANDOM_AI);
        column = new Column(COLUMN_HEIGHT);
    }

    @Test
    public void testConstants() {
        assertEquals("Column's maxTokens", COLUMN_HEIGHT, column.getMaxTokens());
    }

    @Test
    public void testAddToken() {
        column.addToken(new Token(alice));
        column.addToken(new Token(cyborg));
        column.addToken(new Token(alice));
        assertEquals("First added token (alice)", new Token(alice), column.getToken(0));
        assertEquals("First added token (cyborg)", new Token(cyborg), column.getToken(1));
        assertEquals("First added token (alice)", new Token(alice), column.getToken(2));
    }

    @Test
    public void testEquals() {
        assertFalse("Alice's token with Cyborg's Token", aliceToken.equals(cyborgToken));
        assertTrue("Alice's token with another one of her tokens",
                aliceToken.equals(new Token(alice)));
        assertTrue("Cyborg's token with Cyborg's Token", cyborgToken.equals(cyborgToken));
    }

}
