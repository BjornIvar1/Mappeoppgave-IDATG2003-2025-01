package filehandler;

import model.Board;

import java.io.IOException;
import java.nio.file.Path;

public interface BoardFileWriter {
  void writeBoard(Board board, Path filePath) throws IOException;
}
