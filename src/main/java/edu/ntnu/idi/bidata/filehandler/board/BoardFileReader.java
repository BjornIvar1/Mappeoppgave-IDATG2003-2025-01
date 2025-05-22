package edu.ntnu.idi.bidata.filehandler.board;

import edu.ntnu.idi.bidata.model.entity.Board;
import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface for reading a board from a file.
 *
 * <p>This interface defines a method for reading a board from a file.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public interface BoardFileReader {
  /**
   * Reads a board from a file path.
   *
   * @param path the path to the file
   * @return the board read from the file
   * @throws IOException if an I/O error occurs
   */
  Board readBoard(Path path) throws IOException;
}
