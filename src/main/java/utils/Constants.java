package utils;

/**
 * This class contains constants used throughout the application.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.5
 */
public final class Constants {
  // Prevent instantiation
  private Constants() {}

  public static final String SNAKES_AND_LADDERS_NORMAL_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLadders/SnakesAndLaddersNormalBoard.json";
  public static final String SNAKES_AND_LADDERS_EASY_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLadders/SnakesAndLaddersEasyBoard.json";
  public static final String SNAKES_AND_LADDERS_HARD_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLadders/SnakesAndLaddersHardBoard.json";
  public static final String BOARD_SAVED_FILEPATH =
      "src/main/resources/board/BoardSaved.json";
  public static final String SNAKES_AND_LADDERS_PLAYER_SAVED_CSV =
      "src/main/resources/players/playersSavedSnakesAndLadders.csv";
  public static final String MONOPOLY_PLAYER_SAVED_CSV =
      "src/main/resources/players/PlayersSaveMonopoly";
  public static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";
  public static final int TILE_SIZE = 60;

  public static final String MONOPOLY_BOARD_FILE_PATH =
      "src/main/resources/board/monopolyBoard.json";

  public static final String COLOR_TILE_EVEN = "#32bff5";
  public static final String COLOR_TILE_ODD = "#bbd962";

  public static final String LABEL_START_GAME_BUTTON = "Start Game";
  public static final String LABEL_ROLL_DICE_BUTTON = "Roll Dice";

  public static final String LABEL_LAST_ROLLED_BUTTON = "Last rolled: ---";
  public static final String ACTION = "action";
  public static final String DESCRIPTION = "description";

  public static final int WINNING_BALANCE = 3000000;

  public static final String MONOPOLY_RULES  = """
      This is a simplified monopoly game for 1-4 players. \
      Everyone gets 2 dice and rolls them. The player that earns $3,000,000 wins.
      There are three different tiles in the game:
      Money tile: The player that lands receives $500,000.
      Tax tile: The player that lands pays $250,000.
      Jail tile: The player that lands on this tile will be skipped in the next turn.
      Good luck and have fun!""";
}
