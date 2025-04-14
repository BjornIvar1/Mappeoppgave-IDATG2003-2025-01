package model;

import javafx.scene.paint.Color;

/**
 * Represents the bank action in the game of Monopoly.
 *
 * <p>Inherits from the TileAction interface and allows the player
 * to perform actions related to banking, such as
 * withdrawing money.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.0.2
 */
public class BankAction extends MonopolyActions{
  private int money;

  /**
   * Creates a bank action with a specified amount of money.
   *
   * @param money       the amount of money to be given to the player.
   * @param description the description of the action.
   */
  public BankAction(int money, String description) {
    super(description);
    setMoney(money);
  }

  /**
   * Updates the players balance with the amount of money.
   *
   * @param player that gets the money.
   */
  @Override
  public void perform(Player player) {
    if (player instanceof PlayerInMonopoly playerInMonopoly) {
      playerInMonopoly.updateBalance(playerInMonopoly.getBalance() + getMoney());
    }
  }

  @Override
  public int getDestinationTile() {
    return 0; // No specific destination tile for bank action
  }

  @Override
  public Color getColor() {
    return null;
  }

  /**
   * Accesses the amount of money the player will get.
   *
   * @return money that the player will get.
   */
  public int getMoney() {
    return money;
  }

  /**
   * Mutates the amount of money the player will get.
   *
   * @param money that the player will get.
   * @throws IllegalArgumentException if the money is lower than 0.
   */
  public void setMoney(int money) throws IllegalArgumentException {
    if (money < 0) {
      throw new IllegalArgumentException("Money cannot be negative");
    }
    this.money = money;
  }
}
