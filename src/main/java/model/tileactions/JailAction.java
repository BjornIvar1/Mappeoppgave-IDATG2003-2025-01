package model.tileactions;

import engine.BoardGame;
import javafx.scene.paint.Color;
import model.Player;
import model.exception.NegativeIntegerException;
import model.exception.TileNotFoundException;

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
