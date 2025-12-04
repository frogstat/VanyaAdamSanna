package se.yrgo.utilities;

import org.junit.jupiter.api.Test;
import se.yrgo.game.Dice;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class DiceGameLogicTest {
    @Test
    public void hasStraightLowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        assertTrue(DiceGameLogic.hasStraight(diceSet, DiceGameLogic.lowStraight));
    }

    @Test
    public void hasStraightNotLowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.SIX));
        assertFalse(DiceGameLogic.hasStraight(diceSet, DiceGameLogic.lowStraight));
    }

    @Test
    public void hasStraightHighTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        diceSet.add(new Dice(DiceSides.SIX));
        assertTrue(DiceGameLogic.hasStraight(diceSet, DiceGameLogic.highStraight));
    }

    @Test
    public void hasStraightNotHighTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        diceSet.add(new Dice(DiceSides.SIX));
        assertFalse(DiceGameLogic.hasStraight(diceSet, DiceGameLogic.highStraight));
    }

    @Test
    public void hasStraightFlushTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        diceSet.add(new Dice(DiceSides.SIX));
        assertTrue(DiceGameLogic.hasStraight(diceSet, DiceGameLogic.flush));
    }

    @Test
    public void hasStraightNotFlushTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        diceSet.add(new Dice(DiceSides.SIX));
        assertFalse(DiceGameLogic.hasStraight(diceSet, DiceGameLogic.flush));
    }

    @Test
    public void hasThreeInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNotNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 3));
    }

    @Test
    public void hasFourInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNotNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 4));
    }

    @Test
    public void hasFiveInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNotNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 5));
    }

    @Test
    public void hasSixInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNotNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 6));
    }

    @Test
    public void hasNotThreeInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 3));
    }

    @Test
    public void hasNotFourInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 4));
    }

    @Test
    public void hasNotFiveInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 5));
    }

    @Test
    public void hasNotSixInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        assertNull(DiceGameLogic.hasThreeOrMoreInARow(diceSet, 6));
    }
}
