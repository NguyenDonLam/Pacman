import bagel.Font;
import bagel.Image;

public interface GameConstants {
    String PLAYER = "Player";
    String WALL = "Wall";
    String DOT = "Dot";
    String CHERRY = "Cherry";
    String PELLET = "Pellet";
    String GHOST = "Ghost";
    String GHOST_RED = "GhostRed";
    String GHOST_BLUE = "GhostBlue";
    String GHOST_GREEN = "GhostGreen";
    String GHOST_PINK = "GhostPink";
    String PATH_WALL = "res/Wall.png";
    String PATH_DOT = "res/Dot.png";
    String PATH_CHERRY = "res/Cherry.png";
    String PATH_PELLET = "res/Pellet.png";
    String PATH_GHOST_RED = "res/ghostRed.png";
    String PATH_GHOST_BLUE = "res/ghostBlue.png";
    String PATH_GHOST_GREEN = "res/ghostGreen.png";
    String PATH_GHOST_PINK = "res/ghostPink.png";
    String TITLE = "ShadowPac";
    String INSTRUCT_SPACE = "PRESS SPACE TO START";
    String INSTRUCT_KEYS = "USE ARROW KEYS TO MOVE";
    String LEVEL_WIN = "LEVEL COMPLETE!";
    String GAME_WIN = "WELL DONE!";
    String GAME_LOSS = "GAME OVER!";
    String SCORE = "SCORE %d";
    String PATH_LEVEL = "res/level%d.csv";
    double UP = 1.5 * Math.PI;
    double LEFT = Math.PI;
    double DOWN = 0.5 * Math.PI;
    double RIGHT = 0;
    int MAX_FRENZY = 1000;
    int MAX_WIN_DISPLAY = 300;
    int WIN_MARK_0 = 1210;
    int WIN_MARK_1 = 800;







    int WINDOW_WIDTH = 1024;
    int WINDOW_HEIGHT = 768;
    String GAME_TITLE = "SHADOW PAC";
    Image BACKGROUND_IMAGE = new Image("res/background0.png");
    Font BACKGROUND_TEXT = new Font("res/FSO8BITR.TTF", 64);
    Font INSTRUCTION_TEXT = new Font("res/FSO8BITR.TTF", 24);
    Font SCORE_FONT = new Font("res/FSO8BITR.TTF", 20);
    Image life = new Image("res/heart.png");
}
