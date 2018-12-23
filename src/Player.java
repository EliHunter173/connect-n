/**
 * Defines all the information associated with a player.
 * @author Eli W. Hunter
 */
public class Player {

    /** A special player with an invalid ID and player type. */
    public static Player NONE = new Player();

    /** A unique constructor for the NONE player. */
    private Player() {
        this.id = -1;
        this.name = "Nobody";
        this.playerType = -1;
    }

    /** The number of current players. This is used to assign IDs sequentially. */
    private static int idCounter = 0;

    /** Resets the id counter. This is used when creating a new game. */
    public static void resetCounter() {
        idCounter = 0;
    }

    /**
     * Defines the Human player type. The actual value doesn't matter as long as its consistent.
     */
    public static final byte HUMAN = 0;
    /**
     * Defines the Random AI player type. The actual value doesn't matter as long as its consistent.
     */
    public static final byte RANDOM_AI = 1;
    /**
     * Defines the Simple AI player type. The actual value doesn't matter as long as its consistent.
     */
    public static final byte SIMPLE_AI = 2;

    /**
     * A Player's unique ID, which cannot be changed. This is used to determine
     * if two players are equal and assign names for players without a given name.
     */
    private final int id;
    /** A Player's display name.  */
    private final String name;
    /**
     * This determines a player's type, which determines how the controller
     * interacts with them.
     */
    private byte playerType; // Note: This is not final so we can make a player an AI if they forfeit

    /**
     * Creates a player with the given name and the player's type.  The ID is
     * assigned from the ID counter is incremented for future players.
     * @param name The name of the player to be created.
     * @param playerType The type of player the new Player object is.
     *     (i.e. Are they human, AI, etc.)
     * @throws IllegalArgumentException When the inputted name is empty.
     */
    public Player(String name, byte playerType) {
        if (name.equals(""))
            throw new IllegalArgumentException("A player cannot have an empty name.");

        this.id = idCounter++;
        this.name = name;
        this.playerType = playerType;
    }

    /**
     * Creates a player with the given player's type and then
     * assigns a name from their ID.
     * @param playerType The type of player the new Player object is.
     *      (i.e. Are they human, AI, etc.)
     */
    public Player(byte playerType) {
        this.id = idCounter++;
        this.name = "Player " + this.id;
        this.playerType = playerType;
    }

    /**
     * Accessor Method.
     * @return This player's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Accessor Method.
     * @return This player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor Method for playerType.
     * @return This player's playerType.
     */
    public byte getPlayerType() {
        return playerType;
    }

    /**
     * Determines whether two paleyrs are equal or not by seeing if they are
     * both players and have the same id.
     * @param other The other object that this Player object is being compared
     *     against.
     * @return True if both objects are players that have the same ID.
     *     False otherwise.
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
