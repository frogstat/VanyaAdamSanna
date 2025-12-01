package se.yrgo.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player player;

    public PlayerTest(){
        player = new Player("John");
    }

    @Test
    public void startScoreMustBeZeroTest(){
        assertEquals(0, player.getScore());
    }

    @Test
    public void startScoreIsNotZeroTest(){
        assertFalse(player.getScore() != 0);
    }

    @Test
    public void addScoreTest(){
        int currentScore = player.getScore();
        player.addScore(100);
        assertEquals(currentScore + 100, player.getScore());
    }

    @Test
    public void addScoreSucceededTest(){
        assertTrue(player.addScore(100));
    }

    @Test
    public void addScoreFailedTest(){
        assertFalse(player.addScore(-50));
    }

    @Test
    public void resetScoreTest(){
        player.addScore(100);
        player.resetScore();
        int currentScore = player.getScore();
        assertEquals(0, currentScore);
    }
}
