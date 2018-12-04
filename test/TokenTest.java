import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Tests Token class.
 * @author Eli W. Hunter
 */
public class TokenTest extends TestCase {

    /** Player Alice for testing */
    private Player alice;
    /** Player Cyborg for testing */
    private Player cyborg;
    /** Alice's Token for testing */
    private Token aliceToken;
    /** Cyborg's Token for testing */
    private Token cyborgToken;


    @Before
    public void setUp() {
        Player.resetCounter();
        alice = new Player("Alice", Player.HUMAN);
        cyborg = new Player("Cyborg", Player.RANDOM_AI);
        aliceToken = new Token(alice);
        cyborgToken = new Token(cyborg);
    }

    @Test
    public void testGetOwner() {
        assertEquals("Alice's name", alice, aliceToken.getOwner());
        assertEquals("Cyborg's name", cyborg, cyborgToken.getOwner());
    }

    @Test
    public void testEquals() {
        assertFalse("Alice's token with Cyborg's Token", aliceToken.equals(cyborgToken));
        assertTrue("Alice's token with another one of her tokens",
                aliceToken.equals(new Token(alice)));
        assertTrue("Cyborg's token with Cyborg's Token", cyborgToken.equals(cyborgToken));
    }

}
