import bagel.*;

import java.util.ArrayList;

public class Ghost extends Entity implements Edible, Movable {
    protected final Image frenzy = new Image("res/ghostFrenzy.png");
    protected boolean eaten = false;
    protected int frendziedTime = NOT_FRENZIED;
    protected double direction = RIGHT;
    protected double speed = GHOST_RED_SPEED;

    /**
     * Sets up a Ghost at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the Ghost
     * @param y: the y co-ordinate of the Ghost
     */
    public Ghost(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }

    /**
     * Spawns the Ghost at its original position
     */
    @Override
    public void spawn() {
        this.x = this.originX;
        this.y = this.originY;
        sprite.drawFromTopLeft(this.originX, this.originY);
        eaten = false;
    }

    /**
     * Renders the Ghost at its current position with the relevant sprite
     */
    @Override
    public void render() {
        if (frendziedTime <= MAX_FRENZY && !this.eaten) {
            frenzy.drawFromTopLeft(this.x, this.y);
        } else
            sprite.drawFromTopLeft(this.x, this.y);
    }

    /**
     * Gets the current speed of the Ghost
     * @return the current speed of the Ghost
     */
    @Override
    public double getSpeed(){
        if (frendziedTime <= MAX_FRENZY)
            return this.speed - FRENZY_NERF;
        return this.speed;
    }

    /**
     * Check if the current Ghost is being eaten by a Player or not
     * @return the points rewarded to the Player when eaten
     */
    @Override
    public int beingEaten() {
        this.eaten = true;
        return GHOST_POINT;
    }

    /**
     * checks whether the current Ghost has been eaten or not
     * @return whether the Ghost has been eaten
     */
    @Override
    public boolean beenEaten() {
        return this.eaten;
    }

    /**
     * Manages frenzy time, will be overwritten by its subclasses
     * to perform the Ghosts' action
     * @param walls: an array of all walls to check for collision
     */
    public void action(ArrayList<Wall> walls) {
        if (frendziedTime <= MAX_FRENZY)
            frendziedTime++;
    }

    /**
     * Starts frenzy mode
     */
    public void startScared() {
        frendziedTime = 1;
    }
}
