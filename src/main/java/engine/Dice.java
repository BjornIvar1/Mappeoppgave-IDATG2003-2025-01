package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * The class engine.Dice extends the class engine.Die
 * to represent multiple dice.
 * It includes methods for rolling multiple dice and return a specific die.
 *
 * @since 0.0.1
 * @author Bjørn
 * @version 0.1.0
 */
public class Dice extends Die {
  private final List<Die> dice;

  /**
   * Constructor for engine.Dice object that holds multiple dice.
   * Creates an {@link ArrayList} containing a specific amount of dice.
   *
   * @param numberOfDice stored in the ArrayList dice
   */
  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }

  /**
   * A method that access the quantity of dices in the game.
   *
   * @return the quantity of dices.
   */
  public int getNumberOfDice() {
    return dice.size();
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
    for (Die die : dice) {
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
    return dice.get(dieNumber).getValue();
  }
}
