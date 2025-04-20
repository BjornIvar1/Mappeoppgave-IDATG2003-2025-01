package engine;

import java.util.ArrayList;
import java.util.List;
import filehandler.PlayerFileWriter;
import model.Board;
import model.PlayerInMonopoly;
import model.Tile;

/**
 * The class {@code engine.BoardGame} represents the whole game.
 * It includes methods for: adding a player, creating dice, creating a board
 * and playing the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
 * @since 0.0.1
 */
public class BoardGame {
  private final List<PlayerInMonopoly> players;
  private Board board;
  private PlayerInMonopoly currentPlayer;
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
  public void addPlayer(PlayerInMonopoly player) {
    if (!players.contains(player) && player != null) {
      players.add(player);
    }
    PlayerFileWriter.writeToCSV(players, "src/main/resources/players/playersInGameFile.csv");
  }

  /**
   * Retrieves a player that is participating in
   * the game.
   *
   * @return players.
   */
  public List<PlayerInMonopoly> getPlayers() {
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
   * Accessor method that returns the current player.
   *
   * @return currentPlayer
   */
  public PlayerInMonopoly getCurrentPlayer(){
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
  public void play() {
    if (currentPlayer == null) {
      players.forEach(player -> {
        currentPlayer = players.get(currentPlayerIndex);
        player.placeOnTile(board.getTile(1));
      });
    }

    if (currentPlayer == null) {
      throw new IllegalArgumentException("The current player can not be null.");
    }

    int steps = dice.roll();
    currentPlayer.move(steps);

    Tile currentTile = currentPlayer.getCurrentTile();

    if (currentTile.getTileId() == board.getTiles().size()) {
      return;
    }

    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    currentPlayer = players.get(currentPlayerIndex);
  }
}
