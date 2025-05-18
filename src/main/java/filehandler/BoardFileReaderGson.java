package filehandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import model.tileactions.*;
import model.Board;
import model.Tile;
import utils.Constants;

/**
 * Class that reads a board from a file using Gson.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.5.0
 * @since 0.0.1
 */
public class BoardFileReaderGson implements BoardFileReader {

  /**
   * Reads a board from a file using Gson.
   *
   * @param filePath the path to the file
   * @return the board that was read from the file
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
   * Creates a map of tiles from the JSON array and adds the tiles to {@link HashMap} tileMap.
   * Returns the map of tiles.
   *
   * @param tilesArray the JSON array of tiles
   * @param board the board to add the tiles to
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
   * Links the next tile to the current tile.
   *
   * @param tilesArray the JSON array of tiles
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
   * @param tilesArray the JSON array of tiles
   * @param tileMap the map of tiles
   */
  private static void createTileAction(JsonArray tilesArray, Map<Integer, Tile> tileMap, JsonObject rootObj) {
    for (JsonElement element : tilesArray) {
      JsonObject tileObj = element.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      Tile currentTile = tileMap.get(id);
      if (tileObj.has(Constants.ACTION) && "Snakes and Ladders".equals(rootObj.get("name").getAsString())) {
        JsonObject actionObj = tileObj.getAsJsonObject(Constants.ACTION);
        TileAction tileAction = createTileActionSnakesAndLadders(actionObj);
        currentTile.setLandAction(tileAction);
      } else if (tileObj.has(Constants.ACTION) && "Monopoly".equals(rootObj.get("name").getAsString())) {
        JsonObject actionObj = tileObj.getAsJsonObject(Constants.ACTION);
        TileAction tileAction = createTileMonopolyAction(actionObj);
        currentTile.setLandAction(tileAction);
      } else {
        currentTile.setLandAction(null);
      }
    }
  }

  /**
   * Creates the tile Action for Snakes and Ladders.
   * Returns the tile action or null, if the tile do not have an action.
   *
   * @param actionObj the JSON object of the action
   * @return the tile action or null
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
    }
    return null;
  }

  /**
   * Creates the tile Action for Monopoly.
   * Returns the tile action or null, if the tile do not have an action.
   *
   * @param actionObj the JSON object of the action
   * @return the tile action or null
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
    } else if ("JailAction".equals(type)) {
      String description = actionObj.get(Constants.DESCRIPTION).getAsString();
      return new JailAction(description);
    }
    return null;
  }

}
