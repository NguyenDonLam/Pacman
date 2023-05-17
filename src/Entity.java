import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;
public class Entity {
    protected double originX, originY, x, y;
    protected Image sprite;
    protected Rectangle boundingBox;

    /**
     * Sets up an Entity at the provided co-ordinates and the provided image file
     * @param imagePath: the file path to the provided sprite
     * @param x: the x co-ordinate of the Entity
     * @param y: the y co-ordinate of the Entity
     */
    protected Entity(String imagePath, double x, double y) {
        sprite = new Image(imagePath);
        boundingBox = sprite.getBoundingBox();
        this.originX = x;
        this.originY = y;
        this.x = x;
        this.y = y;
        boundingBox.moveTo(new Point(this.originX, this.originY));
    }

    /**
     * Sets up an Entity at the provided co-ordinates
     * @param x: the x co-ordinate of the Entity
     * @param y: the y co-ordinate of the Entity
     */
    public Entity(double x, double y) {
        this.originX = x;
        this.originY = y;
    }

    /**
     * Draws the sprite at its origin co-ordinates
     */
    public void spawn() {
        this.x = this.originX;
        this.y = this.originY;
        sprite.drawFromTopLeft(this.originX, this.originY);
    }

    /**
     * Renders the sprite at its current co-ordinate
     */
    public void render() {
        sprite.drawFromTopLeft(this.x, this.y);
    }

    /**
     * Get the current bounding box of the sprite
     * @return the bounding box of the sprite
     */
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * Set the bounding box at the co-ordinate provided
     * @param x: the x co-ordinate for the bounding box
     * @param y: the y co-ordinate for the bounding box
     */
    public void setBoundingBox(double x, double y) {
        boundingBox.moveTo(new Point(x, y));
    }

    /**
     * Get the current x co-ordinate of the Entity
     * @return the current x co-ordinate of the Entity
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the current y co-ordinate of the Entity
     * @return the current y co-ordinate of the Entity
     */
    public double getY() {
        return this.y;
    }

    /**
     * Checks whether the current Entity is overlapping another Entity or not
     * @param entity: the other Entity to check with
     * @return true or false depending on the entities overlapping or not
     */
    public boolean overlaps(Entity entity) {
        boundingBox.moveTo(new Point(this.x, this.y));
        entity.setBoundingBox(entity.getX(), entity.getY());
        return boundingBox.intersects(entity.getBoundingBox());
    }

    /**
     * Sets the co-ordinates for the current Entity
     * @param x: the new x co-ordinate
     * @param y: the new y co-ordinate
     */
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
