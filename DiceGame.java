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
        System.out.println("***********************************");
        System.out.println("Adam, Sanna och Vanya's DICE GAME!");
        System.out.println("***********************************");
        Thread.sleep(2000);
        int choice;
        while(true) {
            choice = 0;
            slowText("""
                    [1] Play Game
                    [2] Rules
                    [3] Options
                    [4] Exit""",true);
            while(choice < 1 || choice > 4){
                choice = inputInt();
            }
            switch (choice) {
                case 1 -> playGame();
                case 2 -> slowText(getRules(),true);
                case 3 -> gameSettings();
                case 4 -> {
                    return;
                }
            }
        }
    }

    private void playGame() throws InterruptedException {
        slowText("Player one! Enter your name: ",false);
        Player player1 = new Player(scanner.nextLine());
        slowText("Player Two! Enter your name: ", false);
        Player player2 = new Player(scanner.nextLine());

        slowText(player1.getName() + " VS " + player2.getName(),true);
        slowText("It's a race to " + scoreToWin + "!", true);
        Player currentPlayer = player1;
        boolean isLastStand = false;

        while (player2.getScore() < scoreToWin){
            slowText("**********************************",true);
            if(player1.getScore() >= scoreToWin){
                slowText("LAST STAND! " + player2.getName() + " has one last chance to beat " + player1.getName() + "'s score!",true);
                isLastStand = true;
            }
            slowText(player1.toString(),true);
            slowText(player2.toString(), true);
            slowText(currentPlayer.getName() + "'s turn! Type 'roll' to throw your dice!",true);
            String answer = scanner.nextLine();
            while(!answer.equalsIgnoreCase("roll")){
                slowText("Please type roll: ",false);
                answer = scanner.nextLine();
            }

            throwAllDice();
            printDiceSet();
            if(rethrow()) {
                printDiceSet();
            }
            checkResult(currentPlayer);
            if(isLastStand){
                break;
            }
            currentPlayer = currentPlayer == player1 ? player2 : player1;
        }
        if(player1.getScore() == player2.getScore()){
            slowText("It's a tie!",true);
        }
        else{
            Player winner = player1.getScore() > player2.getScore() ? player1 : player2;
            slowText(winner.getName() + " wins!",true);
        }
        Thread.sleep(1000);
        System.out.println("**********************************");
    }

    private String getRules(){
        return """
                ************************
                Rules for the Dice game:
                Two players throw 6 dice each and aim to earn points through different combinations.
                You can rethrow some or all of your dice once each round.
                First to reach 1500 pts (can be adjusted) wins.
                (If player 1 reaches 1500, player 2 has the opportunity to beat their score in a last throw).
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

    private void gameSettings() throws InterruptedException {
        int choice;
        while(true) {
            slowText("""
                    [1] Change winning score
                    [2] Back to menu""",true);
            choice = inputInt();
            switch (choice) {
                case 1 -> {
                    slowText("Min 500, max 5000. Current winning score: " + scoreToWin,true);
                    slowText("Enter new winning score: ",false);
                    if (setScoreToWin(inputInt())) {
                        slowText("Changed score to win to " + scoreToWin,true);
                    } else {
                        slowText("Invalid score",true);
                    }
                }
                case 2 -> {
                    return;
                }
            }
        }
    }

    private boolean hasStraight(List<DiceSides> sidesToLookFor){
        for(DiceSides diceSide : sidesToLookFor) {
            if(diceSet.stream().noneMatch(d -> d.getDiceSide() == diceSide)){
                return false;
            }
        }
        return true;
    }

    private void slowText(String text,boolean lineBreak) throws InterruptedException{
        for (char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(25);
        }
        if(lineBreak){
            System.out.println();
        }
    }

    private Dice hasThreeOrMoreInARow(int target){
        int amount;

        for(DiceSides side : DiceSides.values()){
            amount = 0;
            for(Dice die : diceSet){
                if(die.getDiceSide() == side){
                    amount++;
                }
                if(amount == target){
                    return die;
                }
            }
        }
        return null;
    }

    private void removeDice(List<DiceSides> diceToRemove){
        for(DiceSides diceSideToRemove : diceToRemove){
            for (int i = 0; i < 6; i++) {
                if (diceSet.get(i).getDiceSide() == diceSideToRemove) {
                    diceSet.remove(i);
                    break;
                }
            }
        }
    }



    private void checkResult(Player currentPlayer) throws InterruptedException {
        int score = 0;
        if(hasStraight(List.of(DiceSides.ONE,DiceSides.TWO,DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX))){
            currentPlayer.addScore(1500);
            slowText("Flush! 1500 pts!",true);
            return; //No point checking the rest if this is true;
        }
        else if(hasStraight(List.of(DiceSides.TWO,DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX))){
            score += 750;
            removeDice(List.of(DiceSides.TWO,DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE, DiceSides.SIX));
            slowText("Long Straight!! 750 pts!",true);
        }
        else if(hasStraight(List.of(DiceSides.ONE,DiceSides.TWO,DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE))){
            score += 500;
            removeDice(List.of(DiceSides.ONE,DiceSides.TWO,DiceSides.THREE, DiceSides.FOUR, DiceSides.FIVE));
            slowText("Short Straight!! 500 pts!",true);
        }
        else{
            Dice dice;
            for (int i = 6; i >= 3; i--) {
                int scoreMultiplier = 0;
                switch(i){
                    case 6 -> scoreMultiplier = 2*2*2;
                    case 5 -> scoreMultiplier = 2*2;
                    case 4 -> scoreMultiplier = 2;
                    case 3 -> scoreMultiplier = 1;
                }
                if((dice = hasThreeOrMoreInARow(i)) != null){
                    int baseMultiplier = dice.getDiceSide() == DiceSides.ONE ? 1000 : 100;
                    score += dice.getDiceSide().getValue()*baseMultiplier*scoreMultiplier;
                    slowText(i + " in a row! " + dice.getDiceSide().getValue()*baseMultiplier*scoreMultiplier + " pts!",true);
                    Dice finalDice1 = dice;
                    diceSet.removeIf((d) -> d.getDiceSide() == finalDice1.getDiceSide());
                    //Since you can have 2 three-in-a-rows, it checks one more time.
                    if(i == 3){
                        if((dice = hasThreeOrMoreInARow(i)) != null){
                            score += dice.getDiceSide().getValue()*baseMultiplier*scoreMultiplier;
                            slowText("Another 3 in a row! " + dice.getDiceSide().getValue()*baseMultiplier*scoreMultiplier + " pts!",true);
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
        for(Dice die : diceSet){
            if(die.getDiceSide() == DiceSides.ONE){
                amountOfOnes++;
                score+=100;
            }
            else if(die.getDiceSide() == DiceSides.FIVE){
                amountOfFives++;
                score+=50;
            }
        }
        //If you have more than 2 ones or fives, the above in-a-row functions will apply instead.
        switch(amountOfOnes){
            case 1 -> slowText("ONE! 100 pts!",true);
            case 2 -> slowText("2 ONES! 200 pts!",true);
        }
        switch(amountOfFives){
            case 1 -> slowText("FIVE! 50 pts!",true);
            case 2 -> slowText("2 FIVES! 100 pts!",true);
        }

        diceSet.clear();
        resetDiceSet();

        slowText(currentPlayer.getName() + " got " + score + " points!",true);
        currentPlayer.addScore(score);

    }

    private void resetDiceSet(){
        for (int i = 1; i <= 6; i++) {
            diceSet.add(new Dice());
        }
    }

    private int inputInt() throws InterruptedException {
        int choice;
        while(true) {
            slowText("Your selection: ",false);
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
        slowText("Rolling...",true);
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

    public boolean rethrow() throws InterruptedException {
        slowText("Choose which dice to rethrow (1-6). Type '0' when done.",true);

        List<Dice> diceToRethrow = new ArrayList<>();
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
            if (diceToRethrow.contains(dieToRethrow)){
                diceToRethrow.remove(dieToRethrow);
                slowText("Removed " + dieToRethrow + " to rethrow list",true);
            }
            else {
                diceToRethrow.add(dieToRethrow);
                slowText("Added " + dieToRethrow + " to rethrow list",true);
            }
        }
        slowText("Rethrow: ",false);
        for(Dice dieToRethrow : diceToRethrow){
            dieToRethrow.throwDice();
        }
        return true;
    }
}