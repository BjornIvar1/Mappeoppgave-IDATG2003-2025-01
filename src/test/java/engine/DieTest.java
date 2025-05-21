package engine;

import model.engine.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {
  Die die;
  @BeforeEach
  void setUp() {
    die = new Die();
  }

  @Test
  void testRollSixSidedDieCorrectly() {
    for (int i = 0; i < 1000; i++) {
      int rolledValue = die.roll();
      assertTrue(0 < rolledValue && rolledValue < 7,
          "Checking that each 1000 die rolled is a number between 1-6");
    }
  }

  @Test
  void testGetValueOfDieCorrectly() {
    int lastRolledValue = die.roll();
    assertEquals(lastRolledValue, die.getValue());
  }
}