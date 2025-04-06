package filehandler;

import engine.BoardGame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFileReaderAndWriterTest {
  private BoardGame game;
  private PlayerFileReader csvReader;
  private static final String TEST_FILE = "test_players.csv";

  @BeforeEach
  void setUp() {
    game = new BoardGame();
    csvReader = new PlayerFileReader();
  }

  @AfterAll
  static void tearDown() throws IOException {
    Files.deleteIfExists(Path.of(TEST_FILE));
  }

  @Test
  void readAndWritePositiveTest() throws IOException {

    // Create a temporary CSV file.
    List<String> players = List.of(
        "Atle,",
        "Marvin,",
        "Bjorn,"
    );

    //Creates and writes the player to the file.
    Files.write(Path.of(TEST_FILE), players);

    csvReader.readCsvBuffered(TEST_FILE, game);

    // check if there are 3 players saved.
    assertEquals(3, game.getPlayers().size(), "Should have added 3 players");

    // checks if the players exist.
    assertEquals("Atle", game.getPlayers().get(0).getName());
    assertEquals("Marvin", game.getPlayers().get(1).getName());
    assertEquals("Bjorn", game.getPlayers().get(2).getName());
  }

  @Test

  void readAndWriteBufferedNegativeTest() throws IOException {
    //Act by making TEST_FILE an empty file.
    Files.write(Path.of(TEST_FILE), List.of());

    //Read the file.
    csvReader.readCsvBuffered(TEST_FILE, game);

    //There should not be any players in the game.
    assertTrue(game.getPlayers().isEmpty(), "No players found");
  }
}