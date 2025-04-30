package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;
import model.exception.NegativeIntegerException;
import model.exception.TileNotFoundException;

/**
 * Represent an action when a player lands on a model.Tile.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.2
 * @since 0.0.1
 */
public interface TileAction {
  /**
   * Perform an Action when a player lands on a tile.
   *
   * @param player The model.Player who lands on the tile
   */
  void perform(Player player) throws NegativeIntegerException, TileNotFoundException;

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
