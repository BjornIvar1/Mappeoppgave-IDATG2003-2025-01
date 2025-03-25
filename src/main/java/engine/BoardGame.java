package engine;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Player;
import model.Tile;

/**
 * The class {@code engine.BoardGame} represents the whole game.
 * It includes methods for: adding a player, creating dice, creating a board
 * and playing the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public class BoardGame {
  private final List<Player> players;
  private Board board;
  private Player currentPlayer;
  private Dice dice;

  /**
   * A constructor the {@code engine.BoardGame} class.
   */
  public BoardGame() {
    players = new ArrayList<>();
  }

  /**
   * A method for adding a player to the board game.
   * The player can not already exist or be {@code null}
   *
   * @param player the player to add
   */
  public void addPlayer(Player player) {
    if (!players.contains(player) && player != null) {
      players.add(player);
    }
  }

  /**
   * Retrieves a player that is participating in
   * the game.
   *
   * @return players.
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Creates a new set of dice with a specific amount,
   * using the constructor from the class engine.Dice.
   *
   * @param numberOfDice the number of dice to create
   */
  public void createDice(int numberOfDice) {
    this.dice = new Dice(numberOfDice);
  }

  /** Accessor the dice that are created.
   *
   * @return the dices that are created.
   */
  public Dice getDice() {
    return dice;
  }

  /**
   * Creates a new board for the game.
   * If board is null it throws an exception.
   *
   * @param board the board to create
   */
  public void createBoard(Board board) {
    if (board == null) {
      throw new IllegalArgumentException("The board can not be null.");
    }
    this.board = board;
  }

  /** Accessor method that returns the board.
   *
   * @return board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Simple board game for testing...
   */
  public void play() {
    boolean winner = false;
    int round = 0;

    System.out.println("The following players are playing the game:");
    for (Player player : players) {
      System.out.println(" -" + player.getName());
      player.placeOnTile(board.getTile(1));
    }

    while (!winner) {
      round++;
      System.out.println();
      System.out.println("Round: " + round);
      for (Player player : players) {
        int steps = dice.roll();
        player.move(steps);

        Tile currentTile = player.getCurrentTile();

        if (currentTile.getTileId() == board.getTiles().size()) {
          System.out.println("And the winner is... " + player.getName());
          winner = true;
          break;
        }

        System.out.println(player.getName() + " on tile " + player.getCurrentTile().getTileId());
      }
    }
  }
}
