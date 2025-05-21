package model.tileactions;

import model.Player;
import utils.exception.NullOrBlankException;
import utils.exception.StringException;

/**
 * Represents the actions in the game of Monopoly.
 *
 * <p>Inherits from the TileAction interface and allows the player
 * to perform actions related to banking, such as receiving
 * and loosing money.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.0.3
 */
abstract class MonopolyActions implements TileAction {
  private String description;

  /**
   * Constructs a Monopoly tile Action with a specified description.
   *
   * @param description of what happens when the player lands on a tile.
   */
  protected MonopolyActions(String description) {
    setDescription(description);
  }

  /**
   * Mutates the description of what happens when the player lands on a tile.
   *
   * @param description of what happens when the player lands on a tile.
   * @throws StringException if the description is null or empty.
   */
  public void setDescription(String description) throws StringException {
    if (description == null || description.isEmpty()) {
      throw new StringException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Perform an Action when a player lands on a tile.
   *
   * <p>Empty method, since it is in use by the subclasses.</p>
   *
   * @param player The model.Player who lands on the tile
   */
  @Override
  public void perform(Player player) throws NullOrBlankException {
    throw new NullOrBlankException("This method must be overridden by subclasses");
  }

  /**
   * Accesses a description of what happens when the player lands on a tile.
   *
   * @return a description.
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Accesses the amount of money the player will get.
   */
  protected abstract void setMoney(int money);
}
