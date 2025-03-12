package engine;

import model.Board;
import model.Player;
import model.Tile;

import java.util.ArrayList;
import java.util.List;

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
  private List<Player> players;
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
    dice = new Dice(numberOfDice);
  }

  /** Accessor the dice that are created.
   *
   * @return the dices that are created.
   */
  public Dice getDice() {
    return dice;
  }

  /**
   * Creates the board with a specified amount of tiles.
   *
   */
  public void createBoard(int numberOfTiles) {
    board = new Board();

    checkTiles(numberOfTiles);
  }

  /** Accessor method that returns the board.
   *
   * @return board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * A method that creates and arranges the tiles on the board.
   *
   *<p>Each tile is linked together forming the board game,
   *using the {@link Tile#setNextTile(Tile)}.
   * </p>
   *
   * @param numberOfTiles the number of tiles to create
   */
  public void checkTiles(int numberOfTiles) {
    Tile previousTile = null;
    for (int i = 0; i < numberOfTiles; i++) {
      Tile tile = new Tile(i);
      board.addTile(tile);

      if (previousTile != null) {
        previousTile.setNextTile(tile);
      }
      previousTile = tile;
    }
  }

  /**
   * Access the number of tiles on the board.
   *
   * @return number of tiles on the board.
   */
  public int numberOfTiles() {
    return board.getTileCount();
  }

  /**
   * Simple board game for testing...
   */
  public void play() {
    boolean winner = false;
    int round = 0;
    createDice(8);
    System.out.println("The following players are playing the game:");
    for (Player player : players) {
      System.out.println(" -" + player.getName());
      player.placeOnTile(board.getTile(0));
    }

    while (!winner) {
      round++;
      System.out.println();
      System.out.println("Round: " + round);
      for (Player player : players) {
        int steps = dice.roll();
        player.move(steps);

        Tile currentTile = player.getCurrentTile();

        if (currentTile.getTileId() == board.getTiles().size() - 1) {
          System.out.println("And the winner is... " + player.getName());
          winner = true;
          break;
        }

        System.out.println(player.getName() + " on tile " + player.getCurrentTile().getTileId());
      }
    }
  }
}
