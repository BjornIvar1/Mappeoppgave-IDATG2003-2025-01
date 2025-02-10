import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the whole game.
 *
 * @version 0.0.1
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
    players.add(player);
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
