package se.yrgo.utilities;

import se.yrgo.game.Dice;
import se.yrgo.game.Player;

import java.util.ArrayList;
import java.util.List;

public class DiceGameLogic {
    final static List<DiceSides> lowStraight = new ArrayList<>(List.of(DiceSides.ONE, DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE));
    final static List<DiceSides> highStraight = new ArrayList<>(List.of(DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));
    final static List<DiceSides> flush = new ArrayList<>(List.of(DiceSides.ONE, DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));


    /**
     * By Vanya
     * <p>
     * Checks the result of the player's throw and awards points accordingly.
     * Once a die has contributed to award a point, it is removed from the list so it cannot award more points.
     * <p>
     * Example: If you get ONE-TWO-THREE-FOUR-FIVE-ONE, you will get 600 points for a short straight + a ONE.
     * Even though you got 2 ONES, you only get points for the ONE that wasn't part of the short straight.
     *
     * @param currentPlayer the player who threw the dice. This player earns the points.
     */
    public static void checkResult(Player currentPlayer, List<Dice> diceSet) throws InterruptedException {
        int score = 0;
        if (hasStraight(diceSet, flush)) {
            currentPlayer.addScore(1500);
            slowText("Flush! 1500 pts!\n");
            return; //No point checking the rest if this is true;
        } else if (hasStraight(diceSet, highStraight)) {
            score += 750;
            removeDice(highStraight, diceSet);
            slowText("Long Straight!! 750 pts!\n");
        } else if (hasStraight(diceSet, lowStraight)) {
            score += 500;
            removeDice(lowStraight, diceSet);
            slowText("Short Straight!! 500 pts!\n");
        } else {
            Dice dice;
            for (int i = 6; i >= 3; i--) {
                int scoreMultiplier = 0;
                switch (i) {
                    case 6 -> scoreMultiplier = 2 * 2 * 2;
                    case 5 -> scoreMultiplier = 2 * 2;
                    case 4 -> scoreMultiplier = 2;
                    case 3 -> scoreMultiplier = 1;
                }
                if ((dice = hasThreeOrMoreInARow(diceSet, i)) != null) {
                    int baseMultiplier = dice.getDiceSide() == DiceSides.ONE ? 1000 : 100;
                    score += dice.getDiceSide().getValue() * baseMultiplier * scoreMultiplier;
                    slowText(i + " in a row! " + dice.getDiceSide().getValue() * baseMultiplier * scoreMultiplier + " pts!\n");
                    Dice finalDice1 = dice;
                    diceSet.removeIf((d) -> d.getDiceSide() == finalDice1.getDiceSide());
                    //Since you can have 2 three-in-a-rows, it checks one more time.
                    if (i == 3) {
                        if ((dice = hasThreeOrMoreInARow(diceSet, i)) != null) {
                            score += dice.getDiceSide().getValue() * baseMultiplier * scoreMultiplier;
                            slowText("Another 3 in a row! " + dice.getDiceSide().getValue() * baseMultiplier * scoreMultiplier + " pts!\n");
                            Dice finalDice2 = dice;
                            diceSet.removeIf((d) -> d.getDiceSide() == finalDice2.getDiceSide());
                        }
                    }
                    break;
                }
            }
        }

        int amountOfOnes = 0;
        int amountOfFives = 0;
        for (Dice die : diceSet) {
            if (die.getDiceSide() == DiceSides.ONE) {
                amountOfOnes++;
                score += 100;
            } else if (die.getDiceSide() == DiceSides.FIVE) {
                amountOfFives++;
                score += 50;
            }
        }
        //If you have more than 2 ones or fives, the above in-a-row functions will apply instead.
        switch (amountOfOnes) {
            case 1 -> slowText("ONE! 100 pts!\n");
            case 2 -> slowText("2 ONES! 200 pts!\n");
        }
        switch (amountOfFives) {
            case 1 -> slowText("FIVE! 50 pts!\n");
            case 2 -> slowText("2 FIVES! 100 pts!\n");
        }

        resetDiceSet(diceSet);

        slowText(currentPlayer.getName() + " got " + score + " points!\n");
        currentPlayer.addScore(score);

    }

    /**
     * By Vanya
     * <p>
     * This method resets diceSet in preparation of the next round.
     * Since CheckResult() removes dice, this is needed.
     */
    public static void resetDiceSet(List<Dice> diceSet) {
        diceSet.clear();
        for (int i = 1; i <= 6; i++) {
            diceSet.add(new Dice());
        }
    }

    /**
     * By Vanya
     * <p>
     * Checks if the diceSet contains a Short Straight, Long Straight, or a Flush.
     *
     * @param sidesToLookFor a list of DiceSides to look for.
     * @return true if all the sides in sidesToLookFor are in diceSet, otherwise false.
     */
    public static boolean hasStraight(List<Dice> diceSet, List<DiceSides> sidesToLookFor) {
        for (DiceSides diceSide : sidesToLookFor) {
            if (diceSet.stream().noneMatch(d -> d.getDiceSide() == diceSide)) {
                return false;
            }
        }
        return true;
    }

    /**
     * By Vanya
     * <p>
     * Analyzes diceSet and checks if it contains X-in-a-row.
     *
     * @param target What to look for. 3 checks for 3-in-a-row, 4 checks for 4-in-a-row etc
     * @return Returns the dice that has the X-in-a-row. Otherwise, returns null.
     */
    public static Dice hasThreeOrMoreInARow(List<Dice> diceSet, int target) {
        if (target < 3) {
            throw new IllegalArgumentException("target must be 3 or more.");
        }
        int amount;

        for (DiceSides side : DiceSides.values()) {
            amount = 0;
            for (Dice die : diceSet) {
                if (die.getDiceSide() == side) {
                    amount++;
                }
                if (amount == target) {
                    return die;
                }
            }
        }
        return null;
    }

    /**
     * By Vanya
     * <p>
     * Takes a list of DiceSides, and removes all dice in diceSet that contain that side.
     *
     * @param diceToRemove A list that contains the sides to remove.
     */
    public static void removeDice(List<DiceSides> diceToRemove, List<Dice> diceSet) {
        for (DiceSides diceSideToRemove : diceToRemove) {
            for (int i = 0; i < 6; i++) {
                if (diceSet.get(i).getDiceSide() == diceSideToRemove) {
                    diceSet.remove(i);
                    break;
                }
            }
        }
    }


    /**
     * By Vanya
     * <p>
     * Clears the terminal screen when switching menus. Does nothing in IDE terminals.
     */
    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * By Vanya
     * <p>
     * Takes a string and prints it letter by letter for prettier output.
     *
     * @param text The string to be printed.
     */
    public static void slowText(String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(35);
        }
    }



}
