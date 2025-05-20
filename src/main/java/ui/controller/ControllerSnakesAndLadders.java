package ui.controller;

import engine.BoardGame;
import filehandler.PlayerFileReader;
import filehandler.PlayerFileWriter;
import filehandler.board.BoardFileReaderGson;
import filehandler.board.BoardFileWriter;
import filehandler.board.BoardFileWriterGson;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Logger;
import model.Player;
import model.Tile;
import model.tileactions.TileAction;
import ui.gui.menu.GameSelection;
import utils.Constants;
import utils.exception.NullOrBlankException;


/**
 * Controller for the Snakes and Ladders game.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.2.0
 */
public class ControllerSnakesAndLadders {
  private final SceneManager sceneManager;
  private final String boardFilePath;
  private final String playerFilePath;
  private BoardGame game;

  /**
   * Constructs a {@code ControllerSnakesAndLadders} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerSnakesAndLadders(SceneManager sceneManager,
                                    String boardFilePath,
                                    String playerFilePath) {
    this.sceneManager = sceneManager;
    this.boardFilePath = boardFilePath;
    this.playerFilePath = playerFilePath;
  }

  /**
   * Switches to the game selection scene.
   */
  public void switchToGameSelection() {
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }

  /**
   * Initializes the game by creating a new BoardGame,
   * and creating a board, dice and adding the players.
   */
  public void initializeGame() {
    game = initializeBoardGame();
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
      boardGame.createBoard(reader.readBoard(Path.of(boardFilePath)));
      playerReader.readCsvBuffered(playerFilePath, boardGame);
      boardGame.createDice(2);
      boardGame.getPlayerIterator().forEachRemaining(player ->
          player.placeOnTile(boardGame.getBoard().getTile(player.getCurrentTileId())));
    } catch (IOException | NullOrBlankException e) {
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }

  /**
   * Saves the current game state to a file.
   */
  public void saveGame() throws IOException {
    BoardFileWriter writer = new BoardFileWriterGson();
    writer.writeBoard(game.getBoard(), Path.of(Constants.BOARD_SAVED_FILEPATH));
    PlayerFileWriter.writeToCsv(game.getPlayers(), Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV);
  }

  /**
   * Returns the current game instance.
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
   * Returns the current players ID.
   *
   * @return the ID of the current player
   */
  public int getCurrentTileId() {
    return game.getCurrentPlayer().getCurrentTile().getTileId();
  }

  /**
   * Returns the current player's action for a specific tile.
   *
   * @param tileId the ID of the tile
   * @return the {@code TileAction} associated with the tile
   */
  public TileAction getCurrentPlayerAction(int tileId) {
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
   * Returns the list of players.
   *
   * @return the list of {@code Player}
   */
  public Iterator<Player> getPlayers() {
    return game.getPlayerIterator();
  }
}
