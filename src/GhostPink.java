import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class GhostPink extends Ghost {
    private Random random = new Random();
    /**
     * Sets up a GhostPink at the provided co-ordinates and the provided image file,
     * picks a random direction to travel
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the GhostPink
     * @param y: the y co-ordinate of the GhostPink
     */
    public GhostPink(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.speed = GHOST_PINK_SPEED;
        int randInt = random.nextInt(GHOST_PINK_DIRECTION);
        if (randInt == RAND_DOWN) {
            this.direction = DOWN;
        } else if (randInt == RAND_LEFT) {
            this.direction = LEFT;
        } else if (randInt == RAND_RIGHT) {
            this.direction = RIGHT;
        } else {
            this.direction = UP;
        }
    }

    /**
     * Manages the movements of the GhostPink within the walls provided
     * @param walls: an array of all walls to check for collision
     */
    @Override
    public void action(ArrayList<Wall> walls) {
        HashMap<Double, Boolean> possibleRoute = new HashMap<Double, Boolean>();
        possibleRoute.put(LEFT, true);
        possibleRoute.put(DOWN, true);
        possibleRoute.put(RIGHT, true);
        possibleRoute.put(UP, true);
        boolean blocked = false;
        ArrayList<Double> trueKey = new ArrayList<Double>();

        for (Wall wall : walls) {
            if (this.blockedBy(this, wall, LEFT , this.getSpeed()))
                possibleRoute.put(LEFT, false);

            if (this.blockedBy(this, wall, DOWN, this.getSpeed()))
                possibleRoute.put(DOWN, false);

            if (this.blockedBy(this, wall, RIGHT , this.getSpeed()))
                possibleRoute.put(RIGHT, false);

            if (this.blockedBy(this, wall, UP , this.getSpeed()))
                possibleRoute.put(UP, false);

            if (this.blockedBy(this, wall, this.direction , this.getSpeed()))
                blocked = true;
        }

        for (Double direction : possibleRoute.keySet()) {
            if (possibleRoute.get(direction))
                trueKey.add(direction);
        }
        if (blocked)
            this.direction = trueKey.get(random.nextInt(trueKey.size()));

        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
