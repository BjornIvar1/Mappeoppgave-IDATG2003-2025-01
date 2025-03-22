import engine.BoardGame;
import engine.SnakeAndLaddersGame;
import gui.MainPage;
import model.Player;

public class Main {
  public static void main(String[] args) {
    SnakeAndLaddersGame game = new SnakeAndLaddersGame();
    BoardGame boardGame = new BoardGame();
    MainPage.main(args);
    game.createDice(2);
    game.addPlayer(new Player("Arne", game));
    game.addPlayer(new Player("Ivar", game));
    game.addPlayer(new Player("Majid", game));
    game.addPlayer(new Player("Atle", game));

    //For testing purposes.
    boardGame.addPlayer(new Player("Arne", game));
    boardGame.addPlayer(new Player("Ivar", game));
    boardGame.addPlayer(new Player("Majid", game));
    boardGame.addPlayer(new Player("Atle", game));
    game.play();
  }
}
