package edu.ntnu.idi.bidata.model.tileactions;

import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.utils.exception.IntegerException;
import edu.ntnu.idi.bidata.utils.exception.StringException;
import javafx.scene.paint.Color;

/**
 * Represents the action of losing money in the game of Monopoly.
 *
 * <p>Inherits from the TileAction interface and allows the player
 * to perform actions related to losing money
 * when landing on a tile.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.2.4
 */
public class LooseMoneyAction implements TileAction {
  private int money;
  private String description;

  /**
   * Creates a loose money action with a specified amount of money.
   *
   * @param money       the amount of money to be deducted from the player.
   * @param description the description of the action.
   */
  public LooseMoneyAction(int money, String description) {
    setDescription(description);
    setMoney(money);
  }

  /**
   * Updates the player's balance by deducting the specified amount of money.
   *
   * @param player that loses the money.
   */
  @Override
  public void perform(Player player) throws IntegerException {
    if (player.getBalance() <= getLooseMoney()) {
      player.setBalance(0);
    } else {
      player.setBalance(player.getBalance() - getLooseMoney());
    }
  }


  /**
   * Returns the description of the loose money action.
   *
   * @return the description of the loose money action.
   */
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public int getDestinationTile() {
    return 0; // No specific destination tile for loose money action
  }

  /**
   * Mutates the amount of money the player will lose.
   *
   * @param money that the player will lose.
   * @throws IntegerException if the money is less than 0.
   */
  public void setMoney(int money) throws IntegerException {
    if (money <= 0) {
      throw new IntegerException("Money cannot be smaller than 0");
    }
    this.money = money;
  }

  /**
   * Accesses the amount of money the player will lose.
   *
   * @return money that the player will lose.
   */
  public int getLooseMoney() {
    return money;
  }

  /**
   * Mutates the description of what happens when the player lands on a tile.
   *
   * @param description of what happens when the player lands on a tile.
   * @throws StringException if the description is null or blank.
   */
  public void setDescription(String description) throws StringException {
    if (description == null || description.isEmpty()) {
      throw new StringException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Returns the color of the loose money action tile.
   *
   * @return the color of the loose money action tile.
   */
  public Color getColor() {
    return Color.web("#e5626a");
  }

}
