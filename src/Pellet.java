public class Pellet extends Entity implements Edible {
    private final int POINT = 0;
    private boolean eatened = false;
    public Pellet(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }
    public boolean isEatened() {
        return this.eatened;
    }

    public int beingEaten() {
        this.eatened = true;
        return this.POINT;
    }
}
