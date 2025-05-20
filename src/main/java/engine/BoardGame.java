package engine;

import filehandler.PlayerFileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Board;
import model.Player;
import model.Tile;
import utils.exception.NullOrBlankException;

/**
 * The class {@code engine.BoardGame} represents the whole game.
 * It includes methods for: adding a player, creating dice, creating a board
 * and playing the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.4.0
 * @since 0.0.1
 */
public class BoardGame {
  private final List<Player> players;
  private Board board;
  private Player currentPlayer;
  private Dice dice;
  private int currentPlayerIndex;

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
    PlayerFileWriter.writeToCsv(players, "src/main/resources/players/playersInGameFile.csv");
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

  public Iterator<Player> getPlayerIterator() {
    return players.iterator();
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
   * @throws NullOrBlankException if the board is null
   */
  public void createBoard(Board board) throws NullOrBlankException {
    if (board == null) {
      throw new NullOrBlankException("The board can not be null.");
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
   * Accessor method that returns the current player.
   *
   * @return currentPlayer
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * This method is responsible for playing the game.
   *
   * <p>It rolls the dice and moves the current player
   * to the next tile. If the player lands on a tile
   * that is not the last tile,
   * it will move to the next player.</p>
   */
  public void play() throws NullOrBlankException {
    if (currentPlayer == null) {
      players.forEach(player -> {
        currentPlayer = players.get(currentPlayerIndex);
        if (player.getCurrentTile() == null) {
          player.placeOnTile(board.getTile(1));
        } else {
          player.placeOnTile(player.getCurrentTile());
        }
      });
    }

    if (currentPlayer == null) {
      throw new NullOrBlankException("The current player can not be null.");
    }

    if (currentPlayer.isPlayerInJail()) {
      currentPlayer.setInJail(false);
      getUniqueCurrentPlayer();
    }
    int steps = dice.roll();
    currentPlayer.move(steps);

    Tile currentTile = currentPlayer.getCurrentTile();

    if (currentTile.getTileId() == board.getTileCount()) {
      return;
    }

    getUniqueCurrentPlayer();
  }

  /**
   * This method is responsible for getting the next player.
   *
   * <p>It will check if the current player is the last player<\p>
   */
  private void getUniqueCurrentPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    currentPlayer = players.get(currentPlayerIndex);
  }
}
