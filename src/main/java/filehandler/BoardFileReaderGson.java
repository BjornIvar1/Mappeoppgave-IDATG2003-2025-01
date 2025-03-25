package filehandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Board;
import model.LadderAction;
import model.Tile;
import model.TileAction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that reads a board from a file using Gson.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public class BoardFileReaderGson implements BoardFileeReader {
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
      for (JsonElement element : tilesArray) {
        JsonObject tileObj = element.getAsJsonObject();
        int id = tileObj.get("id").getAsInt();
        Tile currentTile = tileMap.get(id);

        if (tileObj.has("nextTile")) {
          int nextTileId = tileObj.get("nextTile").getAsInt();
          Tile nextTile = tileMap.get(nextTileId);
          currentTile.setNextTile(nextTile);
        }

        if (tileObj.has("action")) {
          JsonObject actionObj = tileObj.getAsJsonObject("action");
          String description = actionObj.get("description").getAsString();

          if ("LadderAction".equals(description)) {
            int destinationTileId = actionObj.get("destinationTileId").getAsInt();
            TileAction action = new LadderAction(destinationTileId, "s");
            currentTile.setLandAction(action);

          }
        }
      }
      return board;
    }
  }
}
