public interface GameInterface {

    private GameBoard game;

    public void takeTurn(Player player);
    public int requestUserInput();
    public void displayBoard();
}
