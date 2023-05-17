import java.util.ArrayList;

public class GhostBlue extends Ghost {
    /**
     * Sets up a GhostBlue at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the GhostBlue
     * @param y: the y co-ordinate of the GhostBlue
     */
    public GhostBlue(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.direction = DOWN;
        this.speed = GHOST_BLUE_SPEED;
    }

    /**
     * Manages the movements of the GhostBlue within the walls provided
     * @param walls: an array of all walls to check for collision
     */
    @Override
    public void action(ArrayList<Wall> walls) {
        boolean blocked = false;
        for (Wall wall : walls) {
            if (this.blockedBy(this, wall, this.direction, this.getSpeed()))
                blocked = true;
        }
        if (blocked) {
            if (this.direction == DOWN) {
                this.direction = UP;
            } else
                this.direction = DOWN;
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
