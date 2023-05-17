public class Dot extends Entity implements Edible {
    private boolean eaten = false;

    /**
     * Sets up a Dot at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the Dot
     * @param y: the y co-ordinate of the Dot
     */
    public Dot(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }

    /**
     * Check if the current Dot is being eaten by a Player or not
     * @return the points rewarded to the player when eaten
     */
    @Override
    public int beingEaten() {
        this.eaten = true;
        return DOT_POINT;
    }
    /**
     * checks whether the current Dot has been eaten or not
     * @return whether the Dot has been eaten
     */
    @Override
    public boolean beenEaten() {
        return this.eaten;
    }
}
