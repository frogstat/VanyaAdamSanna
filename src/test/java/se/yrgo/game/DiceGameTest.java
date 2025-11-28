package se.yrgo.game;

import se.yrgo.utilities.DiceSides;
import se.yrgo.game.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DiceGameTest {

    final List<DiceSides> lowStraight = new ArrayList<>(List.of(DiceSides.ONE, DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE));
    final List<DiceSides> highStraight = new ArrayList<>(List.of(DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));
    final List<DiceSides> flush = new ArrayList<>(List.of(DiceSides.ONE, DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));

    @Test
    public void setScoreToWinTooLowTest() {
        DiceGame diceGame = new DiceGame();
        assertFalse(diceGame.setScoreToWin(499));
    }

    @Test
    public void setScoreToWinTooHighTest() {
        DiceGame diceGame = new DiceGame();
        assertFalse(diceGame.setScoreToWin(5001));
    }

    @Test
    public void setScoreToWinRightTest() {
        DiceGame diceGame = new DiceGame();
        assertTrue(diceGame.setScoreToWin(3000));
    }

    @Test
    public void hasStraightLowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        DiceGame diceGame = new DiceGame();
        assertTrue(diceGame.hasStraight(diceSet, lowStraight));
    }

    @Test
    public void hasStraightNotLowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.SIX));
        DiceGame diceGame = new DiceGame();
        assertFalse(diceGame.hasStraight(diceSet, lowStraight));
    }

    @Test
    public void hasStraightHighTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        diceSet.add(new Dice(DiceSides.SIX));
        DiceGame diceGame = new DiceGame();
        assertTrue(diceGame.hasStraight(diceSet, highStraight));
    }

    @Test
    public void hasStraightNotHighTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.FOUR));
        diceSet.add(new Dice(DiceSides.FIVE));
        diceSet.add(new Dice(DiceSides.SIX));
        DiceGame diceGame = new DiceGame();
        assertFalse(diceGame.hasStraight(diceSet, highStraight));
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
        DiceGame diceGame = new DiceGame();
        assertTrue(diceGame.hasStraight(diceSet, flush));
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
        DiceGame diceGame = new DiceGame();
        assertFalse(diceGame.hasStraight(diceSet, flush));
    }

    @Test
    public void hasThreeInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        DiceGame diceGame = new DiceGame();
        assertNotNull(diceGame.hasThreeOrMoreInARow(diceSet, 3));
    }

    @Test
    public void hasFourInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        DiceGame diceGame = new DiceGame();
        assertNotNull(diceGame.hasThreeOrMoreInARow(diceSet, 4));
    }

    @Test
    public void hasFiveInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        DiceGame diceGame = new DiceGame();
        assertNotNull(diceGame.hasThreeOrMoreInARow(diceSet, 5));
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
        DiceGame diceGame = new DiceGame();
        assertNotNull(diceGame.hasThreeOrMoreInARow(diceSet, 6));
    }

    @Test
    public void hasNotThreeInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.ONE));
        diceSet.add(new Dice(DiceSides.TWO));
        DiceGame diceGame = new DiceGame();
        assertNull(diceGame.hasThreeOrMoreInARow(diceSet, 3));
    }

    @Test
    public void hasNotFourInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        DiceGame diceGame = new DiceGame();
        assertNull(diceGame.hasThreeOrMoreInARow(diceSet, 4));
    }

    @Test
    public void hasNotFiveInARowTest() {
        List<Dice> diceSet = new ArrayList<>();
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.THREE));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        diceSet.add(new Dice(DiceSides.TWO));
        DiceGame diceGame = new DiceGame();
        assertNull(diceGame.hasThreeOrMoreInARow(diceSet, 5));
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
        DiceGame diceGame = new DiceGame();
        assertNull(diceGame.hasThreeOrMoreInARow(diceSet, 6));
    }
}