package model.tileactions;

import javafx.scene.paint.Color;
import model.entity.Player;
import utils.exception.IntegerException;
import utils.exception.StringException;

/**
 * Represents the tile action where the player receives money from the bank in Monopoly.
 *
 * <p>This action gives a specified amount of money to the player when landed upon.
 * It extends {@link MonopolyActions} and overrides the methods.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.1.8
 */
public class BankAction implements TileAction {
  private int money;
  private String description;

  /**
   * Constructs a {@link BankAction} object with the specified amount of money and description.
   *
   * @param money the amount of money to be given to the player.
   * @param description the description of the action.
   */
  public BankAction(int money, String description) {
    setDescription(description);
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

  /**
   * Returns the destination tile for the bank action.
   *
   * @return 0 (no specific destination tile).
   */
  @Override
  public int getDestinationTile() {
    return 0;
  }

  /**
   * Returns the tile color for the bank action.
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
   * Sets the amount of money the player will get.
   *
   * @param money that the player will get.
   * @throws IntegerException if the money is lower than 0.
   */
   public void setMoney(int money) throws IntegerException {
    if (money <= 0) {
      throw new IntegerException("Money mus be greater than 0");
    }
    this.money = money;
  }

  /**
   * Sets the description of the bank tile.
   *
   * @param description of what happens when the player lands on a ladder.
   * @throws StringException if the description is null og empty.
   */
  public void setDescription(String description) throws StringException {
    if (description == null || description.isEmpty()) {
      throw new StringException("Description cannot be null or empty");
    }
    this.description = description;
  }
}
