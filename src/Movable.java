import bagel.util.Point;

public interface Movable {
    default void makeMove(Entity self, double x, double y, double dydx, double direction) {

        //moving right
        if (direction == 0) {
            self.setXY(x + dydx, y);

            //moving down
        } else if (direction == 0.5 * Math.PI) {
            self.setXY(x, y + dydx);

            //moving left
        } else if (direction == Math.PI) {
            self.setXY(x - dydx, y);

            //moving up
        } else if (direction == 1.5 * Math.PI) {
            self.setXY(x, y - dydx);
        }
    }

    default boolean blockedBy(Entity self, Wall wall, double direction, double speed) {
        //moving right
        if (direction == 0) {
            self.setBoundingBox(self.getX() + speed, self.getY());
            //moving down
        } else if (direction == 0.5 * Math.PI) {
            self.setBoundingBox(self.getX(), self.getY() + speed);
            //moving left
        } else if (direction == Math.PI) {
            self.setBoundingBox(self.getX() - speed, self.getY());
            //moving up
        } else if (direction == 1.5 * Math.PI) {
            self.setBoundingBox(self.getX(), self.getY() - speed);
        }
        return self.getBoundingBox().intersects(wall.getBoundingBox());
    }

    double getSpeed();
}
