package se.yrgo.game;

import se.yrgo.utilities.DiceSides;

import java.util.Random;

/**
 * This class represents a six-sided die.
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
     * This method rolls a die by generating a random number between 1-6 and updates diceSides based on the result.
     */
    public void throwDice() {
        int result = random.nextInt(1, 7);
        setDiceSideFromThrowDice(result);
    }

    /**
     * This method sets diceSide based on the result of a die roll.
     *
     * @param result an int between 1-6 representing a die roll.
     */
    public void setDiceSideFromThrowDice(int result) {
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
     * This method converts diceSides to a string representation of the side of the dice.
     * In this case, a string represents a image of a side of a die.
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
     * A getter for the member variable diceSide
     *
     * @return returns diceSide.
     */
    public DiceSides getDiceSide() {
        return diceSide;
    }
}
