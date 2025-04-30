package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;
import model.exception.NegativeIntegerException;

/**
 * Represents the action of losing money in the game of Monopoly.
 *
 * <p>Inherits from the TileAction interface and allows the player
 * to perform actions related to losing money
 * when landing on a tile.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.2.2
 */
public class LooseMoneyAction extends MonopolyActions {
  private int money;
  private String description;

  /**
   * Creates a loose money action with a specified amount of money.
   *
   * @param money       the amount of money to be deducted from the player.
   * @param description the description of the action.
   */
  public LooseMoneyAction(int money, String description) {
    super(description);
    setMoney(money);
  }

  /**
   * Updates the player's balance by deducting the specified amount of money.
   *
   * @param player that loses the money.
   */
  @Override
  public void perform(Player player) throws NegativeIntegerException {
    if (player.getBalance() <= getLooseMoney()) {
      player.setBalance(0);
    } else {
      player.setBalance(player.getBalance() - getLooseMoney());
    }
  }

  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Mutates the amount of money the player will lose.
   *
   * @param money that the player will lose.
   * @throws IllegalArgumentException if the money is less than 0.
   */
  public void setMoney(int money) throws IllegalArgumentException {
    if (money <= 0) {
      throw new IllegalArgumentException("Money cannot be smaller than 0");
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

  @Override
  public int getDestinationTile() {
    return 0; // No specific destination tile for LooseMoneyAction.
  }


  /**
   * Mutates the description of what happens when the player lands on a tile.
   *
   * @param description of what happens when the player lands on a tile.
   */
  @Override
  public void setDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
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
