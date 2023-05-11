public class Cherry extends Entity implements Edible {
    private final int POINT = 20;
    private boolean eatened = false;
    public Cherry(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }

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
