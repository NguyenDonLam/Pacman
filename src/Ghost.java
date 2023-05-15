import bagel.*;

import java.util.ArrayList;

public class Ghost extends Entity implements Edible, Movable {
    protected final int POINTS = 30;
    protected final Image frenzy = new Image("res/ghostFrenzy.png");
    protected boolean eatened = false;
    protected int frendziedTime = 0;
    protected double direction = RIGHT;
    protected double speed = 1;

    public Ghost(String imagePath, double x, double y) {
        super(imagePath, x, y);
    }
    public void spawn() {
        this.x = this.originX;
        this.y = this.originY;
        sprite.drawFromTopLeft(this.originX, this.originY);
        eatened = false;
    }
    public void render() {
        if (frendziedTime % 1000 != 0 && !this.eatened) {
            frenzy.drawFromTopLeft(this.x, this.y);
        } else {
            sprite.drawFromTopLeft(this.x, this.y);
        }
    }
    public double getSpeed(){
        if (frendziedTime % 1000 != 0)
            return this.speed - 0.5;
        return this.speed;
    }

    public boolean isEatened() {
        return this.eatened;
    }

    public int beingEaten() {
        this.eatened = true;
        return this.POINTS;
    }

    public void action(ArrayList<Wall> walls) {
        if (frendziedTime % 1000 != 0)
            frendziedTime++;
    }
    public void startScared() {
        frendziedTime = 1;
    }

}
