package utils;

/**
 * This class contains constants used throughout the application.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.4
 */
public final class Constants {
  // Prevent instantiation
  private Constants() {}

  public static final String SNAKES_AND_LADDERS_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLaddersBoard.json";
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
}
