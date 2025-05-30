package edu.ntnu.idi.bidata.utils;

/**
 * This class contains constants used throughout the application.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.4.1
 */
public final class Constants {
  // Prevent instantiation
  private Constants() {}

  /**
   * The file path to the Snakes and Ladders easy board JSON file.
   */
  public static final String SNAKES_AND_LADDERS_EASY_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLadders/SnakesAndLaddersEasyBoard.json";

  /**
   * The file path to the Snakes and Ladders normal board JSON file.
   */
  public static final String SNAKES_AND_LADDERS_NORMAL_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLadders/SnakesAndLaddersNormalBoard.json";

  /**
   * The file path to the Snakes and Ladders hard board JSON file.
   */
  public static final String SNAKES_AND_LADDERS_HARD_BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLadders/SnakesAndLaddersHardBoard.json";

  /**
   * The file path to the saved board JSON file.
   */
  public static final String BOARD_SAVED_FILEPATH =
      "src/main/resources/board/BoardSaved.json";

  /**
   * The file path to the saved Snakes and Ladders player in a CSV file.
   */
  public static final String SNAKES_AND_LADDERS_PLAYER_SAVED_CSV =
      "src/main/resources/players/playersSavedSnakesAndLadders.csv";

  /**
   * The file path to the saved Monopoly player in a CSV file.
   */
  public static final String MONOPOLY_PLAYER_SAVED_CSV =
      "src/main/resources/players/PlayersSaveMonopoly";

  /**
   * The file path to the players in a game in a CSV file.
   */
  public static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";
  public static final int TILE_SIZE = 60;

  /**
   * The file path to the Monopoly board JSON file.
   */
  public static final String MONOPOLY_BOARD_FILE_PATH =
      "src/main/resources/board/monopoly/monopolyBoard.json";

  /**
   * Color for even tiles in the game board.
   */
  public static final String COLOR_TILE_EVEN = "#32bff5";

  /**
   * Color for odd tiles in the game board.
   */
  public static final String COLOR_TILE_ODD = "#bbd962";

  /**
   * Label for the start game button.
   */
  public static final String LABEL_RESTART_GAME_BUTTON = "Restart Game";

  /**
   * Label for the save game button.
   */
  public static final String LABEL_SAVE_GAME_BUTTON = "Save Game";

  /**
   * Label for the roll dice button.
   */
  public static final String LABEL_ROLL_DICE_BUTTON = "Roll Dice";

  /**
   * Label for last rolled button.
   */
  public static final String LABEL_LAST_ROLLED_BUTTON = "Last rolled:";

  /**
   * Label for the choosing a game board.
   */
  public static final String LABEL_CHOOSE_GAME_BOARD = "Choose Game Board";

  /**
   * Const for the word Action.
   */
  public static final String ACTION = "action";

  /**
   * Const for the word description.
   */
  public static final String DESCRIPTION = "description";

  /**
   * Const for the winning condition.
   */
  public static final int WINNING_BALANCE = 3000000;

  /**
   * Information to the user on what they have to do before starting the game.
   */
  public static final String HELP_INFORMATION = """
  1. Choose the amount of players that will play the game.
  2. When finished click the start game button.
  3. If you choose to continue a previous game, press the "Load Last Saved Game" button.
  Notice: In Snakes and ladders, you can choose between three different boards.""";

  /**
   * Const for the word info.
   */
  public static final String INFO = "info";

  /**
   * Const for the word information.
   */
  public static final String INFORMATION = "Information";

  /**
   * Const for game loaded success message.
   */
  public static final String GAME_LOAD_SUCCESS_MESSAGE =
      "Game loaded successfully!";

  /**
   * Const for game created success message.
   */
  public static final String GAME_CREATED_SUCCESS_MESSAGE =
      "Game created successfully!";

  /**
   * Const for game saved success message.
   */
  public static final String GAME_SAVED_SUCCESS_MESSAGE =
      "Game saved successfully!";

