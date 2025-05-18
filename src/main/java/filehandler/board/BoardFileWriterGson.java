package filehandler.board;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import model.Board;
import model.Player;
import model.Tile;

/**
 * Class that writes a board to a file using Gson.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
 * @since 0.0.1
 */
public class BoardFileWriterGson implements BoardFileWriter {

  /**
   * Writes a board to a file using Gson.
   *
   * @param board the board to write
   * @param filePath the path to the file
   * @throws IOException if the file could not be written
   */
  @Override
  public void writeBoard(Board board, List<Player> playerList, Path filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
      writer.write("{\n");
      writer.write("  \"name\": \"" + "Snakes and Ladders" + "\",\n");
      writer.write("  \"description\": \"" + "A simple snakes and ladders board game" + "\",\n");
      writer.write("  \"rows\": " + board.getRows() + ",\n");
      writer.write("  \"columns\": " + board.getColumns() + ",\n");
      writer.write("  \"tiles\": [\n");

      writeTiles(board, playerList, writer);

      writer.write("  ]\n");
      writer.write("}");
    }
  }

  /**
   * Writes the tiles of the board to the file.
   *
   * @param board the board to write
   * @param writer the writer to write to the file
   * @throws IOException if the file could not be written
   */
  private static void writeTiles(Board board, List<Player> playerList, BufferedWriter writer) throws IOException {
    Iterator<Tile> iterator = board.getTiles().values().iterator();
    while (iterator.hasNext()) {
      Tile tile = iterator.next();
      writer.write("    {");
      writer.write("\"id\": " + tile.getTileId() + ", ");
      writer.write("\"x\": " + tile.getXCoordinate() + ", ");
      writer.write("\"y\": " + tile.getYCoordinate());

      if (tile.getNextTile() != null) {
        writer.write(", " + "\"nextTile\": " + tile.getNextTile().getTileId());
      }

      if (tile.getLandAction() != null) {
        writeLandAction(writer, tile);
      }

      for (int i = 0; i < playerList.size(); i++) {
        Player player = playerList.get(i);
        if (tile.getTileId() == player.getCurrentTile().getTileId()) {
          writer.write(", " + "\"player"+ (i+1) + "\": { ");
          writer.write("\"name\": \"" + player.getName() + "\"");
          writer.write(", " + "\"color\": \"" + player.getColor() + "\"");
          writer.write(", " + "\"balance\": " + player.getBalance());
          writer.write(", " + "\"currentTileId\": " + player.getCurrentTile().getTileId());
          writer.write("}");
        }
      }

      if (iterator.hasNext()) {
        writer.write("},\n");
      } else {
        writer.write("}\n");
      }
    }
  }

  /**
   * Writes the land action of the tile to the file.
   *
   * @param writer the writer to write to the file
   * @param tile the tile to write the land action of
   * @throws IOException if the file could not be written
   */
  private static void writeLandAction(BufferedWriter writer, Tile tile) throws IOException {
    writer.write(", " + "\"action\": { ");
    writer.write("\"type\": \"" + "LadderAction" + "\"");
    writer.write(", " + "\"description\": \"" + tile.getLandAction().getDescription() + "\"");
    writer.write(", " + "\"destinationTileId\": " + tile.getLandAction().getDestinationTile());
    writer.write("}");
  }
}