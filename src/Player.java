import bagel.*;
import bagel.util.Point;
import java.lang.Math;

public class Player extends Entity implements Movable{
    private final int MOVE_AMOUNT = 3;
    private final int CHANGE_MARK = 15;
    private Image closedMouth;
    private Image openMouth;
    private DrawOptions direction = new DrawOptions();
    private int changeMark = 15;
    public Player(String closedMouth, String openMouth, double x, double y) {
        super(x, y);
        this.closedMouth = new Image(closedMouth);
        this.openMouth = new Image(openMouth);
        this.sprite = this.closedMouth;
        this.x = this.originX;
        this.y = this.originY;
        this.boundingBox = sprite.getBoundingBox();
    }

    /**
     * Move the player in different directions, draw it on the Window
     *
     * Takes in a double in radian specifying the direction we are moving in
     */
    public void makeMove(double direction) {
        Movable.super.makeMove(this, this.x, this.y, MOVE_AMOUNT, direction);
        this.direction.setRotation(direction);
        System.out.println("x y is" + this.x + this.y);
    }
    @Override
    public void spawn() {
        direction.setRotation(0);
        this.x = this.originX;
        this.y = this.originY;
        sprite.drawFromTopLeft(this.originX, this.originY);
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
    @Override
    public int getSpeed() {
        return this.MOVE_AMOUNT;
    }
}