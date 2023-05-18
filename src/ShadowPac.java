import bagel.*;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
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
    private int lives = MAX_LIVES;
    private int points = 0;
    private int gameTick = 0;
    private int winTime = MAX_WIN_DISPLAY;
    private int frenzyTime = MAX_FRENZY;
    private int level = 0;
    private boolean start = false;


    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Reads the provided file for a level and store all relevant data
     * @param fp: the file path provided as a string
     */
    private void readCSV(String fp) {
        String info;
        String[] processed;
        int x, y;
        try(BufferedReader br = new BufferedReader(new FileReader(fp))){
            while ((info = br.readLine()) != null) {
                processed = info.split(",");
                x = Integer.parseInt(processed[1]);
                y = Integer.parseInt(processed[2]);
                switch (processed[0]) {
                    case PLAYER:
                        player = new Player(x, y);
                        break;
                    case WALL:
                        walls.add(new Wall(PATH_WALL, x, y));
                        break;
                    case DOT:
                        dots.add(new Dot(PATH_DOT, x, y));
                        break;
                    case GHOST:
                    case GHOST_RED:
                        ghosts.add(new GhostRed(PATH_GHOST_RED, x, y));
                        break;
                    case GHOST_GREEN:
                        ghosts.add(new GhostGreen(PATH_GHOST_GREEN, x, y));
                        break;
                    case GHOST_BLUE:
                        ghosts.add(new GhostBlue(PATH_GHOST_BLUE, x, y));
                        break;
                    case GHOST_PINK:
                        ghosts.add(new GhostPink(PATH_GHOST_PINK, x, y));
                        break;
                    case CHERRY:
                        cherries.add(new Cherry(PATH_CHERRY, x, y));
                        break;
                    case PELLET:
                        pellet = new Pellet(PATH_PELLET, x, y);
                        break;

                }
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * The method providing an update to the game state every tick
     * @param input: the item used to check for keyboard inputs
     */
    @Override
    protected void update(Input input) {
        double playerFacing;
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (input.wasPressed(Keys.ESCAPE))
            Window.close();
        // If currently in the screen break in between levels
        if (!start) {
            if (displayWinStart(input))
                readCSV(String.format(PATH_LEVEL, level));
            // Currently in a game
        } else {
            playerFacing = STANDING_STILL;
            // GAME RENDERING =================================================
            player.idle(gameTick++);
            this.renderAll(level);
            this.displayStat();

            // EATING CHECK ===================================================
            if (level == 1) {
                this.isEating(CHERRY);

                if (this.isEating(PELLET))
                    this.startFrenzy();

                if (frenzyTime <= MAX_FRENZY)
                    this.isEating(GHOST);
            }
            this.isEating(DOT);

            //  LOOSING LIVES CHECK ===========================================
            if (frenzyTime > MAX_FRENZY) {
                for (Ghost ghost : this.ghosts) {
                    if (player.overlaps(ghost)) {
                        player.spawn();
                        ghost.spawn();
                        lives--;
                    }
                }
            }

            // ENTITIES' ACTIONS ==============================================
            if (input.isDown(Keys.UP))
                playerFacing = UP;
            if (input.isDown(Keys.LEFT))
                playerFacing = LEFT;
            if (input.isDown(Keys.RIGHT))
                playerFacing = RIGHT;
            if (input.isDown(Keys.DOWN))
                playerFacing = DOWN;
            allAction(level, playerFacing);
            if (frenzyTime <= MAX_FRENZY)
                frenzyTime++;
            
            this.checkWinLoss();
        }
    }

    /**
     * reset every relevant data the game is currently storing in preparation for next level;
     */
    private void reset() {
        ghosts.clear();
        walls.clear();
        dots.clear();
        lives = MAX_LIVES;
        points = 0;
        level++;
        winTime = 0;
        start = false;
    }

    /**
     * Checks whether the player is currently eating any of the food type provided or not
     * @param item: the name of the food type as a string
     * @return whether the player is eating anything or not
     */
    private boolean isEating(String item) {
        switch (item) {
            case CHERRY:
                for (Cherry cherry : this.cherries) {
                    if (player.overlaps(cherry) && !cherry.beenEaten()) {
                        points += cherry.beingEaten();
                        return true;
                    }
                }
                break;
            case GHOST:
                for (Ghost ghost : this.ghosts) {
                    if (player.overlaps(ghost) && !ghost.beenEaten()) {
                        points += ghost.beingEaten();
                        return true;
                    }
                }
                break;
            case DOT:
                for (Dot dot : this.dots) {
                    if (player.overlaps(dot) && !dot.beenEaten()) {
                        points += dot.beingEaten();
                        return true;
                    }
                }
                break;
            case PELLET:
                if (player.overlaps(pellet) && !pellet.beenEaten()) {
                    points += pellet.beingEaten();
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Renders all relevant entities still in the game
     * @param level: the current level the player is in
     */
    private void renderAll(int level) {
        if (level > 0) {
            for (Cherry cherry: cherries) {
                if (!cherry.beenEaten())
                    cherry.render();
            }
            if (!pellet.beenEaten())
                pellet.render();
        }
        for (Wall wall : this.walls)
            wall.render();
        for (Dot dot: dots) {
            if (!dot.beenEaten())
                dot.render();
        }
        for (Ghost ghost : this.ghosts) {
            if (!ghost.beenEaten())
                ghost.render();
            if (frenzyTime > MAX_FRENZY && ghost.beenEaten())
                ghost.spawn();
        }
    }
    /**
     * Move all movable entities in the current game in their relevant directions
     * @param level: the current level the player is in
     * @param playerFacing: the direction in which the player is moving
     */
    private void allAction(int level, double playerFacing) {
        boolean blocked = false;
        // MOVE GHOSTS ========================================================
        for (Ghost ghost : ghosts) {
            if (level > 0)
                ghost.action(walls);
        }
        // MOVE PLAYER ========================================================
        if (playerFacing != STANDING_STILL) {
            // Making sure the player aren't blocked by a wall
            for (Wall wall : walls) {
                if (player.blockedBy(player, wall, playerFacing, player.getSpeed()))
                    blocked = true;
            }
            if (!blocked)
                player.makeMove(playerFacing);
        }


    }
    /**
     * Start frenzy mode for all ghosts, player and the game itself
     */
    private void startFrenzy() {
        frenzyTime = 0;
        for (Ghost ghost : ghosts)
            ghost.startScared();
        player.startFrenzy();
    }
    /**
     * Display the lives and points in the current game
     */
    private void displayStat() {
        // display points
        SCORE_FONT.drawString(String.format(SCORE, points),SCORE_X, SCORE_Y);

        // display lives
        for (int i = 0; i < lives; i++)
            life.drawFromTopLeft(LIFE_X + (i * LIFE_PADDING), LIFE_Y);
    }

    /**
     * Checks whether the game has been won lost,
     * reset all status for next level if won
     */
    private void checkWinLoss() {
        // Loss condition
        if (lives <= 0)
            start = false;
        // Win conditions
        if (level == 0) {
            if (points >= WIN_MARK_0)
                this.reset();
        } else {
            if (points >= WIN_MARK_1)
                this.reset();
        }
    }

    /**
     * Displays the relevant screen pre/post a level, detects if level is started
     * @param input: the item to identify any keyboard input pressed
     * @return whether the next level has been initiated by the player
     */
    private boolean displayWinStart(Input input) {
        // GAME INITIATION CHECK ==============================================
        if (input.wasPressed(Keys.SPACE)) {
            start = true;
            return true;
        }
        // GAME LOSS SCREEN ===================================================
        if (lives <= 0) {
            BACKGROUND_TEXT.drawString(GAME_LOSS,
                    WINDOW_WIDTH / 2.0 - BACKGROUND_TEXT.getWidth(GAME_LOSS) / 2.0,
                    WINDOW_HEIGHT / 2.0);

            // LEVEL WIN SCREEN ===============================================
        } else if (winTime < MAX_WIN_DISPLAY && level == 1) {
            BACKGROUND_TEXT.drawString(LEVEL_WIN,
                    WINDOW_WIDTH / 2.0 - BACKGROUND_TEXT.getWidth(LEVEL_WIN) / 2.0,
                    WINDOW_HEIGHT / 2.0);
            winTime++;

            // START SCREEN ===================================================
        } else if (level == 0) {
            BACKGROUND_TEXT.drawString(TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_TEXT.drawString(INSTRUCT_SPACE, FRST_INSTRUCT_X, FRST_INSTRUCT_Y);
            INSTRUCTION_TEXT.drawString(INSTRUCT_KEYS, FRST_INSTRUCT_X,
                                                    FRST_INSTRUCT_Y + PADDING);

            // NEXT LEVEL INSTRUCTION =========================================
        } else if (level == 1) {
            NEXT_INSTRUCT_TEXT.drawString(INSTRUCT_SPACE, SCND_INSTRUCT_X, SCND_INSTRUCT_Y);
            NEXT_INSTRUCT_TEXT.drawString(INSTRUCT_KEYS, SCND_INSTRUCT_X,
                                                      SCND_INSTRUCT_Y + PADDING);
            NEXT_INSTRUCT_TEXT.drawString(INSTRUCT_PELLET, SCND_INSTRUCT_X,
                                                        SCND_INSTRUCT_Y + 2 * PADDING);

            // GAME WIN SCREEN =================================================
        } else
            BACKGROUND_TEXT.drawString(GAME_WIN,
                    WINDOW_WIDTH / 2.0 - BACKGROUND_TEXT.getWidth(GAME_WIN) / 2.0,
                    WINDOW_HEIGHT / 2.0);
        return false;
    }
}
