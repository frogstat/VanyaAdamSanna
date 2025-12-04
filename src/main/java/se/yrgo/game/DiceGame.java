package se.yrgo.game;

import se.yrgo.utilities.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DiceGame {

    private final List<Dice> diceSet = new ArrayList<>();
    private int scoreToWin;
    private final Scanner scanner = new Scanner(System.in);
    private Clip diceRollClip;

    public DiceGame() {
        for (int i = 1; i <= 6; i++) {
            diceSet.add(new Dice());
        }
        scoreToWin = 1500;

        String diceRollClipPath = "resources/dice_roll_normal.wav";
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
        DiceGameLogic.clearScreen();
        System.out.println("***********************************");
        System.out.println("Adam, Sanna och Vanya's DICE GAME!");
        System.out.println("***********************************");
        Thread.sleep(2000);
        int choice;
        while (true) {
            choice = 0;
            DiceGameLogic.slowText("""
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
                case 2 -> DiceGameLogic.slowText(getRules());
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
        DiceGameLogic.clearScreen();
        DiceGameLogic.slowText("Player one! Enter your name: ");
        Player player1 = new Player(scanner.nextLine());
        DiceGameLogic.slowText("Player two! Enter your name: ");
        Player player2 = new Player(scanner.nextLine());

        DiceGameLogic.slowText(player1.getName() + " VS " + player2.getName() + "\n");
        DiceGameLogic.slowText("It's a race to " + scoreToWin + "!\n");
        Player currentPlayer = player1;
        boolean isLastStand = false;

        while (player2.getScore() < scoreToWin) {
            DiceGameLogic.slowText("**********************************\n");
            if (player1.getScore() >= scoreToWin) {
                DiceGameLogic.slowText("LAST STAND! " + player2.getName() + " has one last chance to beat " + player1.getName() + "'s score!\n");
                isLastStand = true;
            }
            DiceGameLogic.slowText(player1 + "\n");
            DiceGameLogic.slowText(player2 + "\n");
            DiceGameLogic.slowText(currentPlayer.getName() + "'s turn! Type 'roll' to throw your dice!\n");
            String answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase("roll")) {
                DiceGameLogic.slowText("Please type roll: ");
                answer = scanner.nextLine();
            }

            throwAllDice();
            printDiceSet();
            if (rethrow()) {
                printDiceSet();
            }
            DiceGameLogic.checkResult(currentPlayer, diceSet);
            if (isLastStand) {
                break;
            }
            currentPlayer = currentPlayer == player1 ? player2 : player1;
        }
        if (player1.getScore() == player2.getScore()) {
            DiceGameLogic.slowText("It's a tie!\n");
        } else {
            Player winner = player1.getScore() > player2.getScore() ? player1 : player2;
            DiceGameLogic.slowText(winner.getName() + " wins!\n");
        }
        Thread.sleep(1000);
        DiceGameLogic.slowText("**********************************\n");
    }

    /**
     * By Vanya
     * <p>
     * A simple string to show the rules of the game.
     */
    private String getRules() {
        DiceGameLogic.clearScreen();
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
        DiceGameLogic.clearScreen();
        int choice;
        while (true) {
            DiceGameLogic.slowText("""
                    [1] Change winning score
                    [2] Change dice throw sound effect
                    [3] Back to menu
                    """);
            choice = inputInt();
            switch (choice) {
                case 1 -> {
                    DiceGameLogic.slowText("Min 500, max 5000. Current winning score: " + scoreToWin + "\n");
                    DiceGameLogic.slowText("Enter new winning score: ");
                    if (setScoreToWin(inputInt())) {
                        DiceGameLogic.slowText("Changed winning score to " + scoreToWin + "\n");
                    } else {
                        DiceGameLogic.slowText("Invalid score\n");
                    }
                }
                case 2 -> {
                    DiceGameLogic.slowText("[1] Normal (default)\n[2] Special\n");
                    if (setSoundEffect(inputInt())) {
                        DiceGameLogic.slowText("Success!\n");
                    } else {
                        DiceGameLogic.slowText("Invalid choice.\n");
                    }
                }
                case 3 -> {
                    DiceGameLogic.clearScreen();
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
     * Simple option to change dice roll sound effect.
     */
    public boolean setSoundEffect(int choice) {
        switch (choice) {
            case 1 -> {
                diceRollClip = loadClip("resources/dice_roll_normal.wav");
                return true;
            }
            case 2 -> {
                diceRollClip = loadClip("resources/dice_roll_special.wav");
                return true;
            }
        }
        return false;
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

    /**
     * By Sanna
     * This method prints out text and plays a sound clip.
     */
    public void printDiceSet() throws InterruptedException {
        DiceGameLogic.slowText("Rolling...\n");
        Thread.sleep(500);
        playClip(diceRollClip);
        for (Dice dice : diceSet) {
            Thread.sleep(250);
            System.out.print(dice + " ");
        }
        System.out.println();
        Thread.sleep(500);
    }

    /**
     * By Sanna
     * This method throws all dice and sorts them.
     */
    public void throwAllDice() {
        for (Dice dice : diceSet) {
            dice.throwDice();
        }
        diceSet.sort(Comparator.comparing(Dice::getDiceSide));
    }

    /**
     * By Adam
     * A rethrow method that lets player choose if they wish to rethrow die/dices
     * @return true if player chooses to rethrow die/dices, and false if player chooses not
     * to rethrow die/dices
     */
    public boolean rethrow() throws InterruptedException {
        DiceGameLogic.slowText("Choose which dice to rethrow (1-6). Type '0' when done.\n");

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
                DiceGameLogic.slowText("Removed " + dieToRethrow + " from rethrow list\n");
            } else {
                diceToRethrow.add(dieToRethrow);
                DiceGameLogic.slowText("Added " + dieToRethrow + " to rethrow list\n");
            }
        }
        DiceGameLogic.slowText("Rethrow: ");
        for (Dice dieToRethrow : diceToRethrow) {
            dieToRethrow.throwDice();
        }
        return true;
    }

    /**
     * By Sanna
     * This method loads an audio file. Does not work in WSL. Requires native terminal.
     *
     * @param path the path to the audio file.
     * @return Returns a clip object that has the audio. Null if something goes wrong while trying to load the clip.
     */
    public Clip loadClip(String path) {
        try {
            File soundFile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (IllegalArgumentException ex) {
            System.err.println("Audio playback doesn't work.");
            return null;
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("Unsupported audio file.");
            return null;
        } catch (LineUnavailableException | IOException ex) {
            return null;
        }
    }

    /**
     * By Sanna
     * This method plays audio file.
     *
     * @param clip audio file to be played to be played.
     */
    public void playClip(Clip clip) {
        if (clip == null) {
            return;
        }
        clip.setFramePosition(0);
        clip.start();
    }
}
