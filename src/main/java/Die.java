import java.util.Random;

public class Die {
  private int lastRolledValue;

  public int roll() {
    lastRolledValue = new Random().nextInt(6)+1;
    return lastRolledValue;
  }

  public int getValue() {
    return lastRolledValue;
  }
}
