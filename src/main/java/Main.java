import engine.BoardGame;
import model.Player;

public class Main {
  public static void main(String[] args) {
    BoardGame boardGame = new BoardGame();
    Player player1 = new Player("Jon");
    Player player2 = new Player("Mona");
    Player player3 = new Player("Arpit");
    Player player4 = new Player("Bjørn");
    boardGame.addPlayer(player1);
    boardGame.addPlayer(player2);
    boardGame.addPlayer(player3);
    boardGame.addPlayer(player4);
    boardGame.createBoard(101);

    boardGame.play();
  }
}
