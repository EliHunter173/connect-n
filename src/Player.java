// TODO: This is somewhat deprecated. Reimplement it as the design document states.
public class Player {

    public static int idCounter = 0;

    private GameBoard game;
    private int id;
    private String name;
    private boolean isAI;

    public Player(GameBoard game) {
        this.game = game;
        this.id = ++idCounter;
        this.name = "Player " + id;
        this.boolean = true;
    }

    public Player(GameBoard game, String name) {
        this.game = game;
        this.id = ++idCounter;
        this.name = name;
        this.boolean = false;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object other) {
        if (other instanceof Player) {
            Player otherPlayer = (Player) other;
            return this.id == other.id;

        } else {
            return false;
        }
    }

    public int decideColumn() {
        // TODO: Implement me! At first, just do a random number generator, then be fancy.
    }

}
