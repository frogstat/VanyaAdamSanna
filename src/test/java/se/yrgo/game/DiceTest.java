package se.yrgo.game;

import org.junit.jupiter.api.Test;
import se.yrgo.utilities.DiceSides;
import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {

    @Test
    public void testDiceWithoutParameters() {
        Dice dice = new Dice();
        assertEquals(DiceSides.ONE, dice.getDiceSide());
    }

    @Test
    public void setDiceSideFromThrowDiceTestOne() {
        Dice dice = new Dice();
        dice.setDiceSideFromThrowDice(1);
        assertEquals(DiceSides.ONE, dice.getDiceSide());
    }

    @Test
    public void setDiceSideFromThrowDiceTestTwo() {
        Dice dice = new Dice();
        dice.setDiceSideFromThrowDice(2);
        assertEquals(DiceSides.TWO, dice.getDiceSide());
    }

    @Test
    public void setDiceSideFromThrowDiceTestThree() {
        Dice dice = new Dice();
        dice.setDiceSideFromThrowDice(3);
        assertEquals(DiceSides.THREE, dice.getDiceSide());
    }

    @Test
    public void setDiceSideFromThrowDiceTestFour() {
        Dice dice = new Dice();
        dice.setDiceSideFromThrowDice(4);
        assertEquals(DiceSides.FOUR, dice.getDiceSide());
    }

    @Test
    public void setDiceSideFromThrowDiceTestFive() {
        Dice dice = new Dice();
        dice.setDiceSideFromThrowDice(5);
        assertEquals(DiceSides.FIVE, dice.getDiceSide());
    }

    @Test
    public void setDiceSideFromThrowDiceTestSix() {
        Dice dice = new Dice();
        dice.setDiceSideFromThrowDice(6);
        assertEquals(DiceSides.SIX, dice.getDiceSide());
    }

    @Test
    public void testDiceToStringOne() {
        Dice dice = new Dice(DiceSides.ONE);
        assertEquals("⚀", dice.toString());
    }

    @Test
    public void testDiceToStringTwo() {
        Dice dice = new Dice(DiceSides.TWO);
        assertEquals("⚁", dice.toString());
    }

    @Test
    public void testDiceToStringThree() {
        Dice dice = new Dice(DiceSides.THREE);
        assertEquals("⚂", dice.toString());
    }

    @Test
    public void testDiceToStringFour() {
        Dice dice = new Dice(DiceSides.FOUR);
        assertEquals("⚃", dice.toString());
    }

    @Test
    public void testDiceToStringFive() {
        Dice dice = new Dice(DiceSides.FIVE);
        assertEquals("⚄", dice.toString());
    }

    @Test
    public void testDiceToStringSix() {
        Dice dice = new Dice(DiceSides.SIX);
        assertEquals("⚅", dice.toString());
    }
}
