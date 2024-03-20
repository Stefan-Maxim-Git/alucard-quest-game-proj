package main;

import Entity.*;
import tile.MapBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ContextSettings extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int baseSize = 32;    // 32 x 32
    final int scale = 2;
    public final int tileSize = baseSize * scale;      // 64 x 64 pixels

    public final int tileNrWidth = 16;                 // 16 horizontal tiles
    public final int tileNrHeight = 12;                // 12 vertical tiles
    public final int screenWidth = tileSize * tileNrWidth;
    public final int screenHeight = tileSize * tileNrHeight;

    // FPS
    int FPS = 60;

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int titleState = 3;

    // LEVEL PROGRESSION:
    public int lvl1Mobs = 10;
    public int lvl2Mobs =  10;
    public int lvl3Mobs = 10;

    public int[] mobCount = {2, 4, 6};

    public final int maxLvl = 3;
    public int currentLevel = 0;                // start with 0

    MapBuilder map = new MapBuilder(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread mainThread;
    public Collisions colls = new Collisions(this);
    public EntityFactory Factory = new EntityFactory(this);
    public GameUI ui = new GameUI(this);
    public Player player = new Player(this, keyH);
    public Entity[][] mobs = new Entity[maxLvl][10];
    public Entity[][] obj = new Entity[maxLvl][10];

    public ArrayList<Entity> entityList = new ArrayList<>();

    public ContextSettings() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        gameState = titleState;
    }

    public void setupGame(int currentLevel) {           // Extend this for new levels
        this.currentLevel = currentLevel;
        Factory.placePowerUp();
        Factory.placeZombie();
    }

    public void startMainThread() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    @Override
    public void run() {                 // This method contains the GAME LOOP

        // RENDERING VARIABLES
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (mainThread != null) {

            currentTime = System.nanoTime();


            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer > 1000000000) {
                System.out.println("FPS: " + drawCount);            // Delete if you don't want to see FPS in console
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        if (mobCount[currentLevel] == 0)
            gameState = pauseState;

        if (gameState == playState) {
            player.update();

            if (obj[currentLevel][0] != null) {
                obj[currentLevel][0].update();
            }

            for (int i = 0; i < mobs[0].length; i++){
                if (mobs[currentLevel][i] != null)
                    mobs[currentLevel][i].update();
            }
        } else if (gameState == pauseState) {}
    }

    // Research further on rendering method (have to also research update methods for multiple sprite objects)
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        // MAP
        map.draw(g2D);

        // Entity Array - adding elements:

        entityList.add(player);

        for (int i = 0; i < obj[1].length; i++) {
            if (obj[currentLevel][i] != null) {
                entityList.add(obj[currentLevel][i]);
            }
        }

        // ADD NPCs , Monsters, Objects later

        for (int i = 0; i < mobs[1].length; i++) {
            if (mobs[currentLevel][i] != null)
                entityList.add(mobs[currentLevel][i]);
        }

        // Entity Array - sorting elements
        //entityList.sort(Comparator.comparingInt((Entity[] e) -> e[currentLevel].y));
        entityList.sort(Comparator.comparingInt((Entity o) -> o.y));

        // Entity Array - Drawing and removing elements
        for (Entity entity : entityList) entity.draw(g2D);
        entityList.clear();

        //UI
        ui.draw(g2D);
        g2D.dispose();
    }
}
