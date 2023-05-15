import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class GhostPink extends Ghost {
    Random random = new Random();
    public GhostPink(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.speed = 3;
        int randInt = random.nextInt(4);
        if (randInt == 0) {
            this.direction = LEFT;
        } else if (randInt == 1) {
            this.direction = RIGHT;
        } else if (randInt == 2) {
            this.direction = DOWN;
        } else {
            this.direction = UP;
        }
    }

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
            if (this.blockedBy(this, wall, LEFT , this.getSpeed())) {
                possibleRoute.put(LEFT, false);
            }
            if (this.blockedBy(this, wall, DOWN, this.getSpeed())) {
                possibleRoute.put(DOWN, false);
            }
            if (this.blockedBy(this, wall, RIGHT , this.getSpeed())) {
                possibleRoute.put(RIGHT, false);
            }
            if (this.blockedBy(this, wall, UP , this.getSpeed())) {
                possibleRoute.put(UP, false);
            }
            if (this.blockedBy(this, wall, this.direction , this.getSpeed())) {
                blocked = true;
            }
        }

        for (Double direction : possibleRoute.keySet()) {
            if (possibleRoute.get(direction)) {
                trueKey.add(direction);
            }
        }
        if (blocked) {
            this.direction = trueKey.get(random.nextInt(trueKey.size()));
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
