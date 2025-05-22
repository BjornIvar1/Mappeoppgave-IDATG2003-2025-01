package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.ui.gui.GameLauncher;

/**
 * Main class to launch the game.
 *
 * <p>This class is the entry point of the application.
 * It contains the main method that starts the game. It uses the {@link GameLauncher}
 * class to initialize and start the game. It is responsible for setting up the game
 * environment and launching the GUI.</p>
 *
 */
public class Main {

  /**
   * The main method to launch the game.
   *
   * <p>This method is the entry point of the application.
   * It initializes and starts the game using the {@link GameLauncher} class.</p>
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    GameLauncher.main(args);
  }
}