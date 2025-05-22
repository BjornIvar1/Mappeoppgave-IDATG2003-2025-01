package edu.ntnu.idi.bidata.filehandler.board;

import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Tile;
import edu.ntnu.idi.bidata.model.tileactions.TileAction;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Class that writes a {@link Board} to a JSON file by using Gson.
 *
 * <p>Each tile is written with an id, its position and {@code TileAction} if it exists,
 *   and stores it as JSON file.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.4
 * @since 0.0.1
 */
public class BoardFileWriterGson implements BoardFileWriter {

  /**
   * Writes a board into file using Gson.
   *
   * <p>This includes writing the data for the tiles.</p>
   *
   * @param board the board to write
   * @param filePath the path for the file
   * @throws IOException if the file can not be written
   */
  @Override
  public void writeBoard(Board board, Path filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
      writer.write("{\n");
      writer.write("  \"name\": \"" + "Snakes and Ladders" + "\",\n");
      writer.write("  \"description\": \"" + "A simple snakes and ladders board game" + "\",\n");
      writer.write("  \"rows\": " + board.getRows() + ",\n");
      writer.write("  \"columns\": " + board.getColumns() + ",\n");
      writer.write("  \"tiles\": [\n");

      writeTiles(board, writer);

      writer.write("  ]\n");
      writer.write("}");
    }
  }

  /**
   * Writes the tiles of the board to the file.
   *
   * <p>Each tile includes the tile id, x and y coordinate,
   * links it up to the next tile and any associated action.</p>
   *
   * @param board the board to write
   * @param writer the writer to write to the file
   * @throws IOException if the file could not be written
   */
  private static void writeTiles(Board board, BufferedWriter writer) throws IOException {
    Iterator<Tile> iterator = board.getTileIterator();
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
        writeTileAction(writer, tile);
      }


      if (iterator.hasNext()) {
        writer.write("},\n");
      } else {
        writer.write("}\n");
      }
    }
  }

  /**
   * Writes the action of the tile to the file.
   *
   * <p>Each action includes the type, description and destination tile id.</p>
   *
   * @param writer the writer to write to the file
   * @param tile the tile to write
   * @throws IOException if the file could not be written
   */
  private static void writeTileAction(BufferedWriter writer, Tile tile) throws IOException {
    TileAction action = tile.getLandAction();
    writer.write(", " + "\"action\": { ");
    writer.write("\"type\": \"" + action.getClass().getSimpleName() + "\"");
    writer.write(", " + "\"description\": \"" + action.getDescription() + "\"");
    writer.write(", " + "\"destinationTileId\": " + action.getDestinationTile());
    writer.write("}");
  }
}