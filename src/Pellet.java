public class Pellet implements Edible {
    private final int POINT = 0;
    private boolean eatened = false;
    public boolean isEatened() {
        return this.eatened;
    }

    public int beingEaten() {
        this.eatened = true;
        return this.POINT;
    }
}
