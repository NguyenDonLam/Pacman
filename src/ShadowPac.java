import bagel.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * @author Nguyen Don Lam
 */
public class ShadowPac extends AbstractGame implements GameConstants {

    private Player player;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Cherry> cherries = new ArrayList<Cherry>();
    private Pellet pellet;
    private int lives = 3, points = 0, gameTick = 0, winTime = 0, frenzyTime = MAX_FRENZY;
    private int level = -1;


    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private boolean readCSV(String fp) {
        String info;
        String[] processed;
        try(BufferedReader br = new BufferedReader(new FileReader(fp))){
            while ((info = br.readLine()) != null) {
                processed = info.split(",");
                switch (processed[0]) {
                    case PLAYER:
                        player = new Player(Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                        break;
                    case WALL:
                        walls.add(new Wall(PATH_WALL,
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case DOT:
                        dots.add(new Dot(PATH_DOT,
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case GHOST:
                    case GHOST_RED:
                        ghosts.add(new GhostRed(PATH_GHOST_RED,
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case GHOST_GREEN:
                        ghosts.add(new GhostGreen(PATH_GHOST_GREEN,
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case GHOST_BLUE:
                        ghosts.add(new GhostBlue(PATH_GHOST_BLUE,
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case GHOST_PINK:
                        ghosts.add(new GhostPink(PATH_GHOST_PINK,
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case CHERRY:
                        cherries.add(new Cherry(PATH_CHERRY, Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case PELLET:
                        pellet = new Pellet(PATH_PELLET, Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                        break;

                }
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
        return true;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        double playerFacing;
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        // Game start condition
        if (input.wasPressed(Keys.SPACE)) {
            readCSV(String.format(PATH_LEVEL, ++level));
            player.spawn();
        }

        // display starting screen
        if (level < 0) {
            BACKGROUND_TEXT.drawString(TITLE, 260, 250);
            INSTRUCTION_TEXT.drawString(INSTRUCT_SPACE, 320, 440);
            INSTRUCTION_TEXT.drawString(INSTRUCT_KEYS, 305, 480);

            // win screen display
        } else if (winTime < MAX_WIN_DISPLAY) {
            BACKGROUND_TEXT.drawString(LEVEL_WIN,
                    WINDOW_WIDTH/2 - BACKGROUND_TEXT.getWidth(LEVEL_WIN)/2,
                    WINDOW_HEIGHT/2);
            winTime++;
            // lv0 win condition
        } else if ((points >= WIN_MARK_0 && level == 0) || input.wasPressed(Keys.W)) {
            this.reset();
            winTime = 1;
            readCSV(String.format(PATH_LEVEL, ++level));
            // win game condition
        } else if ((points >= WIN_MARK_1 && level == 1)) {
            BACKGROUND_TEXT.drawString(GAME_WIN,
                    WINDOW_WIDTH / 2 - BACKGROUND_TEXT.getWidth(GAME_WIN) / 2,
                    WINDOW_HEIGHT / 2);
            // Lose condition
        } else if (lives <= 0) {
            BACKGROUND_TEXT.drawString(GAME_LOSS,
                    WINDOW_WIDTH / 2 - BACKGROUND_TEXT.getWidth(GAME_LOSS) / 2,
                    WINDOW_HEIGHT / 2);

            // Currently in a game
        } else {
            playerFacing = -1;
            this.displayStat();

            // animation
            player.idle(gameTick++);

            // renders relevant Entities
            this.renderAll(level);

            // check if eating anything
            if (level == 1) {
                this.isEating(CHERRY);
                if (this.isEating(PELLET)) {
                    this.startFrenzy();
                }
                if (frenzyTime <= MAX_FRENZY) {
                    this.isEating(GHOST);
                }
            }
            this.isEating(DOT);

            // dying to a ghost?
            if (frenzyTime > MAX_FRENZY) {
                for (Ghost ghost : this.ghosts) {
                    if (player.overlaps(ghost)) {
                        player.spawn();
                        ghost.spawn();
                        lives--;
                    }
                }
            }

            // Player movements
            if (input.isDown(Keys.UP)) {
                playerFacing = UP;
            }
            if (input.isDown(Keys.LEFT)) {
                playerFacing = LEFT;
            }
            if (input.isDown(Keys.RIGHT)) {
                playerFacing = RIGHT;
            }
            if (input.isDown(Keys.DOWN)) {
                playerFacing = DOWN;
            }

            allAction(level, playerFacing);
            if (frenzyTime <= MAX_FRENZY) {
                frenzyTime++;
            }
        }
    }

    private void reset() {
        ghosts.clear();
        walls.clear();
        dots.clear();
        lives = 3;
        points = 0;
    }
    private boolean isEating(String item) {
        switch (item) {
            case CHERRY:
                for (Cherry cherry : this.cherries) {
                    if (player.overlaps(cherry) && !cherry.isEatened()) {
                        points += cherry.beingEaten();
                        return true;
                    }
                }
                break;
            case GHOST:
                for (Ghost ghost : this.ghosts) {
                    if (player.overlaps(ghost) && !ghost.isEatened()) {
                        points += ghost.beingEaten();
                        return true;
                    }
                }
                break;
            case DOT:
                for (Dot dot : this.dots) {
                    if (player.overlaps(dot) && !dot.isEatened()) {
                        points += dot.beingEaten();
                        return true;
                    }
                }
                break;
            case PELLET:
                if (player.overlaps(pellet) && !pellet.isEatened()) {
                    points += pellet.beingEaten();
                    return true;
                }
                break;
        }
        return false;
    }
    private void renderAll(int level) {
        for (Wall wall : this.walls) {
            wall.render();
        }
        for (Dot dot: dots) {
            if (!dot.isEatened()) {
                dot.render();
            }
        }
        if (level == 1) {
            for (Cherry cherry: cherries) {
                if (!cherry.isEatened()) {
                    cherry.render();
                }
            }
            if (!pellet.isEatened()) {
                pellet.render();
            }
        }
        for (Ghost ghost : this.ghosts) {
            if (!ghost.isEatened()) {
                ghost.render();
            }
            if (frenzyTime > MAX_FRENZY && ghost.isEatened()) {
                ghost.spawn();
            }
        }
    }


    public void allAction(int level, double playerFacing) {
        boolean blocked = false;
        for (Wall wall : walls) {
            if (player.blockedBy(player, wall, playerFacing, player.getSpeed())) {
                blocked = true;
            }
        }

        for (Ghost ghost : ghosts) {
            if (level > 0) {
                ghost.action(walls);
            }
        }

        if (!blocked && playerFacing != -1) {
            player.makeMove(playerFacing);
        }
    }
    private void startFrenzy() {
        frenzyTime = 0;
        for (Ghost ghost : ghosts) {
            ghost.startScared();
        }
        player.startFrenzy();
    }
    public void displayStat() {
        // display points
        SCORE_FONT.drawString(String.format(SCORE, points),25, 25);

        // display lives
        for (int i = 0; i < lives; i++) {
            life.drawFromTopLeft(900 + (i * 30), 10);
        }
    }
}
