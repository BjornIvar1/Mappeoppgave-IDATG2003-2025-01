package filehandler;

import engine.BoardGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import model.Player;

/**
 * PlayerFileReader class reads the file of player stored in the csv files
 * and adds the players back to the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.2
 * @since 0.0.1
 */
public class PlayerFileReader {

  /**
   * Reads a CSV file and adds players to a given board game.
   *
   * <p>The CSV file should contain player names, separated by commas. Each line
   * represents one player. Only the first column (player name) is used.
   *
   * @param fileName the path to the CSV file
   * @param game the {@code BoardGame} instance to which players will be added
   */
  public void readCsvBuffered(String fileName, BoardGame game) {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.split("\\s*,\\s*");
        if (words.length >= 2) {
          String name = words[0].strip();
          String color = words[1].strip();

          game.addPlayer(new Player(name, game, color));
        } else {
          System.out.println("Skipping invalid line: " + line);
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("File format error");
    } catch (NumberFormatException e) {
      System.out.println("Not an integer: " + e.getMessage());
    }
  }
}