  /**
   * Constant for the color of the text in the {@code ButtonFactory} class.
   */
  public static final String GET_COLOR_WHITE = "-fx-text-fill: white; -fx-font-size: 16px;";

  /**
   * Const for the word players.
   */
  public static final String GAME_RULES = "Rules";

  /**
   * Const for the word back.
   */
  public static final String BACK = "Back";

  /**
   * Const for the word for Monopoly rules.
   */
  public static final String GAME_MONOPOLY_HEADER = "Monopoly rules";

  /**
   * Game rules and information for Monopoly.
   */
  public static final String MONOPOLY_RULES  = """
      This is a simplified monopoly game for 1-4 players. \
      Everyone gets 2 dice and rolls them. The player that earns $3,000,000 wins.
      There are three different tiles in the game:
      Money tile: The player that lands receives $500,000.
      Tax tile: The player that lands pays $250,000.
      Jail tile: The player that lands on this tile will be skipped in the next turn.
      Good luck and have fun!""";


  /**
   * Const for the word for Snakes and Ladders rules.
   */
  public static final String GAME_SNAKES_AND_LADDERS_HEADER = "Snakes and Ladders rules";

  /**
   * Game rules and information for Snakes and Ladders.
   */
  public static final String SNAKES_AND_LADDERS_RULES = """
      This is a snakes and ladders game for 1-4 players. \
      Everyone gets 2 dice and rolls them. The player that reaches the end first wins.
      Good luck and have fun!""";

  /**
   * The file path to the image of the dice.
   *
   * @param value the value of the die
   * @return the file path to the image of the die
   */
  public static String getImageOfDice(int value) {
    return "/image/die/DieSide" + value + ".png";
  }

  /**
   * The file path to the image.
   *
   * @param imageName the name of the image
   * @return the file path to the image
   */
  public static String getImage(String imageName) {
    return "/image/" + imageName;
  }

  /**
   * The file path to the image of the dice.
   */
  public static final String DICE_ONE_IMAGE_FILE_PATH =
      "die/DieSide1.png";

  /**
   * The file path to the image of player in jail.
   */
  public static final String PLAYER_IN_JAIL_FILE_PATH =
      "skippedturn/playerInJail.jpg";

  /**
   * The file path to the image of player skipped.
   */
  public static final String PLAYER_SKIPPED_FILE_PATH =
      "skippedturn/emojiLaughing.jpg";

  /**
   * Style for control panel.
   */
  public static final String CONTROL_PANEL_STYLE =
      "-fx-background-color: #dce4f2; -fx-border-color: #ccc; -fx-border-width: 1px 0 0 0;";

  /**
   * Style for the game information box.
   */
  public static final String GAME_INFO_BOX_STYLE =
      "-fx-background-color: #f2f2f2; "
          + "-fx-padding: 10;"
          + "-fx-border-color: #ddd; "
          + "-fx-border-radius: 8; "
          + "-fx-background-radius: 8;";

  /**
   * Style for the player information box.
   */
  public static final String PLAYER_INFO_BOX_STYLE =
      "-fx-font-size: 13px; -fx-text-fill: #333;";

  /**
   * Style for the roll dice button.
   */
  public static final String ROLL_DICE_RESET_GAME_BUTTON_STYLE =
      "-fx-background-color: #4CAF50; "
          + "-fx-text-fill: white; "
          + "-fx-font-weight: bold; "
          + "-fx-padding: 8 16; "
          + "-fx-background-radius: 8; "
          + "-fx-cursor: hand;";

  /**
   * Style for the save game button.
   */
  public static final String SAVE_GAME_BUTTON_STYLE =
      "-fx-background-color: #FFD54F; "
          + "-fx-text-fill: white; "
          + "-fx-font-weight: bold; "
          + "-fx-padding: 8 16; "
          + "-fx-background-radius: 8; "
          + "-fx-cursor: hand;";
}
