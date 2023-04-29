import bagel.*;
import bagel.util.Point;
import java.lang.Math;

public class Player extends Entity{
    private final int MOVE_AMOUNT = 3;
    private final int CHANGE_MARK = 15;
    private double originalX, originalY;
    private Image closedMouth;
    private Image openMouth;
    private DrawOptions direction = new DrawOptions();
    private int changeMark = 15;
    public Player(String closedMouth, String openMouth, double x, double y) {
        super(x, y);
        this.closedMouth = new Image(closedMouth);
        this.openMouth = new Image(openMouth);
        this.sprite = this.closedMouth;
        this.originalX = x;
        this.originalY = y;
        this.boundingBox = sprite.getBoundingBox();
    }

    /**
     * Move the player in different directions, draw it on the Window
     *
     * Takes in a double in radian specifying the direction we are moving in
     */
    public void makeMove(double direction) {
        //moving right
        if (direction == 0) {
            this.x += MOVE_AMOUNT;
            //moving down
        } else if (direction == 0.5 * Math.PI) {
            this.y += MOVE_AMOUNT;
            //moving left
        } else if (direction == Math.PI) {
            this.x -= MOVE_AMOUNT;
            //moving up
        } else if (direction == 1.5 * Math.PI) {
            this.y -= MOVE_AMOUNT;
        }
        this.direction.setRotation(direction);
    }
    @Override
    public void spawn() {
        direction.setRotation(0);
        this.x = originalX;
        this.y = originalY;
        sprite.drawFromTopLeft(this.originalX, this.originalY);
    }

    /**
     * Do the animation for Pacman
     * Takes in the current time and the point to change animation
     */
    public void idle(int time) {
        if (time % CHANGE_MARK == 0) {
            if (sprite == openMouth) {
                sprite = closedMouth;
                closedMouth.drawFromTopLeft(this.x, this.y, direction);
            } else if (sprite == closedMouth) {
                sprite = openMouth;
                openMouth.drawFromTopLeft(this.x, this.y, direction);
            }
        } else {
            sprite.drawFromTopLeft(this.x, this.y, direction);
        }
    }

    /**
     * Check if the current is blocked by the provided wall in the provided direction
     *
     * Takes in a wall and the direction moving in to check
     * Return True if blocked otherwise False
     */
    public boolean blockedBy(Wall wall, double direction) {
        //moving right
        if (direction == 0) {
            boundingBox.moveTo(new Point(this.x + 3, this.y));
        //moving down
        } else if (direction == 0.5 * Math.PI) {
            boundingBox.moveTo(new Point(this.x, this.y + 3));
        //moving left
        } else if (direction == Math.PI) {
            boundingBox.moveTo(new Point(this.x - 3, this.y));
        //moving up
        } else if (direction == 1.5 * Math.PI) {
            boundingBox.moveTo(new Point(this.x, this.y - 3));
        }
        return boundingBox.intersects(wall.getBoundingBox());
    }
}