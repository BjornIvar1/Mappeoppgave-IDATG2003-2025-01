import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the whole game.
 *
 * @version 0.0.2
 */
public class BoardGame {
  private Board board;
  private Player currentPlayer;
  private List<Player>players;

  /**
   * A constructor the class BoardGame.
   */
  public BoardGame() {
    board = new Board();
    players = new ArrayList<>();
  }

  /**
   * A methods that allows the user to add a new player.
   *
   * @param player a new player.
   */
  private void addPlayer(Player player) {
    for (Player p : players) {
      if (!p.equals(player)) {
        players.add(player);
      }
    }
  }

  /*
  private void play() {

  }

   */

  /*
  private void getWinner(Player player) {
    currentPlayer = player;
  }

   */
}
