package ui.controller;

import engine.BoardGame;
import filehandler.PlayerFileReader;
import filehandler.PlayerFileWriter;
import filehandler.board.BoardFileReaderGson;
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
 * The controller for the Monopoly game.
 *
 * <p>This class is responsible for managing the interactions and logic specific to the
 * Monopoly game. It handles the initialization of the game board, player setup,
 * dice rolling, and checking for a winner. It also handles scene switches from
 * the monopoly to the {@link GameSelection}.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.0
 */
public class ControllerMonopoly {
  private final SceneManager sceneManager;
  private final String playerFilePath;
  private BoardGame boardGameForMonopoly;

  /**
   * Constructs a {@code ControllerMonopoly} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerMonopoly(SceneManager sceneManager, String playerFilePath) {
    this.sceneManager = sceneManager;
    this.playerFilePath = playerFilePath;
  }

  /**
   * Switches to the game selection menu.
   *
   * <p>This method updates the scene to display the {@code GameSelection} interface,
   * allowing users to choose between different games. it is called
   * when the user want to return to the game selection menu.</p>
   */
  public void switchToGameSelection() {
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }

  /**
   * Initializes the Monopoly game.
   *
   * <p>This method sets up the game board and players by
   * reading from the specified files.</p>
   */
  public void initializeMonopoly() {
    boardGameForMonopoly = initializeBoardGame();
  }

  /**
   * Retrieves the current board game instance.
   *
   * @return the current {@code BoardGame} instance
   */
  public BoardGame getBoardGame() {
    return boardGameForMonopoly;
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
   * Saves the current game state to a file.
   */
  public void saveGame() {
    PlayerFileWriter.writeToCsv(boardGameForMonopoly.getPlayers(), Constants.MONOPOLY_PLAYER_SAVED_CSV);
    //TODO listener
  }

  /**
   * Initializes the dice and simulates a turn in the game.
   *
   * <p>This method simulates a turn in the game by rolling the dice
   * and moving the current player to the next tile.
   * If the player lands on a tile that is not the last tile,
   * it will move to the next player.
   * an exception thrown when the tile is not found. </p>
   */
  public void initializeRollDice() {
    try {
      boardGameForMonopoly.play();
    } catch (NullOrBlankException e) {
      Logger.getLogger(ControllerMonopoly.class.getName());
    }
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
    return boardGameForMonopoly.getCurrentPlayer().getBalance()
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
    Iterator<Tile> tileIterator = boardGameForMonopoly.getBoard().getTileIterator();
    while (tileIterator.hasNext()) {
      Tile tile = tileIterator.next();
      if (tile.getTileId() == tileId) {
        return tile.getLandAction();
      }
    }
    return null; // Return null if no matching tile is found
  }

  public Iterator<Player> getPlayersIterator() {
    return boardGameForMonopoly.getPlayerIterator();
  }


}
