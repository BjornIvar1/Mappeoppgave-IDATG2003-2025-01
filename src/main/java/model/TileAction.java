package model;

/**
 * Represent an action when a player lands on a model.Tile.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.1
 * @since 0.0.1
 */
public interface TileAction {
  /**
   * Perform an Action when a player lands on a tile.
   *
   * @param player The model.Player who lands on the tile
   */
  void perform(Player player);

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

}
