package edu.ntnu.idi.bidata.filehandler.board;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Tile;
import edu.ntnu.idi.bidata.model.tileactions.BankAction;
import edu.ntnu.idi.bidata.model.tileactions.LadderAction;
import edu.ntnu.idi.bidata.model.tileactions.LooseMoneyAction;
import edu.ntnu.idi.bidata.model.tileactions.SkipAction;
import edu.ntnu.idi.bidata.model.tileactions.SnakeAction;
import edu.ntnu.idi.bidata.model.tileactions.TileAction;
import edu.ntnu.idi.bidata.utils.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Class is responsible for reading a board from a file using Gson.
 *
 * <p>Reads the file and parses tiles, board dimensions, tile connections, and tile actions.
 * Returns a new {@link Board} object</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.5.1
 * @since 0.0.1
 */
public class BoardFileReaderGson implements BoardFileReader {

  /**
   * Empty constructor for {@link BoardFileReaderGson}.
   */
  public BoardFileReaderGson() {
    // Default constructor, not in use.
  }

  /**
   * Reads a {@link Board} from a JSON file using Gson.
   *
   * @param filePath the path to the JSON file
   * @return the {@link  Board} object read from the file
   * @throws IOException if the file could not be read
   */
  @Override
  public Board readBoard(Path filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
      JsonObject rootObj = JsonParser.parseReader(reader).getAsJsonObject();
      JsonArray tilesArray = rootObj.getAsJsonArray("tiles");
      int rows = rootObj.get("rows").getAsInt();
      int columns = rootObj.get("columns").getAsInt();
      Board board = new Board(rows, columns);

      Map<Integer, Tile> tileMap = getTileMap(tilesArray, board);
      linkNextTile(tilesArray, tileMap);
      createTileAction(tilesArray, tileMap, rootObj);

      return board;
    }
  }

  /**
   * Creates the tiles from the JSON array and adds them to the board.
   *
   * <p>Each tile is created with an id and its position using x and y coordinates,
   *   and stores the tile in a {@link  HashMap}.</p>
   *
   * @param tilesArray the JSON array containing the tiles
   * @param board the board where the tiles will be added
   * @return the map of tiles
   */
  private static Map<Integer, Tile> getTileMap(JsonArray tilesArray, Board board) {
    Map<Integer, Tile> tileMap = new HashMap<>();
    for (JsonElement element : tilesArray) {
      JsonObject tileObj = element.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      int x = tileObj.get("x").getAsInt();
      int y = tileObj.get("y").getAsInt();

      Tile tile = new Tile(id, x, y);
      board.addTile(tile);
      tileMap.put(id, tile);
    }
    return tileMap;
  }

  /**
   * Links each tile to its next tile using {@code setNextTile()} method.
   *
   * @param tilesArray the JSON array containing the tiles
   * @param tileMap the map of tiles
   */
  private static void linkNextTile(JsonArray tilesArray, Map<Integer, Tile> tileMap) {
    for (JsonElement element : tilesArray) {
      JsonObject tileObj = element.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      Tile currentTile = tileMap.get(id);

      if (tileObj.has("nextTile")) {
        int nextTileId = tileObj.get("nextTile").getAsInt();
        Tile nextTile = tileMap.get(nextTileId);
        currentTile.setNextTile(nextTile);
      }
    }
  }

  /**
   * Creates a tile action for the tiles.
   *
   * <p>Supports both board games Snakes and Ladders and Monopoly tile actions</p>
   *
   * @param tilesArray the JSON array containing the tiles
   * @param tileMap the map of tiles
   */
  private static void createTileAction(JsonArray tilesArray,
                                       Map<Integer, Tile> tileMap,
                                       JsonObject rootObj) {
    for (JsonElement element : tilesArray) {
      JsonObject tileObj = element.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      Tile currentTile = tileMap.get(id);
      if (tileObj.has(Constants.ACTION)
          && "Snakes and Ladders".equals(rootObj.get("name").getAsString())) {
        JsonObject actionObj = tileObj.getAsJsonObject(Constants.ACTION);
        TileAction tileAction = createTileActionSnakesAndLadders(actionObj);
        currentTile.setLandAction(tileAction);
      } else if (tileObj.has(Constants.ACTION)
          && "Monopoly".equals(rootObj.get("name").getAsString())) {
        JsonObject actionObj = tileObj.getAsJsonObject(Constants.ACTION);
        TileAction tileAction = createTileMonopolyAction(actionObj);
        currentTile.setLandAction(tileAction);
      } else {
        currentTile.setLandAction(null);
      }
    }
  }

  /**
   * Creates a {@link TileAction} for Snakes and Ladders.
   *
   * <p>Supports actions of type {@link LadderAction} and {@link SnakeAction}</p>
   *
   * @param actionObj the JSON object of the action
   * @return either the {@link TileAction} or {@code null} if the action is not supported
   */
  private static TileAction createTileActionSnakesAndLadders(JsonObject actionObj) {
    String type = actionObj.get("type").getAsString();
    if ("LadderAction".equals(type)) {
      int destinationTileId = actionObj.get("destinationTileId").getAsInt();
      String description = actionObj.get(Constants.DESCRIPTION).getAsString();
      return new LadderAction(destinationTileId, description);
    } else if ("SnakeAction".equals(type)) {
      int destinationTileId = actionObj.get("destinationTileId").getAsInt();
      String description = actionObj.get(Constants.DESCRIPTION).getAsString();
      return new SnakeAction(destinationTileId, description);
    } else if ("SkipAction".equals(type)) {
      return getSkipAction(actionObj);
    }
    return null;
  }

  /**
   * Creates a {@link TileAction} for Snakes and Ladders.
   *
   * <p>Supports actions of type {@link BankAction}, {@link LooseMoneyAction},
   * and {@link SkipAction}</p>
   *
   * @param actionObj the JSON object of the action
   * @return either the {@link TileAction} or {@code null} if the action is not supported
   */
  private static TileAction createTileMonopolyAction(JsonObject actionObj) {
    String type = actionObj.get("type").getAsString();
    if ("BankAction".equals(type)) {
      int amount = actionObj.get("amount").getAsInt();
      String description = actionObj.get(Constants.DESCRIPTION).getAsString();
      return new BankAction(amount, description);
    } else if ("LooseMoneyAction".equals(type)) {
      int amount = actionObj.get("amount").getAsInt();
      String description = actionObj.get(Constants.DESCRIPTION).getAsString();
      return new LooseMoneyAction(amount, description);
    } else if ("SkipAction".equals(type)) {
      return getSkipAction(actionObj);
    }
    return null;
  }

  /**
   * Creates a {@link SkipAction} for the board game.
   *
   * @param actionObj the JSON object of the action
   * @return the {@link SkipAction}
   */
  private static SkipAction getSkipAction(JsonObject actionObj) {
    String description = actionObj.get(Constants.DESCRIPTION).getAsString();
    return new SkipAction(description);
  }

}
