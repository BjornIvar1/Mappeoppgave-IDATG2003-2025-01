import engine.BoardGame;
import gui.MainPage;
import model.Player;

public class Main {
  public static void main(String[] args) {
    BoardGame boardGame = new BoardGame();
    MainPage.main(args);
    boardGame.createBoard(90); // Creates a standard board of 90 linked tiles
    boardGame.createDice(2); // Creates 2 Dice
    boardGame.addPlayer(new Player("Arne", boardGame));
    boardGame.addPlayer(new Player("Ivar", boardGame));
    boardGame.addPlayer(new Player("Majid", boardGame));
    boardGame.addPlayer(new Player("Atle", boardGame));
    boardGame.play();
  }
}
