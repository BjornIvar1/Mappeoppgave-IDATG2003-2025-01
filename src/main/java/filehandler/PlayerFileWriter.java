package filehandler;

import model.Player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * PlayerFileWriter writes and saves all the players that are stored in the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.2
 * @since 0.0.1
 */
public class PlayerFileWriter {

  private PlayerFileWriter () {}

  /**
   * Write a CSV file from a list.
   *
   * @param playersToSave The list of players.
   * @param fileName The file name.
   */
  public static void writeToCSV(List<Player> playersToSave, String fileName) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName))) {
      for (Player player : playersToSave) {
        writer.write(player.getName() + "," + "\n");
      }
    } catch (IOException e) {
      System.out.println("Error writing to file: " + fileName);
    }
  }
}