package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of multiple {@link Die} instances.
 *
 * <p>This class allows for rolling multiple dice at once,
 * and it includes methods for rolling multiple dice and return a specific die.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.2
 */
public class Dice extends Die {
  private final List<Die> diceToPlay;

  /**
   * Constructor for {@link Dice} object which holds a specific number of dice.
   *
   * <p>Creates an {@link ArrayList} containing a specific amount of dice.</p>
   *
   * @param numberOfDice the number of {@link Die} objects
   */
  public Dice(int numberOfDice) {
    diceToPlay = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      diceToPlay.add(new Die());
    }
  }

  /**
   * Returns the total number of dice in the {@code Dice} object.
   *
   * @return the quantity of dices.
   */
  public int getNumberOfDice() {
    return diceToPlay.size();
  }

  /**
   * Rolls multiple dice using {@link Die#roll()} on each die
   * and returns the sum of their values.
   *
   * @return the total sum of all rolled dice
   */
  @Override
  public int roll() {
    int sum = 0;
    for (Die die : diceToPlay) {
      sum += die.roll();
    }
    return sum;
  }

  /**
   * Returns a specific die, and use {@link Die#getValue()} to get its value.
   *
   * @param dieNumber the index of the die to return
   * @return the value of a die
   */
  public int getDie(int dieNumber) {
    return diceToPlay.get(dieNumber).getValue();
  }
}
