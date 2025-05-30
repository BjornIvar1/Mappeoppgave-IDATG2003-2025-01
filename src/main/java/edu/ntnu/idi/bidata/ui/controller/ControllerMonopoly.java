package edu.ntnu.idi.bidata.ui.controller;

import edu.ntnu.idi.bidata.filehandler.board.BoardFileReaderGson;
import edu.ntnu.idi.bidata.filehandler.player.PlayerFileReader;
import edu.ntnu.idi.bidata.filehandler.player.PlayerFileWriter;
import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.entity.Tile;
import edu.ntnu.idi.bidata.model.tileactions.TileAction;
import edu.ntnu.idi.bidata.ui.gui.game.MonopolyPage;
import edu.ntnu.idi.bidata.ui.gui.menu.GameSelection;
import edu.ntnu.idi.bidata.utils.Constants;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


/**
 * The controller for the Monopoly game.
 *
 *
 * <p>This class is responsible for interacting with the game engine and logic specific to the
 * Monopoly game. It handles the initialization of the game board, player setup,
 * dice rolling, and checking for a winner. It also handles scene switches from
 * the monopoly to the {@link GameSelection}.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.2
 */
public class ControllerMonopoly {
  private final SceneManager sceneManager;
  private final String playerFilePath;
  private BoardGame game;
  private final MonopolyPage monopolyPage;

  /**
   * Constructs a {@code ControllerMonopoly} with the specified scene manager,
   * and player file path.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   * @param playerFilePath the path to the player file
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
   * <p>This method sets up the game board and players from saved files
   * and registers the view as an observer.</p>
   */
  public void initializeMonopoly() {
    game = initializeBoardGame();
    game.addObserver(monopolyPage);
  }

  /**
   * Resets the Snakes and Ladders game
   * by reinitializing the board and placing players on the start tile.
   */
  public void resetGame() {
    game.removeObserver(monopolyPage);
    game = initializeBoardGame();
    game.getPlayerIterator().forEachRemaining(player ->
        player.placeOnTile(game.getBoard().getTileById(1)));
    game.getPlayerIterator().forEachRemaining(player ->
        player.setBalance(0));
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
      Logger.getLogger(ControllerMonopoly.class.getName())
          .warning("Failed to play turn: " + e.getMessage());
    }
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
   * Creates a new {@link BoardGame} by reading the board and player data from files.
   *
   * <p>Reads the board configuration from a JSON file,
   * and the player data from a CSV file. Initializes the game with two dice,
   * and places each player on the starting tile.</p>
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
          player.placeOnTile(boardGame.getBoard().getTileById(player.getCurrentTileId())));
    } catch (IOException | NullOrBlankException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }

  /**
   * Saves the current game state to a file.
   *
   * <p>This method collects the current players using an iterator,
   * and saves them to a CSV file using {@link PlayerFileWriter}.</p>
   */
  public void saveGame() {
    List<Player> players = new ArrayList<>();
    getPlayersIterator().forEachRemaining(players::add);
    PlayerFileWriter.writeToCsv(players, Constants.MONOPOLY_PLAYER_SAVED_CSV);
  }

  /**
   * Returns the current player's action for a specific tile.
   *
   * @param tileId the ID of the tile
   * @return the {@code TileAction} associated with the current tile, or {@code null}
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

  /**
   * Returns an iterator for players in the game.
   *
   * @return an {@code Iterator} of {@code Player} objects
   */
  public Iterator<Player> getPlayersIterator() {
    return game.getPlayerIterator();
  }
}
