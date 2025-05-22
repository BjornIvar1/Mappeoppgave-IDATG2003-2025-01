package edu.ntnu.idi.bidata.model.tileactions;

import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.utils.exception.StringException;
import javafx.scene.paint.Color;

/**
 * Represents a skip action in the game of Monopoly.
 *
 * <p>This class implements the {@link TileAction} interface and allows the player
 * to perform actions related to being in skip. The class is responsible to
 * skip a turn of the player that has landed on the tile.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.4
 */
public class SkipAction implements TileAction {
  private String description;

  /**
   * Creates a skip action with a specified description.
   *
   * @param description the description of the action
   */
  public SkipAction(String description) {
    this.description = description;
  }

  /**
   * Performs the skip action on the specified player.
   *
   * <p>This method sets the player's status to in skip.</p>
   *
   * @param player that will be skipped.
   */
  @Override
  public void perform(Player player) {
    player.setInSkipped(true);
  }

  /**
   * Returns the description of the skip action.
   *
   * @return the description of the skip action
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the {@code SkipAction}.
   *
   * @param description the new description of the skip action.
   * @throws StringException if the description is null or blank.
   */
  public void setDescription(String description) throws StringException {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Returns the color of the {@code SkipAction}.
   *
   * @return the color of the {@code SkipAction}.
   */
  @Override
  public Color getColor() {
    return Color.web("#dfdfdf");
  }

  @Override
  public int getDestinationTile() {
    return 0; // No specific destination tile for skip action
  }
}
