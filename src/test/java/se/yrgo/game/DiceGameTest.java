package se.yrgo.game;

import se.yrgo.utilities.DiceSides;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DiceGameTest {
    
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
    public void loadClipReturnsNullWhenFailedTest() {
        DiceGame diceGame = new DiceGame();
        Clip clip = diceGame.loadClip("fakePath/fakePath");
        assertNull(clip);
    }

    @Test
    public void playClipDoesNotThrowTest() {
        DiceGame diceGame = new DiceGame();
        assertDoesNotThrow(() -> {diceGame.playClip(null);});
    }
}