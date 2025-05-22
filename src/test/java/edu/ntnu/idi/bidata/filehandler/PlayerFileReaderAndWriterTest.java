package edu.ntnu.idi.bidata.filehandler;

import edu.ntnu.idi.bidata.filehandler.player.PlayerFileReader;
import edu.ntnu.idi.bidata.filehandler.player.PlayerFileWriter;
import edu.ntnu.idi.bidata.model.engine.BoardGame;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.bidata.model.entity.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  void readAndWritePositiveTest() {
    Player player1 = new Player("Atle", "BLACK", game, 0, 1);
    Player player2 = new Player("Marvin", "BLACK", game, 0, 2);
    Player player3 = new Player("Bjorn", "BLACK", game, 0, 3);
    Player player4 = new Player("Atle", "BLACK", game, 0, 1);

    // Create a temporary CSV file.
    List<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);

    //Creates and writes the player to the file.
    PlayerFileWriter.writeToCsv(players, Path.of(TEST_FILE).toString());

    csvReader.readCsvBuffered(TEST_FILE, game);

    // check if there are 3 players saved.
    assertEquals(4, game.getPlayers().size(), "Should have added 3 players");

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