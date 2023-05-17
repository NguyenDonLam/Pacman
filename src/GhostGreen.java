import java.util.ArrayList;
import java.util.Random;
public class GhostGreen extends Ghost {
    /**
     * Sets up a GhostGreen at the provided co-ordinates and the provided image file,
     * picks a random direction between down and left to travel
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the GhostGreen
     * @param y: the y co-ordinate of the GhostGreen
     */
    public GhostGreen(String imagePath, double x, double y) {
        super(imagePath, x, y);
        Random random = new Random();
        this.speed = GHOST_GREEN_SPEED;
        int randInt = random.nextInt(GHOST_GREEN_DIRECTION);
        if (randInt == RAND_DOWN) {
            this.direction = DOWN;
        } else
            this.direction = LEFT;
    }

    /**
     * Manages the movements of the GhostGreen within the walls provided
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
            } else if (this.direction == RIGHT){
                this.direction = LEFT;
            } else if (this.direction == UP) {
                this.direction = DOWN;
            } else if (this.direction == DOWN)
                this.direction = UP;
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
