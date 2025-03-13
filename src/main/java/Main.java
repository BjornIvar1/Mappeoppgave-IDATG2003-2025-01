import engine.SnakeAndLaddersGame;
import gui.LandingPage;
import model.Player;

public class Main {
  public static void main(String[] args) {
    SnakeAndLaddersGame game = new SnakeAndLaddersGame();
    LandingPage.main(args);
    game.createDice(2);
    game.addPlayer(new Player("Arne", game));
    game.addPlayer(new Player("Ivar", game));
    game.addPlayer(new Player("Majid", game));
    game.addPlayer(new Player("Atle", game));
    game.play();
  }
}
