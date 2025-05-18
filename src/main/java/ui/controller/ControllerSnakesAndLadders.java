package ui.controller;

import engine.BoardGame;
import filehandler.board.BoardFileReaderGson;
import filehandler.board.BoardFileWriter;
import filehandler.board.BoardFileWriterGson;
import filehandler.PlayerFileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import model.Player;
import model.tileactions.TileAction;
import ui.gui.menu.GameSelection;
import utils.Constants;
import utils.exception.NullOrBlankException;

/**
 * Controller for the Snakes and Ladders game.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.1.1
 */
public class ControllerSnakesAndLadders {
  private final SceneManager sceneManager;
  private final String boardFilePath;
  private BoardGame game;

  /**
   * Constructs a {@code ControllerSnakesAndLadders} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerSnakesAndLadders(SceneManager sceneManager, String boardFilePath) {
    this.sceneManager = sceneManager;
    this.boardFilePath = boardFilePath;
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
      playerReader.readCsvBuffered(Constants.PLAYER_FILE_PATH, boardGame);
      boardGame.createDice(2);
      boardGame.getPlayers().forEach(player -> player.placeOnTile(boardGame.getBoard().getTile(1)));
    } catch (IOException | NullOrBlankException e) {
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }

  public void saveGame() throws IOException {
    BoardFileWriter writer = new BoardFileWriterGson();
    writer.writeBoard(game.getBoard(), game.getPlayers(), Path.of(Constants.SNAKES_AND_LADDERS_SAVE_BOARD_FILE_PATH));
  }

  public void loadGame() throws IOException {
    BoardGame boardGame = new BoardGame();
    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of(Constants.SNAKES_AND_LADDERS_SAVE_BOARD_FILE_PATH)));
      playerReader.readCsvBuffered(Constants.PLAYER_FILE_PATH, boardGame);
      boardGame.createDice(2);
      //TODO siden du kan plasere spilleren på currenttile her
      // så kan man bare legge til currenttile i player konstruktøren og skrive den ned i Player filereader
      // og videre lese av en lagret fil av spillerene og så plassere dem på currentttile
      // igjen kan og currenttile altid være 1 i player?
      // kan dette gå ?
      // Mulig lage en ny branch for å teste dette
      boardGame.getPlayers().forEach(player -> player.placeOnTile(boardGame.getBoard().getTile(player.getCurrentTile())));
    } catch (IOException | NullOrBlankException e) {
      Logger.getLogger(ControllerSnakesAndLadders.class.getName())
          .warning("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
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
   * Returns the current players action on the specified tile.
   *
   * @param tileId the ID of the tile
   * @return the {@code TileAction} associated with the current player on the specified tile
   */
  public TileAction getCurrentPlayerAction(int tileId) {
    return game.getBoard().getTiles().get(tileId).getLandAction();
  }

  /**
   * Returns the list of players.
   *
   * @return the list of {@code Player}
   */
  public List<Player> getPlayers() {
    return game.getPlayers();
  }
}
