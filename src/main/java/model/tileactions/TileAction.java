package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;

/**
 * Represent an action when a player lands on a model.Tile.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public interface TileAction {
  /**
   * Perform an Action when a player lands on a tile.
   *
   * @param player The model.Player who lands on the tile
   */
  void perform(Player player);

  String getDescription();

  int getDestinationTile();

  Color getColor();
}
