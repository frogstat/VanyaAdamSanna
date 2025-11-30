package se.yrgo.game;

import se.yrgo.utilities.DiceSides;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.*;

public class DiceGame {

    private List<Dice> diceSet = new ArrayList<>();
    private int scoreToWin;
    private final Scanner scanner = new Scanner(System.in);
    final List<DiceSides> lowStraight = new ArrayList<>(List.of(DiceSides.ONE, DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE));
    final List<DiceSides> highStraight = new ArrayList<>(List.of(DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));
    final List<DiceSides> flush = new ArrayList<>(List.of(DiceSides.ONE, DiceSides.TWO,
            DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));
    private final Clip diceRollClip;

    public DiceGame() {
        for (int i = 1; i <= 6; i++) {
            diceSet.add(new Dice());
        }
        scoreToWin = 1500;

        String diceRollClipPath = "resources/dice_roll.wav";
        diceRollClip = loadClip(diceRollClipPath);
    }

    /**
     * By Vanya
     * <p>
     * Starting menu for the Dice game.
     * The game always starts here.
     * <p>
     * Choose whether to play, see rules, go to options menu or exit.
     *
     * @throws InterruptedException Required due to slow text feature.
     */
    public void gameMenu() throws InterruptedException {
        System.out.println("***********************************");
        System.out.println("Adam, Sanna och Vanya's DICE GAME!");
        System.out.println("***********************************");
        Thread.sleep(2000);
        int choice;
        while (true) {
            choice = 0;
            slowText("""
                    [1] Play Game
                    [2] Rules
                    [3] Options
                    [4] Exit
                    """);
            while (choice < 1 || choice > 4) {
                choice = inputInt();
            }
            switch (choice) {
                case 1 -> playGame();
                case 2 -> slowText(getRules());
                case 3 -> gameSettings();
                case 4 -> {
                    return;
                }
            }
        }
    }

