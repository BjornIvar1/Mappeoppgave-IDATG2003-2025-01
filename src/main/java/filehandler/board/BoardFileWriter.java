package filehandler.board;

import java.io.IOException;
import java.nio.file.Path;
import model.entity.Board;

/**
 * An interface for writing a {@link Board} object to a file.
 *
 * <p>This interface defines a method for writing a board to a JSON file.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public interface BoardFileWriter {
  /**
   * Writes a board to a file.
   *
   * @param board the board to write
   * @param filePath the path to the file
   * @throws IOException if an I/O error occurs
   */
  void writeBoard(Board board, Path filePath) throws IOException;
}
