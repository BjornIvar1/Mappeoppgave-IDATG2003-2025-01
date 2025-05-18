package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;
import model.exception.TileNotFoundException;

/**
 * Represents a snake in the game of snake and ladders.
 *
 * <p>Inherent TileAction's method {@code perform} to allow the user
 * to land on a snake and moves them to another tile based on the
 * {@code destinationTile}.
 * </p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.3
 */
public class SnakeAction implements TileAction {
  private int destinationTile;
  private String description;

  /**
   * Creates a tile destination of where the player will land.
   *
   * @param destinationTile of where the user will land.
   */
  public SnakeAction(int destinationTile, String description) {
    setDestinationTile(destinationTile);
    setDescription(description);
  }

  /**
   * Move the player to the correct tile.
   *
   * @param player The Player who lands on the tile.
   */
  @Override
  public void perform(Player player) throws TileNotFoundException {
    player.moveToTile(destinationTile);
  }

  /**
   * Mutates the destination the player will land on.
   *
   * @param destinationTile that the player will land on.
   * @throws IllegalArgumentException if the destinationTile is lower than 1.
   */
  public void setDestinationTile(int destinationTile) {
    if (destinationTile <= 0) {
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
  public void setDescription(String description) throws IllegalArgumentException {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Accesses the color of the snake.
   *
   * @return the color of the snake.
   */
  public Color getColor() {
    return Color.web("#e5626a");
  }
}
