package edu.ntnu.idi.bidata.filehandler;

import edu.ntnu.idi.bidata.filehandler.board.BoardFileReaderGson;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.tileactions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardFileReaderGsonTest {
  private BoardFileReaderGson gsonReader;
  private static final String BOARDJSONSNAKESANDLADDERS = """
    {
      "name": "Snakes and Ladders",
      "columns": 1,
      "rows": 9,
      "tiles": [
        { "id": 1, "x": 0, "y": 0, "nextTile": 2 },
        { "id": 2, "x": 1, "y": 0, "nextTile": 3 },
        { "id": 3, "x": 2, "y": 0, "nextTile": 4, "action": { "type": "LadderAction", "description": "Ladder from 3 to 6", "destinationTileId": 6 }},
        { "id": 4, "x": 3, "y": 0, "nextTile": 5, "action": { "type": "SnakeAction", "description": "Ladder from 3 to 6", "destinationTileId": 6 }},
        { "id": 5, "x": 4, "y": 0, "nextTile": 6, "action": {"type": "SkipAction", "description": "SKIP: your turn is skipped!"}},
        { "id": 6, "x": 5, "y": 0, "nextTile": 7},
        { "id": 7, "x": 6, "y": 0, "nextTile": 8, "action": { "type": "BankAction", "description": "You won 100000", "amount": 100000 } },
        { "id": 8, "x": 7, "y": 0, "nextTile": 9, "action": { "type": "LooseMoneyAction", "description": "You lost 100000", "amount": 100000 } },
        { "id": 9, "x": 8, "y": 0, "nextTile": 10 },
        { "id": 10, "x": 9, "y": 0 }
      ]
    }
  """;
  private static final String BOARDJSONMONOPLY = """
    {
      "name": "Monopoly",
      "columns": 1,
      "rows": 9,
      "tiles": [
        { "id": 1, "x": 0, "y": 0, "nextTile": 2, "action": { "type": "BankAction", "description": "You won 100000", "amount": 100000 } },
        { "id": 2, "x": 1, "y": 0, "nextTile": 3, "action": { "type": "LooseMoneyAction", "description": "You lost 100000", "amount": 100000 } },
        { "id": 3, "x": 2, "y": 0, "nextTile": 4, "action": {"type": "SkipAction", "description": "SKIP: your turn is skipped!"}},
        { "id": 4, "x": 3, "y": 0 }
      ]
    }
  """;

  @BeforeEach
  void setUp() {
    gsonReader = new BoardFileReaderGson();
  }

  @Test
  void readBoardSnakesActionsTest() throws IOException {
    Path tempFile = Files.createTempFile("board-test", ".json");
    Files.writeString(tempFile, BOARDJSONSNAKESANDLADDERS);
    Board board = gsonReader.readBoard(tempFile);

    //Testing the creation of the board
    assertNotNull(board);
    assertEquals(1, board.getColumns(), "The columns should be 1");
    assertEquals(9, board.getRows(), "The rows should be 9");
    assertEquals(10, board.getTiles().size(), "The size should be 10");

    //Testing the tiles
    for (int i = 1; i <= 10; i++) {
      assertEquals(i, board.getTileById(i).getTileId(), "The id should be " + i);
      if (i == 10) {
        assertNull(board.getTileById(i).getNextTile(), "The next tile should be null");
      } else {
        assertEquals(i+1, board.getTileById(i).getNextTile().getTileId(), "The next tile id should be " + (i+1));
      }
    }

    //Testing the actions
    //LadderAction
    TileAction ladderActionTile3 = board.getTileById(3).getLandAction();
    assertEquals("LadderAction", ladderActionTile3.getClass().getSimpleName(), "The action should be a LadderAction");
    assertEquals("Ladder from 3 to 6", ladderActionTile3.getDescription(), "The description should be 'Ladder from 3 to 6'");
    assertEquals(6, ladderActionTile3.getDestinationTile(), "The destination tile id should be 6");

    // SnakeAction
    TileAction snake = board.getTileById(4).getLandAction();
    assertInstanceOf(SnakeAction.class, snake);
    assertEquals("Ladder from 3 to 6", snake.getDescription());
    assertEquals(6, snake.getDestinationTile());

    // SkipAction
    TileAction skip = board.getTileById(5).getLandAction();
    assertInstanceOf(SkipAction.class, skip);
    assertEquals("SKIP: your turn is skipped!", skip.getDescription());
  }

  @Test
  void readBoardTestMonopolyActions() throws IOException {
    Path tempFile1 = Files.createTempFile("board-test-2", ".json");
    Files.writeString(tempFile1, BOARDJSONMONOPLY);
    Board board = gsonReader.readBoard(tempFile1);

    // BankAction
    TileAction bank = board.getTileById(1).getLandAction();
    assertInstanceOf(BankAction.class, bank);
    assertEquals("You won 100000", bank.getDescription());
    assertEquals(100000, ((BankAction) bank).getMoney());

    // LooseMoneyAction
    TileAction lose = board.getTileById(2).getLandAction();
    assertInstanceOf(LooseMoneyAction.class, lose);
    assertEquals("You lost 100000", lose.getDescription());
    assertEquals(100000, ((LooseMoneyAction) lose).getLooseMoney());

    // SkipAction
    TileAction skip = board.getTileById(3).getLandAction();
    assertInstanceOf(SkipAction.class, skip);
    assertEquals("SKIP: your turn is skipped!", skip.getDescription());

    //Null Check
    TileAction noTileACtion = board.getTileById(4).getLandAction();
    assertNull(noTileACtion);
  }
}