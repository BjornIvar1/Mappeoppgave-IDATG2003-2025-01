package model;

/**
 * Represent an action when a player lands on a model.Tile.
 *
 * @since 0.0.1
 * @author Bjørn
 * @version 0.0.1
 */
public interface TileAction {
  /**
   * Perform an Action when a player lands on a tile.
   *
   * @param player The model.Player who lands on the tile
   */
  void perform(Player player);
}
