package edu.ntnu.idi.bidata.engine;

import edu.ntnu.idi.bidata.model.engine.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {
  Dice dice;
  @BeforeEach
  void setUp() {
    dice = new Dice(2);
  }

  @Test
  void testRollSumsDiceCorrectly() {
    for (int i = 0; i < 1000; i++) {
      int rolledValue = dice.roll();
      assertTrue(1 < rolledValue && rolledValue < 13,
          "Checking that two dice rolled 1000 times adds up to a number between 2-12");
    }
  }

  @Test
  void testGetDieReturnCorrectValue() {
    dice.roll();
    int dice0 = dice.getDie(0);
    int dice1 = dice.getDie(1);

    assertEquals(dice0, dice.getDie(0));
    assertEquals(dice1, dice.getDie(1));
  }

  @Test
  void getLastRollSPositiveTest() {
    dice.roll();
    int lastRoll = dice.getLastRollS();
    assertTrue(1 < lastRoll && lastRoll < 13,
        "Checking that two dice rolled 1000 times adds up to a number between 2-12");
  }

  @Test
  void getLastRollSNegativeTest() {
    int lastRoll = dice.getLastRollS();
    assertEquals(0, lastRoll, "Checking that the last roll is 0 before rolling the dice");
  }
}