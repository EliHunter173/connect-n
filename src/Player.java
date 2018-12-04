/**
 * Defines all the information associated with a player.
 * @author Eli W. Hunter
 */
public class Player {

    /** The number of current players. This is used to assign IDs sequentially. */
    private static int idCounter = 0;

    /** Resets the id counter. This is used when creating a new game. */
    public static void resetCounter() {
        idCounter = 0;
    }

    /**
     * Defines the Human player type. The actual value doesn't matter as long as its consistent.
     */
    public static final byte HUMAN = -1;
    /**
     * Defines the Random AI player type. The actual value doesn't matter as long as its consistent.
     */
    public static final byte RANDOM_AI = 0;

    /**
     * A Player's unique ID, which cannot be changed. This is used to determine
     * if two players are equal and assign names for players without a given name.
     */
    private final int id;
    /** A Player's display name.  */
    private final String name;
    /**
     * This determines a player's type, which determines how the controller interacts
     * with them.
     */
    private byte playerType; // Note: This is not final so we can make a player an AI if they forfeit

    /**
     * Creates a player with the given name and the player's type.  The ID is assigned from
     * the ID counter is incremented for future players.
     */
    public Player(String name, byte playerType) {
        this.id = ++idCounter; // We want the first player to be player 1 and idCounter to be the number of players
        this.name = name;
        this.playerType = playerType;
    }

    /**
     * Creates a player with the given player's type and then assigns a name from that ID.
     * The ID is assigned from the ID counter is incremented for future players.
     */
    public Player(byte playerType) {
        this.id = ++idCounter; // We want the first player to be player 1 and idCounter to be the number of players
        this.name = "Player " + this.id;
        this.playerType = playerType;
    }

    /**
     * Accessor Method for playerType.
     * @return This player's playerType.
     */
    public byte getPlayerType() {
        return playerType;
    }

    /**
     * Accessor Method for name.
     * @return This player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Determines whether two paleyrs are equal or not by seeing if they are both players
     * and have the same id.
     * @return True if both objects are players that have the same ID. False otherwise.
     */
    public boolean equals(Object other) {
        if (other instanceof Player) {
            Player otherPlayer = (Player) other;
            return this.id == otherPlayer.id;
        } else {
            return false;
        }
    }

}
