package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;
import model.exception.NegativeIntegerException;
import model.exception.TileNotFoundException;

/**
 * Represents a jail action in the game of Monopoly.
 *
 * <p>This class implements the TileAction interface and allows the player
 * to perform actions related to being in jail. The class is responsible to
 * skip a turn of the player that has landed on the tile.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.0.1
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

  @Override
  public void perform(Player player) throws NegativeIntegerException, TileNotFoundException {
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
