public class Cherry extends Entity implements Edible {
    private boolean eaten = false;
    /**
     * Sets up a Cherry at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the Cherry
     * @param y: the y co-ordinate of the Cherry
     */
    public Cherry(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }

    /**
     * Check if the current Cherry is being eaten by a Player or not
     * @return the points rewarded to the player when eaten
     */
    @Override
    public int beingEaten() {
        this.eaten = true;
        return CHERRY_POINT;
    }

    /**
     * checks whether the current Cherry has been eaten or not
     * @return whether the Cherry has been eaten
     */
    @Override
    public boolean beenEaten() {
        return this.eaten;
    }
}
