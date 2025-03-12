package engine;

import java.util.Random;

/**
 * The class engine.Die represents a six sided die,
 * that can generate a number between 1-6.
 *
 * @since 0.0.1
 * @author Bjørn
 * @version 0.0.1
 */
public class Die {
  private int lastRolledValue;
  private final Random random = new Random();

  /**
   * Rolls a die generating a random number between 1-6,
   * using {@link Random}.
   *
   * @return the value of the last rolled
   */
  public int roll() {
    lastRolledValue = random.nextInt(1, 7);
    return lastRolledValue;
  }

  /**
   * Returns the last rolled value.
   *
   * @return the value of the last rolled
   */
  public int getValue() {
    return lastRolledValue;
  }
}