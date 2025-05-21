package ui.controller;

import filehandler.board.BoardFileReaderGson;
import filehandler.board.BoardFileWriter;
import filehandler.board.BoardFileWriterGson;
import filehandler.player.PlayerFileReader;
import filehandler.player.PlayerFileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Logger;
import model.engine.BoardGame;
import model.entity.Player;
import model.entity.Tile;
import model.tileactions.TileAction;
import ui.gui.game.SnakesAndLaddersPage;
import ui.gui.menu.GameSelection;
import utils.Constants;
import utils.exception.NullOrBlankException;




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

  public void initializeGame1() {
    game.removeObserver(snakesAndLaddersPage);
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
   * <p>This method uses {@link BoardFileWriter} to save the current board used,
   * and {@link PlayerFileWriter} to save the players to a CSV file.</p>
   */
  public void saveGame() {
    try {
      BoardFileWriter writer = new BoardFileWriterGson();
      writer.writeBoard(game.getBoard(), Path.of(Constants.BOARD_SAVED_FILEPATH));
      PlayerFileWriter.writeToCsv(game.getPlayers(), Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV);
    } catch (IOException e) {
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning("Could not save game:: " + e.getMessage());
    }
  }

  /**
   * Checks whether the current player has won the game.
   *
   * <p>Returns {@code true} if a player have reached the final tile,
   * or {@code false} otherwise</p>
   */
  public boolean winningCondition() {
    return game.getCurrentPlayer().getCurrentTile().getTileId() == getTotalTiles();
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
  public Iterator<Player> getPlayers() {
    return game.getPlayerIterator();
  }
}
