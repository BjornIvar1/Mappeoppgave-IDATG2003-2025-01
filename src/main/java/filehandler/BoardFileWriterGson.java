package filehandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
      //TODO Name and description
      writer.write("\"rows\": " + board.getRows() + ",\n");
      writer.write("\"columns\": " + board.getColumns() + ",\n");
      writer.write("\"tiles\": \n");
      writer.write("[\n");
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
          writer.write(", " + "\"action\": { ");
          writer.write("\"type\": \"" + "LadderAction" + "\"");
          writer.write(", " + "\"description\": \"" + tile.getLandAction().getDescription() + "\"");
          writer.write(", " + "\"destinationTileId\": " + tile.getLandAction().getDestinationTile());
          writer.write("}");
        }
        writer.write("}");

        if (iterator.hasNext()) {
          writer.write(",\n");
        } else {
          writer.write("\n");
        }
      }
      writer.write("  ]\n");
      writer.write("}");
    }
  }
}