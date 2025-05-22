package edu.ntnu.idi.bidata.filehandler;

import edu.ntnu.idi.bidata.filehandler.board.BoardFileWriter;
import edu.ntnu.idi.bidata.filehandler.board.BoardFileWriterGson;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Tile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import edu.ntnu.idi.bidata.model.tileactions.LadderAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardFileWriterGsonTest {
  private BoardFileWriter boardFileWriter;

  @BeforeEach
  void setUp() {
    boardFileWriter = new BoardFileWriterGson();
  }

  @Test
  void writeBoardTest() throws IOException {
    Board board = new Board(2, 2);
    Tile tile1 = new Tile(1, 0, 0);
    Tile tile2 = new Tile(2, 1, 0);
    Tile tile3 = new Tile(3,2,1);
    tile1.setLandAction(new LadderAction(3, "Ladder from 1 to 3"));
    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    Path path = Files.createTempFile("board-writer-test", ".json");
    boardFileWriter.writeBoard(board, path);

    String expected = """
        {
          "name": "Snakes and Ladders",
          "description": "A simple snakes and ladders board game",
          "rows": 2,
          "columns": 2,
          "tiles": [
            {"id": 1, "x": 0, "y": 0, "nextTile": 2, "action": { "type": "LadderAction", "description": "Ladder from 1 to 3", "destinationTileId": 3}},
            {"id": 2, "x": 1, "y": 0, "nextTile": 3},
            {"id": 3, "x": 2, "y": 1}
          ]
        }
        """;

    String actual = Files.readString(path);
    assertEquals(expected.trim(), actual.trim());
  }
}