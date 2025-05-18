package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;

/**
 * Represents a jail action in the game of Monopoly.
 *
 * <p>This class implements the TileAction interface and allows the player
 * to perform actions related to being in jail. The class is responsible to
 * skip a turn of the player that has landed on the tile.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.2
 */
public class JailAction implements TileAction {
  private String description;

  /**
   * Creates a jail action with a specified description and color.
   *
   * @param description the description of the action
   */
  public JailAction(String description) {
    this.description = description;
  }

  /**
   * Performs the jail action on the specified player.
   *
   * <p>This method sets the player's status to in jail.</p>
   *
   * @param player the player to perform the action on
   */
  @Override
  public void perform(Player player) {
    player.setInJail(true);
  }

  /**
   * Returns the description of the jail action.
   *
   * @return the description of the jail action
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the jail action.
   *
   * @param description the new description of the jail action.
   * @throws IllegalArgumentException if the description is null or blank.
   */
  public void setDescription(String description) throws IllegalArgumentException {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Returns the color of the jail action.
   *
   * @return the color of the jail action
   */
  @Override
  public Color getColor() {
    return Color.web("#dfdfdf");
  }

  @Override
  public int getDestinationTile() {
    return 0; // No specific destination tile for jail action
  }
}
