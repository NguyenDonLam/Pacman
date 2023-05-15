import java.util.ArrayList;

public class GhostBlue extends Ghost {
    public GhostBlue(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.direction = DOWN;
        this.speed = 2;
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
            if (this.direction == DOWN) {
                this.direction = UP;
            } else {
                this.direction = DOWN;
            }
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
