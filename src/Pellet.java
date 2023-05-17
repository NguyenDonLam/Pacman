public class Pellet extends Entity implements Edible {
    private boolean eaten = false;

    /**
     * Sets up a Pellet at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the Pellet
     * @param y: the y co-ordinate of the Pellet
     */
    public Pellet(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }

    /**
     * Check if the current Pellet is being eaten by a Player or not
     * @return the points rewarded to the player when eaten
     */
    @Override
    public int beingEaten() {
        this.eaten = true;
        return PELLET_POINT;
    }

    /**
     * checks whether the current Pellet has been eaten or not
     * @return whether the Pellet has been eaten
     */
    @Override
    public boolean beenEaten() {
        return this.eaten;
    }
}
