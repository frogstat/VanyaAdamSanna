import java.util.*;

public class DiceGame {

    private List<Dice> diceSet = new ArrayList<>();
    private int scoreToWin;
    private final Scanner scanner = new Scanner(System.in);

    public DiceGame(){
        for (int i = 1; i <= 6; i++) {
            diceSet.add(new Dice());
        }
        scoreToWin = 1500;
    }

    public void gameMenu(){
        int choice;
        while(true) {
            choice = 0;
            System.out.println("""
                    [1] Play Game
                    [2] Rules
                    [3] Options
                    [4] Exit""");
            while(choice < 1 || choice > 4){
                choice = inputInt();
            }
            switch (choice) {
                case 1 -> System.out.println("Placeholder");
                case 2 -> System.out.println(getRules());
                case 3 -> gameSettings();
                case 4 -> {
                    return;
                }
            }
        }
    }

    private String getRules(){
        return """
                ************************
                Rules for the Dice game:
                Two players throw 6 dice each and aim to earn points through different combinations.
                You can rethrow some or all of your dice once each round.
                First to reach 1500 pts (can be adjusted) wins.
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
                ************************""";
    }

    private void gameSettings(){
        int choice;
        while(true) {
            System.out.println("""
                    [1] Change winning score
                    [2] Back to menu""");
            choice = inputInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Min 500, max 5000. Current winning score: " + scoreToWin);
                    System.out.print("Enter new winning score: ");
                    if (setScoreToWin(inputInt())) {
                        System.out.println("Changed score to win to " + scoreToWin);
                    } else {
                        System.out.println("Invalid score");
                    }
                }
                case 2 -> {
                    return;
                }
            }
        }

    }

    private int inputInt(){
        int choice;
        while(true) {
            System.out.print("Your selection: ");
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

    public boolean setScoreToWin(int scoreToWin) {
        if(scoreToWin > 500 && scoreToWin < 5000){
            this.scoreToWin = scoreToWin;
            return true;
        }
        return false;
    }
}