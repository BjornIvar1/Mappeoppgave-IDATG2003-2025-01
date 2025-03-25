package filehandler;

import java.io.IOException;
import java.nio.file.Path;
import model.Board;

/**
 * An interface for reading a board from a file.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public interface BoardFileeReader {
  /**
   * Reads a board from a file.
   *
   * @param path the path to the file
   * @return the board read from the file
   * @throws IOException if an I/O error occurs
   */
  Board readBoard(Path path) throws IOException;
}