    /**
     * By Vanya
     * <p>
     * The actual game code itself.
     * Loops until a player reaches the score goal.
     * To make things fair, player 2 gets a final throw if player 1 reaches the winning score.
     */
    private void playGame() throws InterruptedException {
        slowText("Player one! Enter your name: ");
        Player player1 = new Player(scanner.nextLine());
        slowText("Player two! Enter your name: ");
        Player player2 = new Player(scanner.nextLine());

        slowText(player1.getName() + " VS " + player2.getName() + "\n");
        slowText("It's a race to " + scoreToWin + "!\n");
        Player currentPlayer = player1;
        boolean isLastStand = false;

        while (player2.getScore() < scoreToWin) {
            slowText("**********************************\n");
            if (player1.getScore() >= scoreToWin) {
                slowText("LAST STAND! " + player2.getName() + " has one last chance to beat " + player1.getName() + "'s score!\n");
                isLastStand = true;
            }
            slowText(player1 + "\n");
            slowText(player2 + "\n");
            slowText(currentPlayer.getName() + "'s turn! Type 'roll' to throw your dice!\n");
            String answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase("roll")) {
                slowText("Please type roll: ");
                answer = scanner.nextLine();
            }

            throwAllDice();
            printDiceSet();
            if (rethrow()) {
                printDiceSet();
            }
            checkResult(currentPlayer);
            if (isLastStand) {
                break;
            }
            currentPlayer = currentPlayer == player1 ? player2 : player1;
        }
        if (player1.getScore() == player2.getScore()) {
            slowText("It's a tie!\n");
        } else {
            Player winner = player1.getScore() > player2.getScore() ? player1 : player2;
            slowText(winner.getName() + " wins!\n");
        }
        Thread.sleep(1000);
        System.out.println("**********************************");
    }

    /**
     * By Vanya
     * <p>
     * A simple string to show the rules of the game.
     */
    private String getRules() {
        return """
                ************************
                Rules for the Dice game:
                Two players throw 6 dice each and aim to earn points
                through different combinations.
                You can rethrow some or all of your dice once each round.
                First to reach 1500 pts (can be adjusted) wins.
                (If player 1 reaches 1500, player 2 has the opportunity
                to beat their score in a last throw).
                ⚀⚀⚀ = 1000 pts
                ⚁⚁⚁ = 200 pts
                ⚂⚂⚂ = 300 pts
                ⚃⚃⚃ = 400 pts
                ⚄⚄⚄ = 500 pts
                ⚅⚅⚅ = 600 pts
                Each additional die after 3 in a row doubles the points earned.
                ⚁⚁⚁⚁ = 400 pts
                ⚁⚁⚁⚁⚁ = 800 pts
                ⚁⚁⚁⚁⚁⚁ = 1600 pts
                ONE and FIVE always give points.
                ⚀ = 100 pts
                ⚄ = 50 pts
                Additionally there are other scoring combinations.
                ⚀⚁⚂⚃⚄ = 500 pts
                ⚁⚂⚃⚄⚅ = 750 pts
                ⚀⚁⚂⚃⚄⚅ = 1500 pts
                Good luck!
                ************************
                """;
    }

    /**
     * By Vanya
     * <p>
     * Settings menu. Currently only allows changing the score to win.
     */
    private void gameSettings() throws InterruptedException {
        int choice;
        while (true) {
            slowText("""
                    [1] Change winning score
                    [2] Back to menu
                    """);
            choice = inputInt();
            switch (choice) {
                case 1 -> {
                    slowText("Min 500, max 5000. Current winning score: " + scoreToWin + "\n");
                    slowText("Enter new winning score: ");
                    if (setScoreToWin(inputInt())) {
                        slowText("Changed score to win to " + scoreToWin + "\n");
                    } else {
                        slowText("Invalid score\n");
                    }
                }
                case 2 -> {
                    return;
                }
            }
        }
    }

    /**
     * By Vanya
     * <p>
     * Allows the player to change the score to win.
     * The allowed range is 500 to 5000 points.
     *
     * @param scoreToWin the new score goal.
     * @return true if the new score is allowed. False if not.
     */
    public boolean setScoreToWin(int scoreToWin) {
        if (scoreToWin >= 500 && scoreToWin <= 5000) {
            this.scoreToWin = scoreToWin;
            return true;
        }
        return false;
    }

    /**
     * By Vanya
     * <p>
     * Takes a string and prints it letter by letter for prettier output.
     *
     * @param text The string to be printed.
     */
    private void slowText(String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(35);
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
    public boolean hasStraight(List<Dice> diceSet, List<DiceSides> sidesToLookFor) {
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
    public Dice hasThreeOrMoreInARow(List<Dice> diceSet, int target) {
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
    private void removeDice(List<DiceSides> diceToRemove) {
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
     * Checks the result of the player's throw and awards points accordingly.
     * Once a die has contributed to award a point, it is removed from the list so it cannot award more points.
     * <p>
     * Example: If you get ONE-TWO-THREE-FOUR-FIVE-ONE, you will get 600 points for a short straight + a ONE.
     * Even though you got 2 ONES, you only get points for the ONE that wasn't part of the short straight.
     *
     * @param currentPlayer the player who threw the dice. This player earns the points.
     */
    private void checkResult(Player currentPlayer) throws InterruptedException {
        int score = 0;
        if (hasStraight(diceSet, flush)) {
            currentPlayer.addScore(1500);
            slowText("Flush! 1500 pts!\n");
            return; //No point checking the rest if this is true;
        } else if (hasStraight(diceSet, lowStraight)) {
            score += 750;
            removeDice(lowStraight);
            slowText("Long Straight!! 750 pts!\n");
        } else if (hasStraight(diceSet, highStraight)) {
            score += 500;
            removeDice(highStraight);
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

        resetDiceSet();

        slowText(currentPlayer.getName() + " got " + score + " points!\n");
        currentPlayer.addScore(score);

    }

    /**
     * By Vanya
     * <p>
     * This method resets diceSet in preparation of the next round.
     * Since CheckResult() removes dice, this is needed.
     */
    private void resetDiceSet() {
        diceSet.clear();
        for (int i = 1; i <= 6; i++) {
            diceSet.add(new Dice());
        }
    }

    /**
     * By Vanya
     * <p>
     * This is a simple wrapper for scanner.nextInt() that handles input mismatch.
     *
     * @return the valid input the user made.
     */
    private int inputInt() {
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
        }
        return choice;
    }


    public void printDiceSet() throws InterruptedException {
        slowText("Rolling...\n");
        Thread.sleep(500);
        playClip(diceRollClip);
        for (Dice dice : diceSet) {
            Thread.sleep(250);
            System.out.print(dice + " ");
        }
        System.out.println();
        Thread.sleep(500);
    }

    public void throwAllDice() {
        for (Dice dice : diceSet) {
            dice.throwDice();
        }
        diceSet.sort(Comparator.comparing(Dice::getDiceSide));
    }

    public boolean rethrow() throws InterruptedException {
        slowText("Choose which dice to rethrow (1-6). Type '0' when done.\n");

        List<Dice> diceToRethrow = new ArrayList<>();
        while (true) {
            int choice;
            choice = inputInt();
            if (choice == 0) {
                if (diceToRethrow.isEmpty()) {
                    return false;
                }
                break;
            }
            if (choice < 1 || choice > 6) {
                continue;
            }
            choice -= 1;
            Dice dieToRethrow = diceSet.get(choice);
            if (diceToRethrow.contains(dieToRethrow)) {
                diceToRethrow.remove(dieToRethrow);
                slowText("Removed " + dieToRethrow + " from rethrow list\n");
            } else {
                diceToRethrow.add(dieToRethrow);
                slowText("Added " + dieToRethrow + " to rethrow list\n");
            }
        }
        slowText("Rethrow: ");
        for (Dice dieToRethrow : diceToRethrow) {
            dieToRethrow.throwDice();
        }
        return true;
    }

    public Clip loadClip(String path) {
        try {
            File soundFile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception ex) {
            return null;
        }
    }

    public void playClip(Clip clip) {
        if (clip == null) {
            return;
        }
        clip.setFramePosition(0);
        clip.start();
    }
}
