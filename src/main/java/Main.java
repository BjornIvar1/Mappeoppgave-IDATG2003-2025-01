import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.BoardFileReader;
import filehandler.BoardFileWriter;
import filehandler.BoardFileWriterGson;
import model.Player;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) {
    BoardFileReader reader = new BoardFileReaderGson();
    BoardFileWriter writer = new BoardFileWriterGson();
    try {
      BoardGame boardGame = new BoardGame();
      boardGame.createBoard(reader.readBoard(Path.of("src/main/resources/board.json")));
      boardGame.createDice(1);
      boardGame.addPlayer(new Player("Arne", boardGame));
      boardGame.addPlayer(new Player("Ivar", boardGame));
      boardGame.addPlayer(new Player("Majid", boardGame));
      boardGame.addPlayer(new Player("Atle", boardGame));
      boardGame.play();
      BoardGame boardGame1 = new BoardGame();
      writer.writeBoard(boardGame.getBoard(), Path.of("src/main/resources/boardWritten.json"));
      boardGame1.createBoard(reader.readBoard(Path.of("src/main/resources/boardWritten.json")));
      boardGame1.addPlayer(new Player("Majid", boardGame1));
      boardGame1.addPlayer(new Player("Atle", boardGame1));
      boardGame1.createDice(1);
      System.out.println("NEW GAME");
      boardGame1.play();
    } catch (IOException e) {
      System.out.println("Could not read board from file: " + e.getMessage());
    }
  }
}