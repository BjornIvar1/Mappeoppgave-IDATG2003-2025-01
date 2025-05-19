package filehandler.board;

import model.Board;
import model.Player;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface BoardFileWriter {
  void writeBoard(Board board, Path filePath) throws IOException;
}
