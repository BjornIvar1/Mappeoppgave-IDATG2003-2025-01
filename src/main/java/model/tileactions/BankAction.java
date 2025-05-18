package model.tileactions;

import javafx.scene.paint.Color;
import model.Player;

/**
 * Represents the bank action in the game of Monopoly.
 *
 * <p>Inherits from the TileAction interface and allows the player
 * to perform actions related to banking, such as
 * withdrawing money.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.1.6
 */
public class BankAction extends MonopolyActions {
  private int money;
  private String description;
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
    player.setBalance(player.getBalance() + getMoney());
  }
  /**
   * Returns the description of the bank action.
   *
   * @return the description of the bank action.
   */
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public int getDestinationTile() {
    return 0; // No specific destination tile for bank action
  }

  /**
   * Returns the color of the bank action.
   *
   * @return the color of the bank action.
   */
  public Color getColor() {
    return Color.web("#f5f04b");
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
    if (money <= 0) {
      throw new IllegalArgumentException("Money cannot be negative");
    }
    this.money = money;
  }

  /**
   * Mutates the description.
   *
   * @param description of what happens when the player lands on a ladder.
   * @throws IllegalArgumentException if the description is null og empty.
   */
  @Override
  public void setDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }
}
