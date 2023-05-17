import bagel.util.Point;

public interface Movable {
    double RIGHT = 0;
    double LEFT = Math.PI;
    double DOWN = 0.5 * Math.PI;
    double UP = 1.5 * Math.PI;
    int RAND_DOWN = 0;
    int RAND_LEFT = 1;
    int RAND_RIGHT = 2;
    int MAX_FRENZY = 1000;
    int GHOST_RED_SPEED = 1;
    int GHOST_BLUE_SPEED = 2;
    int GHOST_GREEN_SPEED = 4;
    int GHOST_PINK_SPEED = 3;
    double FRENZY_NERF = 0.5;
    int FRENZY_BUFF = 1;
    int NOT_FRENZIED = MAX_FRENZY + 1;
    int GHOST_GREEN_DIRECTION = 2;
    int GHOST_PINK_DIRECTION = 4;

    /**
     * Move the provided Entity in the provided direction
     * @param self: the Entity to be moved
     * @param x: the current Entity's x co-ordinate
     * @param y: the current Entity's y co-ordinate
     * @param speed: the speed of the Entity
     * @param direction: the direction to be moved in
     */
    default void makeMove(Entity self, double x, double y, double speed, double direction) {

        //moving right
        if (direction == RIGHT) {
            self.setXY(x + speed, y);

            //moving down
        } else if (direction == DOWN) {
            self.setXY(x, y + speed);

            //moving left
        } else if (direction == LEFT) {
            self.setXY(x - speed, y);

            //moving up
        } else if (direction == UP)
            self.setXY(x, y - speed);
    }

    /**
     * Checks whether the provided Entity is blocked by the provided Wall
     * @param self: the Entity to be collision checked
     * @param wall: the Wall to be collision checked
     * @param direction: the direction the Entity is moving in
     * @param speed: the speed of the Entity
     * @return true or false depending on the Entity being blocked or not
     */
    default boolean blockedBy(Entity self, Wall wall, double direction, double speed) {
        //moving right
        if (direction == RIGHT) {
            self.setBoundingBox(self.getX() + speed, self.getY());
            //moving down
        } else if (direction == DOWN) {
            self.setBoundingBox(self.getX(), self.getY() + speed);
            //moving left
        } else if (direction == LEFT) {
            self.setBoundingBox(self.getX() - speed, self.getY());
            //moving up
        } else if (direction == UP)
            self.setBoundingBox(self.getX(), self.getY() - speed);

        return self.getBoundingBox().intersects(wall.getBoundingBox());
    }

    /**
     * Gets the current speed of the Entity
     * @return the current speed of the Entity
     */
    double getSpeed();
}
