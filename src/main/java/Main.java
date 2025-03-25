import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.BoardFileeReader;
import model.Board;
import model.Player;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) {
    BoardFileeReader reader = new BoardFileReaderGson();
    try {
      Board board = reader.readBoard(Path.of("board.json"));
      BoardGame boardGame = new BoardGame(board);
      boardGame.createDice(1);
      boardGame.addPlayer(new Player("Arne", boardGame));
      boardGame.addPlayer(new Player("Ivar", boardGame));
      boardGame.addPlayer(new Player("Majid", boardGame));
      boardGame.addPlayer(new Player("Atle", boardGame));
      boardGame.play();
    } catch (IOException e) {
      System.out.println("Could not read board from file: " + e.getMessage());
    }
  }
}