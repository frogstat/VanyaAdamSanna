package se.yrgo.game;

import org.junit.jupiter.api.Test;
import se.yrgo.utilities.DiceSides;
import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {

    /**
     * By Sanna
     * This test tests that when Dice is created without parameters, the DiceSides should be one.
     */
    @Test
    public void testDiceWithoutParameters() {
        Dice dice = new Dice();
        assertEquals(DiceSides.ONE, dice.getDiceSide());
    }

    /**
     * By Sanna
     * This test tests that toString method prints out the right DiceSides string.
     * In this case, the output should be diceside one.
     */
    @Test
    public void testDiceToStringOne() {
        Dice dice = new Dice(DiceSides.ONE);
        assertEquals("⚀", dice.toString());
    }

    /**
     * By Sanna
     * This test tests that toString method prints out the right DiceSides string.
     * In this case, the output should be diceside two.
     */
    @Test
    public void testDiceToStringTwo() {
        Dice dice = new Dice(DiceSides.TWO);
        assertEquals("⚁", dice.toString());
    }

    /**
     * By Sanna
     * This test tests that toString method prints out the right DiceSides string.
     * In this case, the output should be diceside three.
     */
    @Test
    public void testDiceToStringThree() {
        Dice dice = new Dice(DiceSides.THREE);
        assertEquals("⚂", dice.toString());
    }

    /**
     * By Sanna
     * This test tests that toString method prints out the right DiceSides string.
     * In this case, the output should be diceside four.
     */
    @Test
    public void testDiceToStringFour() {
        Dice dice = new Dice(DiceSides.FOUR);
        assertEquals("⚃", dice.toString());
    }

    /**
     * By Sanna
     * This test tests that toString method prints out the right DiceSides string.
     * In this case, the output should be diceside five.
     */
    @Test
    public void testDiceToStringFive() {
        Dice dice = new Dice(DiceSides.FIVE);
        assertEquals("⚄", dice.toString());
    }

    /**
     * By Sanna
     * This test tests that toString method prints out the right DiceSides string.
     * In this case, the output should be diceside six.
     */
    @Test
    public void testDiceToStringSix() {
        Dice dice = new Dice(DiceSides.SIX);
        assertEquals("⚅", dice.toString());
    }
}
