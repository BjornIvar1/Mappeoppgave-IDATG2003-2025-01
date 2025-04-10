package model.tileactions;

import model.Player;

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
 * @version 0.0.1
 */
public class LadderAction implements TileAction {
  private int destinationTile;
  private String description;

  /**
   * Creates a tile destination of where the player will land.
   *
   * @param destinationTile of where the user will land.
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
  public void perform(Player player) {
    player.moveToTile(destinationTile);
  }

  /**
   * Mutates the destination the player will land on.
   *
   * @param destinationTile that the player will land on.
   * @throws IllegalArgumentException if the destinationTile is lower than 1.
   */
  public void setDestinationTile(int destinationTile) {
    if (destinationTile <= 0){
      throw new IllegalArgumentException("Destination tile cannot be lower than 1");
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
   * Mutates the description.
   *
   * @param description of what happens when the player lands on a ladder.
   * @throws IllegalArgumentException if the description is null og empty.
   */
  public void setDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }
}
