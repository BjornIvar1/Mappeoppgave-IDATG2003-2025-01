package model.tileactions;

import javafx.scene.paint.Color;
import model.entity.Player;
import model.entity.Tile;
import utils.exception.NullOrBlankException;

/**
 * Represent an action when a player lands on a {@link Tile}.
 *
 * <p>Implemented by classes which defines what happens
 * when a player lands on a specific tile.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.3
 * @since 0.0.1
 */
public interface TileAction {
  /**
   * Perform an Action when a player lands on a tile.
   *
   * @param player The model.entity.Player who lands on the tile
   */
  void perform(Player player) throws NullOrBlankException;

  /**
   * Accesses a description of what happens when the player lands on a tile.
   *
   * @return a description.
   */
  String getDescription();

  /**
   * Accesses the tile the player will be moved to.
   *
   * @return destinationTile of where the player will be moved.
   */
  int getDestinationTile();

  /**
   * Accesses the color of the tile.
   *
   * @return color of the tile.
   */
  Color getColor();
}
