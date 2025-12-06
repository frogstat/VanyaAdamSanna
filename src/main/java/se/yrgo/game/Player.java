package se.yrgo.game;

/**
 * Player class for the user to have their unique name and track their score.
 * By Adam
 */
public class Player {
    private String name;
    private int score;

    /**
     * By Adam
     * The constructor for the player. Player starts with 0 points
     *
     * @param name To let the player put in its name
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * By Adam
     * A getter for getting player name
     *
     * @return Returns the name of player
     */
    public String getName() {
        return name;
    }

    /**
     * By Adam
     * A getter for getting score
     *
     * @return Returns the score of player
     */
    public int getScore() {
        return score;
    }

    /**
     * By Adam
     * Method to add score for the player
     */
    public boolean addScore(int score) {
        if (score <= 0) {
            return false;
        }

        this.score += score;
        return true;
    }

    /**
     * By Adam
     * A reset score method, to reset code
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * By Adam
     * A toString method
     *
     * @return Returns the player's name and shows it's current points
     */
    public String toString() {
        return getName() + " has " + getScore() + " points";
    }
}