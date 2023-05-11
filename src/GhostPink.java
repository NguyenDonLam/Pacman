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
            this.direction = 0;
        } else if (randInt == 1) {
            this.direction = Math.PI;
        } else if (randInt == 2) {
            this.direction = 0.5 * Math.PI;
        } else {
            this.direction = 1.5 * Math.PI;
        }
    }

    @Override
    public void action(ArrayList<Wall> walls) {
        HashMap<Double, Boolean> possibleRoute = new HashMap<Double, Boolean>();
        possibleRoute.put(0.0, true);
        possibleRoute.put(0.5*Math.PI, true);
        possibleRoute.put(Math.PI, true);
        possibleRoute.put(1.5*Math.PI, true);
        boolean blocked = false;
        ArrayList<Double> trueKey = new ArrayList<Double>();

        for (Wall wall : walls) {
            if (this.blockedBy(this, wall, 0 , this.getSpeed())) {
                possibleRoute.put(0.0, false);
            }
            if (this.blockedBy(this, wall, 0.5 * Math.PI, this.getSpeed())) {
                possibleRoute.put(0.5 * Math.PI, false);
            }
            if (this.blockedBy(this, wall, Math.PI , this.getSpeed())) {
                possibleRoute.put(Math.PI, false);
            }
            if (this.blockedBy(this, wall, 1.5 * Math.PI , this.getSpeed())) {
                possibleRoute.put(1.5 * Math.PI, false);
            }
            if (this.blockedBy(this, wall, this.direction , this.getSpeed())) {
                blocked = true;
            }
        }
        System.out.println(possibleRoute);

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
