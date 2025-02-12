/**
 * A entity class for player.
 *
 * <p>This class represents the player in the game.</p>
 *
 * @since 0.0.1
 * @author Arpit Sahoo
 * @version 0.1.0
 */

public class Player {
  private String name;
  private Tile currrenTile;
  //String color

  //constructor
  /**
   * A constructor for the class player.
   *
   * @param name is the name of the player.
   */
  Player(String name) {
    setName(name);
  }

  //getter and setter
  /**
   * Mutator method for the name.
   *
   * @throws IllegalArgumentException if name is null or isBlank.
   * @param name of the player.
   */
  public void setName(String name) {
    if(name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    this.name = name;
  }

  /**
   * Accessor method for player name.
   *
   * @return the player name
   */
  public String getName() {
    return name;
  }

  /**
   * Moves the player on the board,
   * by using a loop to move the player onto the next tile.
   *
   * @param steps The amount of steps to the player.
   */
  public void move(int steps) {
    for (int i = 0; i < steps ; i++) {
      if (currrenTile.getNextTile() != null) {
        currrenTile = currrenTile.getNextTile();
      }
    }
    currrenTile.landPlayer(this);
  }

  /**
   * The current the player is on.
   *
   * @param tile the player is on.
   */
  public void placeOnTile(Tile tile) {
    currrenTile = tile;
  }

}
