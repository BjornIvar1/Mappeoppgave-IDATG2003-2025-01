import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class PlayerTest {
  Player player1;
  @BeforeEach
  void setUp() {
    player1 = new Player("John");
  }

  @Test
  void setNamePositiveTest(){
    assertEquals("John", player1.getName());
  }

  @Test
  void setNameNegativeTest(){
    assertThrows(IllegalArgumentException.class, () -> player1.setName(" "));
    assertThrows(IllegalArgumentException.class, () -> player1.setName(null));
  }

}