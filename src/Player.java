import bagel.*;
import bagel.util.Point;
import java.lang.Math;

public class Player extends Entity implements Movable{
    private final int MOVE_AMOUNT = 3;
    private final int CHANGE_MARK = 15;
    private Image CLOSED_MOUTH = new Image("res/pac.png");
    private Image OPEN_MOUTH = new Image("res/pacOpen.png");
    private DrawOptions direction = new DrawOptions();
    private int frenzyTime = NOT_FRENZIED;

    /**
     * Set up a player at the provided location
     * @param x: the starting x coordinate of the player
     * @param y: the starting y coordinate of the player
     */
    public Player(double x, double y) {
        super(x, y);
        this.sprite = this.CLOSED_MOUTH;
        this.x = this.originX;
        this.y = this.originY;
        this.boundingBox = sprite.getBoundingBox();
        this.boundingBox.moveTo(new Point(this.originX, this.originY));
    }

    /**
     * Move the player in the direction provided
     * @param direction: the direction provided in radians
     */
    public void makeMove(double direction) {
        Movable.super.makeMove(this, this.x, this.y, this.getSpeed(), direction);
        this.direction.setRotation(direction);
    }

    /**
     * Spawn the current player at its original co-ordinates
     */
    @Override
    public void spawn() {
        direction.setRotation(RIGHT);
        super.spawn();
    }

    /**
     * Plays the animation for the player
     * @param time: the current tick in the game
     */
    public void idle(int time) {
        if (time % CHANGE_MARK == 0) {
            if (sprite == OPEN_MOUTH) {
                sprite = CLOSED_MOUTH;
                CLOSED_MOUTH.drawFromTopLeft(this.x, this.y, direction);
            } else if (sprite == CLOSED_MOUTH) {
                sprite = OPEN_MOUTH;
                OPEN_MOUTH.drawFromTopLeft(this.x, this.y, direction);
            }
        } else
            sprite.drawFromTopLeft(this.x, this.y, direction);

        if (frenzyTime <= MAX_FRENZY)
            frenzyTime++;
    }

    /**
     * Gets the speed of the player
     * @return the current speed of the player
     */
    @Override
    public double getSpeed() {
        if (frenzyTime <= MAX_FRENZY)
            return this.MOVE_AMOUNT + FRENZY_BUFF;
        return this.MOVE_AMOUNT;
    }

    /**
     * Starts frenzy mode for the player
     */
    public void startFrenzy() {
        frenzyTime = 1;
    }
}