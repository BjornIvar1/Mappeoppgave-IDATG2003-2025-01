/**
 * A entity class for player.
 *
 * <p>This class represents the player in the game.</p>
 *
 * @since 0.0.1
 * @author Arpit Sahoo
 * @version 0.0.1
 */

public class Player {
  private String name;
  //String color

  //constructor
  /**
   * A constructor for the class player.
   *
   * @param name is the name of the player.
   */
  public Player(String name) {
    setName(name);
  }

  //getter and setter
  /**
   * Mutator method for the name.
   *
   * @throws IllegalArgumentException if name is null or isblank
   * @param name
   */
  public void setName(String name) {
    if(name == null || name.isBlank()) {
      throw new NullPointerException("Name cannot be null or blank");
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
}
