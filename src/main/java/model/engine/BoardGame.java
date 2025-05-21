package model.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.entity.Board;
import model.entity.Player;
import model.entity.Tile;
import observer.BoardGameObserver;
import observer.Subjects;
import utils.Constants;
import utils.exception.NullOrBlankException;

/**
 * The {@code engine.BoardGame} class represents the logic for a playing a board game.
 *
 * <p>The class manages players, the board, dice and the turn,
 * It also implements the {@link Subjects} interface for notifying the observers</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.7.0
 * @since 0.0.1
 */
public class BoardGame implements Subjects {
  private final List<Player> players;
  private Board board;
  private Dice dice;
  private int currentPlayerIndex;
  private final List<BoardGameObserver> observers;

  /**
   * A constructor the {@code engine.BoardGame} class.
   */
  public BoardGame() {
    players = new ArrayList<>();
    observers = new ArrayList<>();
  }

  /**
   * A method for adding a player to the board game.
   * The player can not already exist or be {@code null}
   *
   * @param player the {@link Player} to add
   */
  public void addPlayer(Player player) {
    if (!players.contains(player) && player != null) {
      players.add(player);
    }
  }

  /**
   * Returns the list of players in the game.
   *
   * <p>This method is only used in test classes</p>
   *
   * @return players.
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Returns an iterator for the players in the game.
   *
   * @return an iterator for the players
   */
  public Iterator<Player> getPlayerIterator() {
    return players.iterator();
  }

  /**
   * Creates a new set of dice with a specific amount,
   * using the constructor from the class {@link Dice}.
   *
   * @param numberOfDice the number of dice to create
   */
  public void createDice(int numberOfDice) {
    this.dice = new Dice(numberOfDice);
  }

  /**
   * Returns the {@link Dice} used in the game.
   *
   * @return the dices instance
   */
  public Dice getDice() {
    return dice;
  }

  /**
   * Creates a new board for the game.
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

  /**
   * Returns the {@link Board} used in the game.
   *
   * <p>This method is only used in test classes</p>
   *
   * @return board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns the player whose turn it currently is in the game.
   *
   * @return currentPlayer
   */
  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  /**
   * This method is responsible for playing the game.
   *
   * <p>It rolls the dice and moves the current player
   * to the next tile. If the player lands on a tile
   * that is not the last tile,
   * it will move to the next player.</p>
   *
   * @throws NullOrBlankException if there are no players in the game
   */
  public void play() throws NullOrBlankException {

    if (players.isEmpty()) {
      throw new NullOrBlankException("There are no players in the game.");
    }

    Player currentPlayer = players.get(currentPlayerIndex);

    if (currentPlayer.isPlayerIsSkipped()) {
      notifyObservers();
      currentPlayer.setInSkipped(false);
      goToNextPlayer();
      return;
    }
    int steps = dice.roll();
    currentPlayer.move(steps);
    notifyObservers();

    Tile currentTile = currentPlayer.getCurrentTile();

    if (currentTile.getTileId() == board.getTiles().size()) {
      notifyObservers();
      return;
    }
    notifyObservers();
    goToNextPlayer();
  }

  private void goToNextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  /**
   * Adds an observer to the list of observers.
   *
   * @param observer the observer to add
   */
  @Override
  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer the observer to remove
   */
  @Override
  public void removeObserver(BoardGameObserver observer) {
    observers.remove(observer);
  }

  /**
   * Notifies all registered observers about a change.
   *
   * <p>This method is called when a player moves to a new tile on the board
   * and how many steps.</p>
   */
  @Override
  public void notifyObservers() {
    Player currentPlayer = getCurrentPlayer();
    boolean isWinner = currentPlayer.getCurrentTile().getTileId() == board.getTiles().size();

    observers.forEach(observer -> {
      observer.observerPlayerWonInSnakesAndLadders(currentPlayer.getName(), isWinner);
      observer.observerPlayerWonInMonopoly(currentPlayer.getName(),
          currentPlayer.getBalance() >= Constants.WINNING_BALANCE);
      observer.observerIsPlayerSkipped(currentPlayer.getName(), currentPlayer.isPlayerIsSkipped());
      observer.observerPlayerMoved(currentPlayer.getName(), getDice().getLastRollS());
    });

    System.out.println("Current Player: " + currentPlayer.getName());
    System.out.println("Current Tile ID: " + currentPlayer.getCurrentTile().getTileId());
    System.out.println("Winning Tile ID: " + board.getTiles().size());
    System.out.println("Is Winner: " + isWinner);
  }
}
