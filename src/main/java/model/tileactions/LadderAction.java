package model.tileactions;

import javafx.scene.paint.Color;
import model.entity.Player;
import utils.exception.IntegerException;
import utils.exception.NullOrBlankException;
import utils.exception.StringException;

/**
 * Represents the ladders in the game of snake and ladders.
 *
 * <p>Inherent TileAction's method {@code perform} to allow the user
 * to land on a ladder and moves them to another tile based on the
 * {@code destinationTile}.
 * </p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.2.2
 */
public class LadderAction implements TileAction {
  private int destinationTile;
  private String description;

  /**
   * Constructs a {@link LadderAction} object with the specified destination tile and description.
   *
   * @param destinationTile of where the user will land.
   * @param description of what happens when the player lands on a ladder.
   */
  public LadderAction(int destinationTile, String description) {
    setDestinationTile(destinationTile);
    setDescription(description);
  }

  /**
   * Move the player to the correct tile.
   *
   * @param player The Player who lands on the tile.
   */
  @Override
  public void perform(Player player) throws NullOrBlankException {
    player.moveToTile(destinationTile);
  }

  /**
   * Mutates the destination the player will land on.
   *
   * @param destinationTile that the player will land on.
   * @throws IntegerException if the destinationTile is lower than 1.
   */
  public void setDestinationTile(int destinationTile) throws IntegerException {
    if (destinationTile <= 0) {
      throw new IntegerException("Destination tile cannot be lower than 1");
    }
    this.destinationTile = destinationTile;
  }

  /**
   * Accesses the tile the player will be moved to.
   *
   * @return destinationTile of where the player will be moved.
   */
  public int getDestinationTile() {
    return destinationTile;
  }

  /**
   * Accesses the description the player will get when landing
   * on a ladder.
   *
   * @return description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the ladder action.
   *
   * @param description of what happens when the player lands on a ladder.
   * @throws IllegalArgumentException if the description is null og empty.
   */
  public void setDescription(String description) throws StringException {
    if (description == null || description.isEmpty()) {
      throw new StringException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Accesses the color of the ladder.
   *
   * @return the color of the ladder.
   */
  public Color getColor() {
    return Color.web("#f5f04b");
  }
}
