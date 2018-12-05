/**
 * Defines all the information associated with a token, which is owned by a player.
 * @author Eli W. Hunter
 */
public class Token {

    public static final Token EMPTY = new Token(null);

    /** The owner of this Token object, which cannot be changed after creation. */
    private final Player owner;

    /**
     * Creates a Token object with the given owner.
     * @param owner The Player who will be the "owner" of this token.
     */
    public Token(Player owner) {
        this.owner = owner;
    }

    /**
     * Accessor Method for owner.
     * @return The owner of this token as a Player object.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Determines whether two tokens are equal or not by seeing if they are both tokens
     * and have the same player.
     * @return True if both objects are tokens that are owned by the same player. False otherwise.
     */
    public boolean equals(Object other) {
        if (other instanceof Token) {
            Token otherToken = (Token) other;
            return this.owner.equals(otherToken.owner);
        } else {
            return false;
        }
    }

}
