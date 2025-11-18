public enum DiceSides{
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

    public final int value;
    DiceSides(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
