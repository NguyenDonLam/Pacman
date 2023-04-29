import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;
public class Entity {
    protected double x, y;
    protected Image sprite;
    protected Rectangle boundingBox;
    protected Entity(String imagePath, double x, double y) {
        sprite = new Image(imagePath);
        boundingBox = sprite.getBoundingBox();
        this.x = x;
        this.y = y;
    }
    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw the sprite at their origin
     */
    public void spawn() {
        sprite.drawFromTopLeft(this.x, this.y);
    }

    /**
     * Gets the currrent space in which the sprite occupy
     *
     * returns a Rectangle object at the sprite's location
     */
    public Rectangle getBoundingBox() {
        boundingBox.moveTo(new Point(this.x, this.y));
        return this.boundingBox;
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
        return boundingBox.intersects(entity.getBoundingBox());
    }
}
