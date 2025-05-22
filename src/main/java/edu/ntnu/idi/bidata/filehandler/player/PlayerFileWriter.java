package edu.ntnu.idi.bidata.filehandler.player;

import edu.ntnu.idi.bidata.model.entity.Player;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

/**
 * PlayerFileWriter writes and saves all the players that are stored in the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.1.2
 * @since 0.0.1
 */
public class PlayerFileWriter {
  private PlayerFileWriter() {}

  /**
   * Write a CSV file from a list.
   *
   * <p>Each player is written in the following format:</p>
   * <pre>playerName, playerColor, playerBalance, currentTileID</pre>
   *
   * <p>If a players name contains commas, they are removed to preserve the CSV format.</p>
   *
   * @param playersToSave The list of players.
   * @param fileName The file name.
   */
  public static void writeToCsv(List<Player> playersToSave, String fileName) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName))) {
      for (Player player : playersToSave) {
        String playerName = player.getName().replace(",", "");
        int currentTileId = player.getCurrentTile() != null
            ? player.getCurrentTile().getTileId() : player.getCurrentTileId();
        writer.write(playerName + ","
            + player.getColor() + ","
            + player.getBalance() + ","
            + currentTileId + "\n");
      }
    } catch (IOException e) {
      Logger.getLogger("Error writing to file: " + e.getMessage());
    }
  }
}
