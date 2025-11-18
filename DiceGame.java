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

    public void gameMenu() throws InterruptedException {
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
                case 1 -> playGame();
                case 2 -> System.out.println(getRules());
                case 3 -> gameSettings();
                case 4 -> {
                    return;
                }
            }
        }
    }

    private void playGame() throws InterruptedException {
        System.out.print("Player one! Enter your name: ");
        Player player1 = new Player(scanner.nextLine());
        System.out.print("Player Two! Enter your name: ");
        Player player2 = new Player(scanner.nextLine());
        Player currentPlayer = player1;

        while (player1.getScore() <= scoreToWin && player2.getScore() <= scoreToWin){

            System.out.println(currentPlayer.getName() + "'s turn! Type 'roll' to throw your dice!");
            String answer = "";
            answer = scanner.nextLine();
            while(!answer.equalsIgnoreCase("roll")){
                System.out.print("Please type roll: ");
                answer = scanner.nextLine();
            }

            throwAllDice();
            printDiceSet();
            rethrow();
            checkResult(currentPlayer);
            printDiceSet();


            if(currentPlayer.equals(player1)){
                currentPlayer = player2;
            }
            else {
                currentPlayer = player1;
            }
        }

        player1.resetScore();
        player2.resetScore();
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

    private String isStraight(){
        if(diceSet.contains(new Dice(DiceSides.ONE))
                && diceSet.contains(new Dice(DiceSides.TWO))
                && diceSet.contains(new Dice(DiceSides.THREE))
                && diceSet.contains(new Dice(DiceSides.FOUR))
                && diceSet.contains(new Dice(DiceSides.FIVE))){
            if(diceSet.contains(new Dice(DiceSides.SIX))){
                return "Flush";
            }
            return "Short Straight";
        }
        else if (diceSet.contains(new Dice(DiceSides.TWO))
                && diceSet.contains(new Dice(DiceSides.THREE))
                && diceSet.contains(new Dice(DiceSides.FOUR))
                && diceSet.contains(new Dice(DiceSides.FIVE))
                && diceSet.contains(new Dice(DiceSides.SIX))) {
            return "Long Straight";
        }
        return "none";
    }



    private void checkResult(Player currentPlayer){
        if(isStraight().equals("Flush")){
            currentPlayer.addScore(1500);
            System.out.println("Flush! 1500 pts!");
        }
        else if(isStraight().equals("Long Straight")){
            currentPlayer.addScore(750);
            System.out.println("Long Straight!! 750 pts!");
        }
        else if(isStraight().equals("Short Straight")){
            currentPlayer.addScore(500);
            System.out.println("Short Straight!! 500 pts!");
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

    public void printDiceSet() throws InterruptedException {
        System.out.println("Rolling...");
        Thread.sleep(500);
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

    public void rethrow() {
        System.out.println("Do you wish to rethrow any dice? Answer y/n: ");
        String askRethrow = "";
        while(!askRethrow.equalsIgnoreCase("n") && !askRethrow.equalsIgnoreCase("y")) {
            askRethrow = scanner.nextLine();
        }
        if (askRethrow.equalsIgnoreCase("n")) {
            return;
        }

        List<Dice> diceToRethrow = new ArrayList<>();
        System.out.println("Type 0 when you're done.");
        while(true){
            int choice;
            choice = inputInt();
            if(choice == 0){
                break;
            }
            if (choice < 1 || choice > 6){
                continue;
            }
            choice -= 1;
            Dice dieToRethrow = diceSet.get(choice);
            diceToRethrow.add(dieToRethrow);
            System.out.println("Added " + dieToRethrow + " to rethrow list");
        }
        for(Dice dieToRethrow : diceToRethrow){
            dieToRethrow.throwDice();
        }
    }
}