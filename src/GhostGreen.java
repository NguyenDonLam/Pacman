import java.util.ArrayList;
import java.util.Random;
public class GhostGreen extends Ghost {
    public GhostGreen(String imagePath, double x, double y) {
        super(imagePath, x, y);
        Random random = new Random();
        this.speed = 4;
        int randInt = random.nextInt(2);
        if (randInt == 0) {
            this.direction = DOWN;
        } else {
            this.direction = LEFT;
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
            if (this.direction == LEFT) {
                this.direction = RIGHT;
            } else if (this.direction == RIGHT){
                this.direction = LEFT;
            } else if (this.direction == UP) {
                this.direction = DOWN;
            } else if (this.direction == DOWN) {
                this.direction = UP;
            }
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
