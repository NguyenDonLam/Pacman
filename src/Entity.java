import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;
public class Entity {
    protected double originX, originY, x, y;
    protected Image sprite;
    protected Rectangle boundingBox;
    protected Entity(String imagePath, double x, double y) {
        sprite = new Image(imagePath);
        boundingBox = sprite.getBoundingBox();
        this.originX = x;
        this.originY = y;
        this.x = x;
        this.y = y;
        boundingBox.moveTo(new Point(this.originX, this.originY));
    }
    public Entity(double x, double y) {
        this.originX = x;
        this.originY = y;
    }

    /**
     * Draw the sprite at their origin
     */
    public void spawn() {
        this.x = this.originX;
        this.y = this.originY;
        sprite.drawFromTopLeft(this.originX, this.originY);
    }
    public void render() {
        sprite.drawFromTopLeft(this.x, this.y);
    }

    /**
     * Gets the currrent space in which the sprite occupy
     *
     * returns a Rectangle object at the sprite's location
     */
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }
    public void setBoundingBox(double x, double y) {
        boundingBox.moveTo(new Point(x, y));
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }

    /**
     * Checks whether or not the current entity overlaps the provided entity
     *
     * Takes in an entity that we check with
     *
     * Returns True or False depending on if it will overlap
     */
    public boolean overlaps(Entity entity) {
        boundingBox.moveTo(new Point(this.x, this.y));
        entity.setBoundingBox(entity.getX(), entity.getY());
        return boundingBox.intersects(entity.getBoundingBox());
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
