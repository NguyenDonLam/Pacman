import java.util.ArrayList;

public class GhostRed extends Ghost {
    public GhostRed(String imagePath, double x, double y) {
        super(imagePath, x, y);
        this.direction = LEFT;
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
            } else {
                this.direction = LEFT;
            }
        }
        this.makeMove(this, this.x, this.y, this.getSpeed(), this.direction);
        super.action(walls);
    }
}
