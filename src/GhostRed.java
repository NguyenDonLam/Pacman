import java.util.ArrayList;

public class GhostRed extends Ghost {
    /**
     * Sets up a GhostRed at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the GhostRed
     * @param y: the y co-ordinate of the GhostRed
     */
    public GhostRed(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.direction = LEFT;
    }

    /**
     * Manages the movements of the GhostRed within the walls provided
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
            if (this.direction == LEFT) {
                this.direction = RIGHT;
            } else
                this.direction = LEFT;
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
