public class Ghost extends Entity implements Edible, Movable {
    protected final int POINTS = 30;
    protected boolean eatened;
    protected boolean frienzied;
    public Ghost(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.frienzied = false;
        this.eatened = false;
    }
    public int getSpeed(){
        return 0;
    }

    public boolean isEatened() {
        return this.eatened;
    }

    public int beingEaten() {
        this.eatened = true;
        return this.POINTS;
    }

}
