import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;
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
    private final int ALL_DOT = 121;
    private Player player;
    private Ghost[] ghost = new Ghost[4];
    private Wall[] wall = new Wall[145];
    private Dot[] dot = new Dot[ALL_DOT];
    private int lives = 3, points = 0, dotEaten = 0, idleTime = 0;
    private boolean start = false;


    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV(String fp) {
        String info;
        String[] processed;
        int currGhost, currWall, currDot;
        currGhost = currWall = currDot = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(fp))){
            while ((info = br.readLine()) != null) {
                processed = info.split(",");
                if (processed[0].equals("Player")) {
                    player = new Player("res/pac.png", "res/pacOpen.png",
                            Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                } else if (processed[0].equals("Ghost")) {
                    ghost[currGhost++] = new Ghost("res/ghostRed.png",
                            Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                } else if (processed[0].equals("Wall")) {
                    wall[currWall++] = new Wall("res/wall.png",
                            Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                } else if (processed[0].equals("Dot")) {
                    dot[currDot++] = new Dot("res/dot.png",
                            Integer.parseInt(processed[1]), Integer.parseInt(processed[2]));
                }
            }
        } catch (Exception e) {
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
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        boolean blocked;
        double playerFacing;
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        // Game start condition
        if (input.wasPressed(Keys.SPACE)) {
            start = true;
            readCSV("res/level0.csv");
            player.spawn();
        }

        // display starting screen
        if (!start) {
            BACKGROUND_TEXT.drawString("ShadowPac", 260, 250);
            INSTRUCTION_TEXT.drawString("PRESS SPACE TO START", 320, 440);
            INSTRUCTION_TEXT.drawString("USE ARROW KEYS TO MOVE", 305, 480);

            // Win condition
        } else if (dotEaten == ALL_DOT) {
            BACKGROUND_TEXT.drawString("WELL DONE!",
                    WINDOW_WIDTH/2 - BACKGROUND_TEXT.getWidth("WELL DONE!")/2, WINDOW_HEIGHT/2);

            // Lose condition
        } else if (lives <= 0) {
            BACKGROUND_TEXT.drawString("GAME OVER!",
                    WINDOW_WIDTH/2 - BACKGROUND_TEXT.getWidth("GAME OVER!")/2, WINDOW_HEIGHT/2);

            // Currently in a game
        } else {
            playerFacing = -1;
            blocked = false;

            // display points
            SCORE_FONT.drawString("SCORE " + points,25, 25);

            // display lives
            for (int i = 0; i < lives; i++) {
                life.drawFromTopLeft(900 + (i * 30), 10);
            }

            // animation
            player.idle(idleTime++);

            // check if eating a dot
            for (int i = 0; i < (dot.length - dotEaten); i++) {
                if (player.overlaps(dot[i])) {
                    for (int j = i; j < (dot.length - dotEaten - 1); j++) {
                        dot[j] = dot[j + 1];
                    }
                    dotEaten++;
                    points += 10;
                }
            }

            // touching a ghost?
            for (Ghost ghost : this.ghost) {
                if (player.overlaps(ghost)) {
                    player.spawn();
                    lives--;
                }
            }

            // Spawns relevant Entities
            for (Wall wall : this.wall) {
                wall.spawn();
            }
            for (Ghost ghost : this.ghost) {
                ghost.spawn();
            }
            for (int i = 0; i < (dot.length - dotEaten); i++) {
                dot[i].spawn();
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

            // check walls if the player is not standing still
            if (playerFacing != -1) {

                // touching a wall?
                for (Wall wall : this.wall) {
                    if (player.blockedBy(player, wall, playerFacing, player.getSpeed())) {
                        blocked = true;
                    }
                }
                if (!blocked) {
                    player.makeMove(playerFacing);
                }
            }
        }
    }
}
