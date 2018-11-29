public class Player {

    public static int numberOfPlayers = 0;

    private int id;
    private String name;

    public Player() {
        this.id = ++numberOfPlayers;
        this.name = "Player " + id;
    }

    public Player(String name) {
        this.id = ++numberOfPlayers;
        this.name = name;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
