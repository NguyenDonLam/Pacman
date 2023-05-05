public class Cherry implements Edible {
    private final int POINT = 20;
    private boolean eatened = false;

    @Override
    public int beingEaten() {
        this.eatened = true;
        return this.POINT;
    }

    @Override
    public boolean isEatened() {
        return this.eatened;
    }
}
