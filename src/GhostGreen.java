import java.util.ArrayList;
import java.util.Random;
public class GhostGreen extends Ghost {
    public GhostGreen(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.speed = 4;
        Random random = new Random();
        int randInt = random.nextInt(2);
        if (randInt == 0) {
            this.direction = 1.5 * Math.PI;
        } else {
            this.direction = 0;
        }
    }
    @Override
    public void action(ArrayList<Wall> walls) {
        boolean blocked = false;
        for (Wall wall : walls) {
            if (this.blockedBy(this, wall, this.direction, this.getSpeed())) {
                blocked = true;
            }
        }
        if (blocked) {
            if (this.direction == 0) {
                this.direction = Math.PI;
            } else if (this.direction == Math.PI){
                this.direction = 0;
            } else if (this.direction == 1.5 * Math.PI) {
                this.direction = 0.5 * Math.PI;
            } else if (this.direction == 0.5 * Math.PI) {
                this.direction = 1.5 * Math.PI;
            }
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
