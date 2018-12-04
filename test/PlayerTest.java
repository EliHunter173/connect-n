import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Tests Player class.
 * @author Eli W. Hunter
 */
public class PlayerTest extends TestCase {

    /** Player Alice for testing */
    private Player alice;
    /** Player Cyborg for testing */
    private Player cyborg;
    /** Player 3 for testing */
    private Player player3;
    /** Player 4 for testing */
    private Player player4;

    @Before
    public void setUp() {
        Player.resetCounter();
        alice = new Player("Alice", Player.HUMAN);
        cyborg = new Player("Cyborg", Player.RANDOM_AI);
        player3 = new Player(Player.HUMAN);
        player4 = new Player(Player.RANDOM_AI);
    }

    @Test
    public void testGetName() {
        assertEquals("Alice's name", "Alice", alice.getName());
        assertEquals("Cyborg's name", "Cyborg", cyborg.getName());
        assertEquals("Player 3's name", "Player 3", player3.getName());
        assertEquals("Player 4's name", "Player 4", player4.getName());
    }

    @Test
    public void testGetPlayerType() {
        assertEquals("Alice's type", Player.HUMAN, alice.getPlayerType());
        assertEquals("Cyborg's type", Player.RANDOM_AI, cyborg.getPlayerType());
        assertEquals("Player 3's type", Player.HUMAN, player3.getPlayerType());
        assertEquals("Player 4's type", Player.RANDOM_AI, player4.getPlayerType());
    }

    @Test
    public void testEquals() {
        assertTrue("Alice with herself", alice.equals(alice));
        assertFalse("Alice with cyborg", alice.equals(cyborg));
        assertFalse("Cyborg with another AI named Cyborg",
                cyborg.equals(new Player("Cyborg", Player.RANDOM_AI)));
        assertFalse("Player 3 with another unnamed human",
                player3.equals(new Player(Player.HUMAN)));
    }

}
