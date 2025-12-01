package se.yrgo.game;
/**
 * A player class for the user to have their unique name and track their score.
 * -Adam
 */
public class Player {
    private String name;
    private int score;

    /**
     * The constructor for the player. Player starts with 0 points
     * @param name To let the player put in its name
     * -Adam
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * A getter for getting player name
     * @return Returns the name of player
     * -Adam
     */
    public String getName(){
        return name;
    }

    /**
     * A getter for getting score
     * @return Returns the score of player
     * -Adam
     */
    public int getScore(){
        return score;
    }

    /**
     * Method to add score for the player
     * -Adam
     */
    public boolean addScore(int score){
        if (score <= 0){
            return false;
        }

        this.score += score;
        return true;
    }

    /**
     * A reset score method, to reset code
     * -Adam
     */
    public void resetScore(){
        this.score = 0;
    }

    /**
     * A toString method
     * @return Returns the player's name and shows it's current points
     * -Adam
     */
    public String toString(){
        return getName() + " has " + getScore() + " points";
    }
}