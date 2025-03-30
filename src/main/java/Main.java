import engine.BoardGame;
import filehandler.*;
import model.Player;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) {
    BoardFileReader reader = new BoardFileReaderGson();
    BoardFileWriter writer = new BoardFileWriterGson();
    PlayerFileReader playerFileReader = new PlayerFileReader();
    try {
      BoardGame boardGame = new BoardGame();
      boardGame.createBoard(reader.readBoard(Path.of("src/main/resources/board/board.json")));
      boardGame.createDice(1);
      boardGame.addPlayer(new Player("Arne", boardGame));
      boardGame.addPlayer(new Player("Ivar", boardGame));
      boardGame.addPlayer(new Player("Majid", boardGame));
      boardGame.addPlayer(new Player("Atle", boardGame));
      boardGame.play();
      BoardGame boardGame1 = new BoardGame();
      writer.writeBoard(boardGame.getBoard(), Path.of("src/main/resources/board/boardWritten.json"));
      boardGame1.createBoard(reader.readBoard(Path.of("src/main/resources/board/boardWritten.json")));
      playerFileReader.readCsvBuffered("src/main/resources/players/playersInGameFile.csv", boardGame1);
      boardGame1.createDice(1);
      System.out.println("NEW GAME");
      boardGame1.play();
    } catch (IOException e) {
      System.out.println("Could not read board from file: " + e.getMessage());
    }
  }
}