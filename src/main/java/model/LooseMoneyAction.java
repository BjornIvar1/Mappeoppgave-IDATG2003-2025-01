package model;

public class LooseMoneyAction implements TileAction{
  private int money;
  private String description;

  @Override
  public void perform(Player player) {
    if (player instanceof PlayerInMonopoly playerInMonopoly) {
      playerInMonopoly.updateBalance(playerInMonopoly.getBalance() - looseMoney(money));
    }
  }

  public void setDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setMoney(int money) {
    if (money < 0) {
      throw new IllegalArgumentException("Money cannot be negative");
    }
    this.money = money;
  }

  @Override
  public int looseMoney(int money) {
    return money;
  }

  @Override
  public int getDestinationTile() {
    return 0;
  }

  @Override
  public int getMoney() {
    return money;
  }
}
