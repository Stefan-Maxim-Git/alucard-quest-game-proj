package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class GameUI {
    ContextSettings cs;

    BufferedImage[] transformBar = new BufferedImage[4];
    BufferedImage[] healthBar = new BufferedImage[4];
    BufferedImage head;

    // HEALTH BAR AND MOON BAR PARAMETERS
    int width = 258;
    int height = 64;
    public final int topLeftX = 5;
    public final int topLeftY = 5;
    public final int bottomRightX = 740;
    public final int bottomRightY = 640;
    public Rectangle barArea = new Rectangle(bottomRightX, bottomRightY, width, height);

    // TITLE SCREEN
    public BufferedImage background;
    public BufferedImage title;
    public BufferedImage newGame;
    public BufferedImage loadGame;
    public BufferedImage exitGame;

    // PAUSE SCREEM

    public BufferedImage pause;
    public BufferedImage resume;
    public BufferedImage save;
    public BufferedImage nextLvlScreen;
    public BufferedImage nextLvl;
    public BufferedImage gameOver;

    // OTHER PARAMETERS HERE
    ToolBox tools = new ToolBox();
    Graphics2D g2d;
    Font font;

    public int optionSelected = 1;


    public GameUI(ContextSettings cs) {
        this.cs = cs;
        font = new Font("Arial", Font.PLAIN, 60);
        loadAssets();
    }

    public void loadAssets() {
            transformBar[0] = loadGUIComponent(0, "Moon_Bar", 258, 64);
            transformBar[1] = loadGUIComponent(1, "Moon_Bar", 258, 64);
            transformBar[2] = loadGUIComponent(2, "Moon_Bar", 258, 64);
            transformBar[3] = loadGUIComponent(3, "Moon_Bar", 258, 64);

            healthBar[0] = loadGUIComponent(0, "Health_Bar", 258, 64);
            healthBar[1] = loadGUIComponent(1, "Health_Bar", 258, 64);
            healthBar[2] = loadGUIComponent(2, "Health_Bar", 258, 64);
            healthBar[3] = loadGUIComponent(3, "Health_Bar", 258, 64);

            title = loadGUIComponent("TITLE_1", cs.screenWidth, cs.screenHeight);
            background = loadGUIComponent("NEW_MENU_1", cs.screenWidth, cs.screenHeight);
            exitGame = loadGUIComponent("EXIT_BUTTON_1", 64*4, 48*4);
            loadGame = loadGUIComponent("LOAD_BUTTON_1", 64*4, 48*4);
            newGame = loadGUIComponent("NEW_GAME_BUTTON_1", 64*4, 48*4);

            head = loadGUIComponent("ZOMBIE_HEAD_1", 32*2, 48*2);

            pause = loadGUIComponent("PAUSE_SCREEN_1", cs.screenWidth, cs.screenHeight);
            resume = loadGUIComponent("RESUME_BUTTON_1", 64*4, 48*4);
            save = loadGUIComponent("SAVE_BUTTON_1", 64*4, 48*4);
            nextLvl = loadGUIComponent("NEXT_LEVEL_BUTTON_1", 64*4, 48*4);

            nextLvlScreen = loadGUIComponent("LEVEL_COMPLETE_1", cs.screenWidth, cs.screenHeight);
            gameOver = loadGUIComponent("GAME_OVER_1", cs.screenWidth, cs.screenHeight);
    }

    // HealthBar and Moonbar: width = 258, height = 64
    public BufferedImage loadGUIComponent(int index, String imageName, int width, int height) {
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read (new FileInputStream("res/GUI/"+ imageName+ "_Stage_" + (index+1) + ".png"));
            scaledImage = tools.scaleImage(scaledImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledImage;
    }

    public BufferedImage loadGUIComponent(String imageName, int width, int height) {
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read (new FileInputStream("res/GUI/"+ imageName + ".png"));
            scaledImage = tools.scaleImage(scaledImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledImage;
    }

    public void draw(Graphics2D g2d) {

        this.g2d = g2d;
        
        if (cs.gameState == cs.playState) {
            // Draw healthBar, moonBar
            drawGUI();
        }
        
        if (cs.gameState == cs.titleState ) {
            // Draw title screen
            drawTitleScreen();
        }
        
        if (cs.gameState == cs.pauseState && cs.mobCount[cs.currentLevel] == 0) {
            // Draw next level screen
            drawNextLevelScreen();
        } else if (cs.gameState == cs.pauseState) drawPauseScreen();
    }

    private void drawNextLevelScreen() {
        g2d.drawImage(nextLvlScreen, 0, 0, null);

        int x = cs.screenWidth / 2 - 32*4;
        int y = cs.screenHeight / 2 - 24*4 + 50;

        if (optionSelected == 1) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(nextLvl, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        y = y + 48 + 30;
        if (optionSelected == 2) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(exitGame, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawPauseScreen() {
        g2d.drawImage(pause, 0, 0, null);

        int x = cs.screenWidth / 2 - 32*4;
        int y = cs.screenHeight / 2 - 24*4 + 50;

        if (optionSelected == 1) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(resume, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        y = y + 48 + 30;
        if (optionSelected == 2) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(save, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        y = y + 48 + 30;
        if (optionSelected == 3) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(exitGame, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    private void drawTitleScreen() {
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(title, 0, 0, null);

        // For buttons, calculations:
            // button width = 64*3  pixels, button height = 48*3 pixels
            // to draw buttons centered at the screen:
            // x = screen width / 2 - button width / 2;
            // y = screen height / 2 + button height / 2 + difference depending on positions

        // NEW GAME BUTTON:
        int x = cs.screenWidth / 2 - 32*4;
        int y = cs.screenHeight / 2 - 24*4 + 50;

        if (optionSelected == 1) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(newGame, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // LOAD GAME BUTTON:
        y = y + 48 + 30;
        if (optionSelected == 2) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(loadGame, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // EXIT BUTTON:
        y = y + 48 + 30;
        if (optionSelected == 3) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2d.drawImage(exitGame, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawGUI(){
        BufferedImage imgMoonBar;
        BufferedImage imgHealthBar;

        imgMoonBar = transformBar[cs.player.MoonStage];
        imgHealthBar = healthBar[cs.player.life];

        int centerX = cs.player.x + cs.player.width/2;
        int centerY = cs.player.y + cs.player.height/2;

        // if player intersects bar area, change to opposite corner
        // bars are initially in bottom right, when intersecting change to top left
        if (centerX > barArea.x && centerY > barArea.y) {
            g2d.drawImage(imgMoonBar, topLeftX, topLeftY, null);
            g2d.drawImage(imgHealthBar, topLeftX, topLeftY + height, null);
        } else {
            g2d.drawImage(imgMoonBar, bottomRightX, bottomRightY, null);
            g2d.drawImage(imgHealthBar, bottomRightX, bottomRightY + height, null);
        }

        g2d.drawImage(head, 870, 20, null);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(""+ cs.mobCount[cs.currentLevel], 930, 80);

    }
}

