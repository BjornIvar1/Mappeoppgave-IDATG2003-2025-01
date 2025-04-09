package model;

/**
 * Represents the bank action in the game of Monopoly.
 *
 * <p>Inherits from the TileAction interface and allows the player
 * to perform actions related to banking, such as
 * withdrawing money.</p>
 *
 * @since 0.0.1
 * @author Arpit @ Bjørn
 * @version 0.0.1
 */
public class BankAction implements TileAction{
  private String description;
  private int money;

  public BankAction(int money, String description) {
    setMoney(money);
    setDescription(description);
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

  public void setDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }

  /**
   * Accesses the description the player will get when landing
   * on the tile.
   *
   * @return description of the action.
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Accesses the amount of money the player will get.
   *
   * @return money that the player will get.
   */
  @Override
  public int getMoney() {
    return money;
  }

  /**
   * Mutates the amount of money the player will get.
   *
   * @param money that the player will get.
   * @throws IllegalArgumentException if the money is lower than 0.
   */
  private void setMoney(int money) {
    if (money < 0) {
      throw new IllegalArgumentException("Money cannot be negative");
    }
    this.money = money;
  }

  @Override
  public int getDestinationTile() {
    return 0; // Not applicable for bank action
  }

  @Override
  public int looseMoney(int money) {
    return 0; // Not applicable for bank action
  }
}
