package edu.ntnu.idi.bidata.ui.controller;

import edu.ntnu.idi.bidata.filehandler.board.BoardFileReaderGson;
import edu.ntnu.idi.bidata.filehandler.board.BoardFileWriter;
import edu.ntnu.idi.bidata.filehandler.board.BoardFileWriterGson;
import edu.ntnu.idi.bidata.filehandler.player.PlayerFileReader;
import edu.ntnu.idi.bidata.filehandler.player.PlayerFileWriter;
import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.entity.Tile;
import edu.ntnu.idi.bidata.model.tileactions.TileAction;
import edu.ntnu.idi.bidata.ui.gui.game.SnakesAndLaddersPage;
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
 * Controls the game logic for Snakes and Ladders.
 *
 * <p>This class iss responsible for interacting with the game engine,
 * loading and saving game data and providing data to the UI</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.2.1
 */
public class ControllerSnakesAndLadders {
  private final SceneManager sceneManager;
  private final String boardFilePath;
  private final String playerFilePath;
  private BoardGame game;
  private final SnakesAndLaddersPage snakesAndLaddersPage;

  /**
   * Constructs a {@code ControllerSnakesAndLadders} for the Snakes and Ladders game.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   * @param boardFilePath the path to the board file
   * @param playerFilePath the path to the player file
   */
  public ControllerSnakesAndLadders(SceneManager sceneManager,
                                    String boardFilePath, String playerFilePath) {
    this.sceneManager = sceneManager;
    this.boardFilePath = boardFilePath;
    this.playerFilePath = playerFilePath;
    snakesAndLaddersPage = new SnakesAndLaddersPage(this);
  }

  /**
   * Switches to the game selection scene.
   */
  public void switchToGameSelection() {
    game.removeObserver(snakesAndLaddersPage);
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }

  /**
   * Initializes the game by creating a new BoardGame,
   * and creating a board, dice and adding the players.
   */
  public void initializeGame() {
    game = initializeBoardGame();
    game.addObserver(snakesAndLaddersPage);
  }

  /**
   * Resets the Snakes and Ladders game
   * by reinitializing the board and placing players on the start tile.
   */
  public void resetGame() {
    game.removeObserver(snakesAndLaddersPage);
    game = initializeBoardGame();
    game.getPlayerIterator().forEachRemaining(player ->
        player.placeOnTile(game.getBoard().getTileById(1)));
    game.addObserver(snakesAndLaddersPage);
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
      boardGame.createBoard(reader.readBoard(Path.of(boardFilePath)));
      playerReader.readCsvBuffered(playerFilePath, boardGame);
      boardGame.createDice(2);
      boardGame.getPlayerIterator().forEachRemaining(player ->
          player.placeOnTile(boardGame.getBoard().getTileById(player.getCurrentTileId())));
    } catch (IOException | NullOrBlankException e) {
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }

  /**
   * Saves the current game state to a file.
   *
   * <p>This method collects the current players using an iterator,
   * and {@link PlayerFileWriter} to save the players to a CSV file.
   * It also saves the current board used by using {@link BoardFileWriter}.</p>
   *
   * @throws IOException if an error occurs while saving the game
   */
  public void saveGame() throws IOException {
    try {
      List<Player> players = new ArrayList<>();
      getPlayersIterator().forEachRemaining(players::add);

      BoardFileWriter writer = new BoardFileWriterGson();
      writer.writeBoard(game.getBoard(), Path.of(Constants.BOARD_SAVED_FILEPATH));
      PlayerFileWriter.writeToCsv(players, Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV);
    } catch (IOException e) {
      String errorMessage = "Could not save game: " + e.getMessage();
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning(errorMessage);
      throw new IOException(errorMessage);
    }
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
   * Returns the current {@link BoardGame} instance.
   *
   * @return the current {@code BoardGame} instance
   */
  public BoardGame getGame() {
    return game;
  }

  /**
   * Returns the number of tiles on the board.
   *
   * @return the total number of tiles on the board
   */
  public int getTotalTiles() {
    return game.getBoard().getColumns() * game.getBoard().getRows();
  }

  /**
   * Returns the number of columns on the board.
   *
   * @return the number of columns on the board
   */
  public int getColumns() {
    return game.getBoard().getColumns();
  }

  /**
   * Returns the number of rows on the board.
   *
   * @return the number of rows on the board
   */
  public int getRows() {
    return game.getBoard().getRows();
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
    return null;
  }

  /**
   * Returns the list of players.
   *
   * @return the list of {@code Player}
   */
  public Iterator<Player> getPlayersIterator() {
    return game.getPlayerIterator();
  }
}
