package edu.ntnu.idi.bidata.filehandler.player;

import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.entity.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is responsible for reading player data from a CSV file and adding them to the game.
 *
 * <p>The class reads the player data stored in a CSV file,
 * and creates {@link Player} object, and adds them to a {@link BoardGame}</p>
 *
 * <p>CSV format per line:</p>
 * <pre>playerName, playerColor, playerBalance, currentTileID</pre>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.6
 * @since 0.0.1
 */
public class PlayerFileReader {

  /**
   * Reads a CSV file and adds players to the given {@link BoardGame}.
   *
   * <p>The CSV file should contain player names, separated by commas. Each line
   * represents one player. Only the first column (player name) is used.
   *
   * @param fileName the path to the CSV file
   * @param game the {@link  BoardGame} instance to which players will be added
   */
  public void readCsvBuffered(String fileName, BoardGame game) {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.split("\\s*,\\s*");
        if (words.length >= 4) {
          String name = words[0].strip();
          String color = words[1].strip();
          int balance = Integer.parseInt(words[2].strip());
          int currentTileId = Integer.parseInt(words[3].strip());
          game.addPlayer(new Player(name, color, game, balance, currentTileId));
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