import java.util.Objects;
import java.util.Random;

public class Dice {
    private DiceSides diceSide;
    Random random = new Random();

    public Dice() {
        this.diceSide = DiceSides.ONE;
    }

    public Dice(DiceSides diceSide) {
        this.diceSide = diceSide;
    }

    // TODO: JAVADOC/COMMENTS!
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dice dice = (Dice) o;
        return diceSide == dice.diceSide;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(diceSide);
    }

    public DiceSides getDiceSide() {
        return diceSide;
    }
}