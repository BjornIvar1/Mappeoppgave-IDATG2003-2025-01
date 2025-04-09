package model;

import engine.BoardGame;

/**
 * Represents a player in the game of Monopoly.
 *
 * <p>Inherits from the Player class and adds functionality for managing
 * the player's balance.</p>
 *
 * @author Arpit @ Bjørn
 * @version 0.0.1
 * @since 0.0.1
 */
public class PlayerInMonopoly extends Player {
  private int balance;

  /**
   * Constructor for the PlayerInMonopoly class.
   *
   * @param name    the name of the player
   * @param game    the board game the player is playing
   * @param balance the initial balance of the player
   */
  public PlayerInMonopoly(String name, BoardGame game, int balance) {
    super(name, game);
    setBalance(balance);
  }

  /**
   * Accessor method for the player's balance.
   *
   * @return the player's balance
   */
  public int getBalance() {
    return balance;
  }

  /**
   * Mutator method for the player's balance.
   *
   * @param balance the new balance of the player
   * @throws IllegalArgumentException if the balance is negative
   */
  public void setBalance(int balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance cannot be negative");
    }
    this.balance = balance;
  }

  /**
   * Updates the player's balance by adding or subtracting an amount.
   *
   * @param amount the amount to add or subtract from the balance
   * @throws IllegalArgumentException if the resulting balance would be negative
   */
  public void updateBalance(int amount) {
    if (amount < 0 && Math.abs(amount) > balance) {
      throw new IllegalArgumentException("Insufficient funds");
    }
    setBalance(amount);
  }

}
