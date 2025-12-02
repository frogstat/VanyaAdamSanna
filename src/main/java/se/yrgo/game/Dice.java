package se.yrgo.game;

import se.yrgo.utilities.DiceSides;

import java.util.Random;

/**
 * By Sanna
 */
public class Dice {
    private DiceSides diceSide;
    Random random = new Random();

    public Dice() {
        this.diceSide = DiceSides.ONE;
    }

    public Dice(DiceSides diceSide) {
        this.diceSide = diceSide;
    }

    /**
     * By Sanna
     * This method chooses a random number between 1-6 and sets diceSide accordingly.
     */
    public void throwDice() {
        int result = random.nextInt(1, 7);
        switch (result) {
            case 1 -> diceSide = DiceSides.ONE;
            case 2 -> diceSide = DiceSides.TWO;
            case 3 -> diceSide = DiceSides.THREE;
            case 4 -> diceSide = DiceSides.FOUR;
            case 5 -> diceSide = DiceSides.FIVE;
            case 6 -> diceSide = DiceSides.SIX;
        }
    }

    /**
     * By Sanna
     * This method converts diceSides to a string representation fo the side of the dice.
     *
     * @return a string representation of the side of the dice.
     */
    @Override
    public String toString() {
        return switch (diceSide) {
            case ONE -> "⚀";
            case TWO -> "⚁";
            case THREE -> "⚂";
            case FOUR -> "⚃";
            case FIVE -> "⚄";
            case SIX -> "⚅";
        };
    }

    /**
     * By Sanna
     * A getter for the member varible diceSide
     *
     * @return returns diceSide.
     */
    public DiceSides getDiceSide() {
        return diceSide;
    }
}
