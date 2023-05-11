import bagel.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * @author Nguyen Don Lam
 */
public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final Font BACKGROUND_TEXT = new Font("res/FSO8BITR.TTF", 64);
    private final Font INSTRUCTION_TEXT = new Font("res/FSO8BITR.TTF", 24);
    private final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", 20);
    private final Image life = new Image("res/heart.png");
    private final int WIN_MARK0 = 1210, WIN_MARK1 = 800;
    private Player player;
    private HashMap<Ghost, Boolean> ghosts = new HashMap<Ghost, Boolean>();
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Cherry> cherries = new ArrayList<Cherry>();
    private Pellet pellet;
    private int lives = 3, points = 0, gameTick = 0, winTime = 0, frenzyTime = 0;
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
                    case "Player":
                        player = new Player("res/pac.png", "res/pacOpen.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                        break;
                    case "Ghost":
                        ghosts.put(new GhostRed("res/ghostRed.png", Integer.parseInt(processed[1]),
                                                                             Integer.parseInt(processed[2])), false);
                        break;
                    case "Wall":
                        walls.add(new Wall("res/wall.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case "Dot":
                        dots.add(new Dot("res/dot.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case "GhostRed":
                        ghosts.put(new GhostRed("res/ghostRed.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])), false);
                        break;
                    case "GhostGreen":
                        ghosts.put(new GhostGreen("res/ghostGreen.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])), false);
                        break;
                    case "GhostBlue":
                        ghosts.put(new GhostBlue("res/ghostBlue.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])), false);
                        break;
                    case "GhostPink":
                        ghosts.put(new GhostPink("res/ghostPink.png",
                                Integer.parseInt(processed[1]), Integer.parseInt(processed[2])), false);
                        break;
                    case "Cherry":
                        cherries.add(new Cherry("res/cherry.png", Integer.parseInt(processed[1]), Integer.parseInt(processed[2])));
                        break;
                    case "Pellet":
                        pellet = new Pellet("res/pellet.png", Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                        break;

                }
            }
        } catch (FileNotFoundException e) {
            return false;
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
            readCSV(String.format("res/level%d.csv", ++level));
            player.spawn();
        }

        // display starting screen
        if (level < 0) {
            BACKGROUND_TEXT.drawString("ShadowPac", 260, 250);
            INSTRUCTION_TEXT.drawString("PRESS SPACE TO START", 320, 440);
            INSTRUCTION_TEXT.drawString("USE ARROW KEYS TO MOVE", 305, 480);

            // lv0 win condition
        } else if (winTime % 20 != 0) {
            BACKGROUND_TEXT.drawString("TEST TEXT!",
                    WINDOW_WIDTH/2 - BACKGROUND_TEXT.getWidth("WELL DONE!")/2, WINDOW_HEIGHT/2);
            winTime++;
        } else if ((points == WIN_MARK0 && level == 0) || input.wasPressed(Keys.W)) {
            BACKGROUND_TEXT.drawString("WELL DONE!",
                    WINDOW_WIDTH/2 - BACKGROUND_TEXT.getWidth("WELL DONE!")/2, WINDOW_HEIGHT/2);
            this.reset();
            winTime = 1;
            readCSV(String.format("res/level%d.csv", ++level));
            // Lose condition
        } else if (lives <= 0) {
            BACKGROUND_TEXT.drawString("GAME OVER!",
                    WINDOW_WIDTH/2 - BACKGROUND_TEXT.getWidth("GAME OVER!")/2, WINDOW_HEIGHT/2);

            // Currently in a game
        } else {
            playerFacing = -1;

            // display points
            SCORE_FONT.drawString("SCORE " + points,25, 25);

            // display lives
            for (int i = 0; i < lives; i++) {
                life.drawFromTopLeft(900 + (i * 30), 10);
            }

            // animation
            player.idle(gameTick++);

            // renders relevant Entities
            this.renderAll(level);

            // check if eating anything
            if (level == 1) {
                this.isEating("Cherry");
                if (this.isEating("Pellet")) {
                    this.startFrenzy();
                }
                if (frenzyTime % 1000 != 0) {
                    this.isEating("Ghost");
                }
            }
            this.isEating("Dot");

            // touching a ghost?
            if (frenzyTime % 1000 == 0) {
                for (Ghost ghost : this.ghosts.keySet()) {
                    if (player.overlaps(ghost)) {
                        player.spawn();
                        lives--;
                    }
                }
            }

            // Player movements
            if (input.isDown(Keys.UP)) {
                playerFacing = 1.5 * Math.PI;
            }
            if (input.isDown(Keys.LEFT)) {
                playerFacing = Math.PI;
            }
            if (input.isDown(Keys.RIGHT)) {
                playerFacing = 0;
            }
            if (input.isDown(Keys.DOWN)) {
                playerFacing = 0.5 * Math.PI;
            }

            allAction(level, playerFacing);
            if (frenzyTime % 1000 != 0) {
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
            case "Cherry":
                for (Cherry cherry : this.cherries) {
                    if (player.overlaps(cherry) && !cherry.isEatened()) {
                        points += cherry.beingEaten();
                        return true;
                    }
                }
                break;
            case "Ghost":
                for (Ghost ghost : this.ghosts.keySet()) {
                    if (player.overlaps(ghost) && !ghost.isEatened()) {
                        points += ghost.beingEaten();
                        return true;
                    }
                }
                break;
            case "Dot":
                for (Dot dot : this.dots) {
                    if (player.overlaps(dot) && !dot.isEatened()) {
                        points += dot.beingEaten();
                        return true;
                    }
                }
                break;
            case "Pellet":
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
        for (Ghost ghost : this.ghosts.keySet()) {
            if (!ghost.isEatened()) {
                ghost.render();
            }
            if (frenzyTime % 1000 == 0 && ghost.isEatened()) {
                ghost.spawn();
            }
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
    }


    public void allAction(int level, double playerFacing) {
        boolean blocked = false;
        for (Wall wall : walls) {
            if (player.blockedBy(player, wall, playerFacing, player.getSpeed())) {
                blocked = true;
            }
        }

        for (Ghost ghost : ghosts.keySet()) {
            if (level > 0) {
                ghost.action(walls);
            }
        }

        if (!blocked && playerFacing != -1) {
            player.makeMove(playerFacing);
        }
    }
    private void startFrenzy() {
        frenzyTime = 1;
        for (Ghost ghost : ghosts.keySet()) {
            ghost.startScared();
        }
        player.startFrenzy();
    }
}
