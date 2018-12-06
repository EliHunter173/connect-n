// NOTE: This would actually make more sense as an abstract class,
// but we haven't been taught those yet.
public interface GameInterface {

    public void setController(GameController controller);

    public void displayBoard();

    public String requestUserAction();

    public Player[] requestPlayers();

    public void displayWin();

    public void displayGameOver();
}
