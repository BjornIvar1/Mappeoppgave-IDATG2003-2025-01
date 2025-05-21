package ui.controller;

import engine.BoardGame;
import filehandler.PlayerFileReader;
import filehandler.PlayerFileWriter;
import filehandler.board.BoardFileReaderGson;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import model.Player;
import model.Tile;
import model.tileactions.TileAction;
import ui.gui.game.MonopolyPage;
import ui.gui.menu.GameSelection;
import utils.Constants;
import utils.exception.NullOrBlankException;

/**
 * The controller for the Monopoly game.
 *
 * <p>This class is responsible for managing the interactions and logic specific to the
 * Monopoly game. It handles the initialization of the game board, player setup,
 * dice rolling, and checking for a winner. It also handles scene switches from
 * the monopoly to the {@link GameSelection}.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.1
 */
public class ControllerMonopoly {
  private final SceneManager sceneManager;
  private final String playerFilePath;
  private BoardGame game;
  private final MonopolyPage monopolyPage;

  /**
   * Constructs a {@code ControllerMonopoly} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerMonopoly(SceneManager sceneManager, String playerFilePath) {
    this.sceneManager = sceneManager;
    this.playerFilePath = playerFilePath;
    this.monopolyPage = new MonopolyPage(this);
  }

  /**
   * Switches to the game selection menu.
   *
   * <p>This method updates the scene to display the {@code GameSelection} interface,
   * allowing users to choose between different games. it is called
   * when the user want to return to the game selection menu.</p>
   */
  public void switchToGameSelection() {
    game.removeObserver(monopolyPage);
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }

  /**
   * Initializes the Monopoly game.
   *
   * <p>This method sets up the game board and players by
   * reading from the specified files.</p>
   */
  public void initializeMonopoly() {
    game = initializeBoardGame();
    game.addObserver(monopolyPage);
  }

  /**
   * Rolls the dice and performs the player's turn.
   *
   * <p>Handles player movement and catches any data related errors</p>
   */
  public void rollDice() {
    try {
      game.play();
    } catch (NullOrBlankException e) {
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning("Failed to play turn: " + e.getMessage());
    }
  }

  /**
   * Calculates the sum of the values of all dice.
   *
   * @return the sum of the values of all dice
   */
  public int getDieSum() {
    int sum = 0;
    for (int i = 0; i < game.getDice().getNumberOfDice(); i++) {
      sum += game.getDice().getDie(i);
    }
    return sum;
  }

  /**
   * Retrieves the current board game instance.
   *
   * @return the current {@code BoardGame} instance
   */
  public BoardGame getGame() {
    return game;
  }

  /**
   * Initializes the board game by reading the board and player data from files.
   *
   * <p>This method creates a new {@code BoardGame} instance,
   * reads the board configuration from a JSON file,
   * and initializes the players from a CSV file.
   * It also places each player on the starting tile of the board.</p>
   *
   * @return a new {@code BoardGame} instance with the initialized board and players
   */
  private BoardGame initializeBoardGame() {
    BoardGame boardGame = new BoardGame();
    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of(Constants.MONOPOLY_BOARD_FILE_PATH)));
      playerReader.readCsvBuffered(playerFilePath, boardGame);
      boardGame.createDice(2);
      boardGame.getPlayerIterator().forEachRemaining(player ->
          player.placeOnTile(boardGame.getBoard().getTile(player.getCurrentTileId())));
    } catch (IOException | NullOrBlankException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }


  /**
   * Saves the current game state to a CSV file.
   *
   * <p>This method saves the current players data to a CSV file
   * using the {@code PlayerFileWriter} class.</p>
   */
  public void saveGame() {
    Iterator<Player> playerIterator = getPlayersIterator();
    // Get the iterator for players
    List<Player> players = new ArrayList<>(); // Create a list to store players
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next(); // Get the next player
      players.add(player); // Add the player to the list
    }
    PlayerFileWriter.writeToCsv(players, Constants.MONOPOLY_PLAYER_SAVED_CSV);
    // Save the players to a CSV file
    players.clear(); // Clear the list after saving
  }

  /**
   * Checks if a player has won the game.
   *
   * <p>This method determines if the current player
   * has reached the winning balance.</p>
   *
   * @return {@code true} if the current player has won, otherwise {@code false}.
   */
  public boolean winnerFound() {
    return game.getCurrentPlayer().getBalance()
        >= Constants.WINNING_BALANCE;
  }

  /**
   * Retrieves the tile action for a specific tile.
   *
   * <p>This method returns the {@code TileAction} associated with the specified tile ID.</p>
   *
   * @param tileId the ID of the tile
   * @return the {@code TileAction} associated with the specified tile ID
   */
  public TileAction getTileAction(int tileId) {
    Iterator<Tile> tileIterator = game.getBoard().getTileIterator();
    while (tileIterator.hasNext()) {
      Tile tile = tileIterator.next();
      if (tile.getTileId() == tileId) {
        return tile.getLandAction();
      }
    }
    return null; // Return null if no matching tile is found
  }

  public Iterator<Player> getPlayersIterator() {
    return game.getPlayerIterator();
  }
}
