package filehandler;

import model.Board;
import model.Tile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

public class BoardFileWriterGson implements BoardFileWriter{
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

  private static void writeTiles(Board board, BufferedWriter writer) throws IOException {
    Iterator<Tile> iterator = board.getTiles().values().iterator();
    while (iterator.hasNext()) {
      Tile tile = iterator.next();
      writer.write("    {");
      writer.write("\"id\": " + tile.getTileId() + ", ");
      writer.write("\"x\": " + tile.getXcoordinate() + ", ");
      writer.write("\"y\": " + tile.getYcoordinate());

      if (tile.getNextTile() != null) {
        writer.write(", " + "\"nextTile\": " + tile.getNextTile().getTileId());
      }

      if (tile.getLandAction() != null) {
        writeLandAction(writer, tile);
      }
      writer.write("}");

      if (iterator.hasNext()) {
        writer.write(",\n");
      } else {
        writer.write("\n");
      }
    }
  }

  private static void writeLandAction(BufferedWriter writer, Tile tile) throws IOException {
    writer.write(", " + "\"action\": { ");
    writer.write("\"type\": \"" + "LadderAction" + "\"");
    writer.write(", " + "\"description\": \"" + tile.getLandAction().getDescription() + "\"");
    writer.write(", " + "\"destinationTileId\": " + tile.getLandAction().getDestinationTile());
    writer.write("}");
  }
}