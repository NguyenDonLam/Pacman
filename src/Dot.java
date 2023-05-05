public class Dot extends Entity implements Edible {
    private boolean eatened = false;
    public Dot(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }
    @Override
    public int beingEaten() {
        this.eatened = true;
        return 10;
    }
    public boolean isEatened() {
        return this.eatened;
    }
}
