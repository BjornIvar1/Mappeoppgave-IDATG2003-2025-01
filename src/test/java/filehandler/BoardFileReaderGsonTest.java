package filehandler;

import filehandler.board.BoardFileReaderGson;
import model.entity.Board;
import model.tileactions.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class BoardFileReaderGsonTest {
  private BoardFileReaderGson gsonReader;
  private static final String BOARDJSON = """
    {
      "name": "Snakes and Ladders",
      "columns": 1,
      "rows": 6,
      "tiles": [
        { "id": 1, "x": 0, "y": 0, "nextTile": 2 },
        { "id": 2, "x": 1, "y": 0, "nextTile": 3 },
        { "id": 3, "x": 2, "y": 0, "nextTile": 4, "action": { "type": "LadderAction", "description": "Ladder from 3 to 6", "destinationTileId": 6 }},
        { "id": 4, "x": 3, "y": 0, "nextTile": 5 },
        { "id": 5, "x": 4, "y": 0, "nextTile": 6 },
        { "id": 6, "x": 5, "y": 0 }
      ]
    }
  """;

  @BeforeEach
  void setUp() {
    gsonReader = new BoardFileReaderGson();
  }

  @Test
  void readBoardTest() throws IOException {
    Path tempFile = Files.createTempFile("board-test", ".json");
    Files.writeString(tempFile, BOARDJSON);
    Board board = gsonReader.readBoard(tempFile);

    //Testing the creation of the board
    assertNotNull(board);
    assertEquals(1, board.getColumns(), "The columns should be 1");
    assertEquals(6, board.getRows(), "The rows should be 6");
    assertEquals(6, board.getTiles().size(), "The size should be 6");

    //Testing the tiles
    for (int i = 1; i <= 6; i++) {
      assertEquals(i, board.getTileById(i).getTileId(), "The id should be " + i);
      if (i == 6) {
        assertNull(board.getTileById(i).getNextTile(), "The next tile should be null");
      } else {
        assertEquals(i+1, board.getTileById(i).getNextTile().getTileId(), "The next tile id should be " + (i+1));
      }
    }

    //Testing the actions
    TileAction ladderActionTile3 = board.getTileById(3).getLandAction();
    assertEquals("LadderAction", ladderActionTile3.getClass().getSimpleName(), "The action should be a LadderAction");
    assertEquals("Ladder from 3 to 6", ladderActionTile3.getDescription(), "The description should be 'Ladder from 3 to 6'");
    assertEquals(6, ladderActionTile3.getDestinationTile(), "The destination tile id should be 6");

  }
}