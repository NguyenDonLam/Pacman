public interface Edible {
    int DOT_POINT = 10;
    int CHERRY_POINT = 20;
    int GHOST_POINT = 30;
    int PELLET_POINT = 0;

    /**
     * Checks whether the current Entity is being eaten or not
     * @return the points rewarded for eating it
     */
    int beingEaten();

    /**
     * checks whether the current Entity has been eaten or not
     * @return whether the Entity has been eaten or not
     */
    boolean beenEaten();
}
