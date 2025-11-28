package se.yrgo.main;
import se.yrgo.game.DiceGame;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DiceGame diceGame = new DiceGame();
        diceGame.gameMenu();
    }
}