import bagel.util.Point;

public interface Movable {
    double RIGHT = 0;
    double LEFT = Math.PI;
    double DOWN = 0.5 * Math.PI;
    double UP = 1.5 * Math.PI;
    default void makeMove(Entity self, double x, double y, double dydx, double direction) {

        //moving right
        if (direction == RIGHT) {
            self.setXY(x + dydx, y);

            //moving down
        } else if (direction == DOWN) {
            self.setXY(x, y + dydx);

            //moving left
        } else if (direction == LEFT) {
            self.setXY(x - dydx, y);

            //moving up
        } else if (direction == UP) {
            self.setXY(x, y - dydx);
        }
    }

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
        } else if (direction == UP) {
            self.setBoundingBox(self.getX(), self.getY() - speed);
        }
        return self.getBoundingBox().intersects(wall.getBoundingBox());
    }

    double getSpeed();
}
