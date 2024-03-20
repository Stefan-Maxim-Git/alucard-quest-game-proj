package Entity;

import main.ContextSettings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Vampire extends Entity{
    public BufferedImage idle, idleUp, idleLeft, idleRight;
    public BufferedImage[] downArr = new BufferedImage[4];
    public BufferedImage[] leftArr = new BufferedImage[4];
    public BufferedImage[] rightArr = new BufferedImage[4];
    public BufferedImage[] upArr = new BufferedImage[4];

    public Vampire (ContextSettings cs, int x, int y) {
        super(cs);
        setDefaultValues(x, y);
        getSprites();
    }

    // Might make a parameter constructor for wave placement (x and y)

    public void setDefaultValues(int x, int y){
        name = "vampire";
        speed = 3;
        maxLife = 2;
        life = maxLife;
        width = 64;
        height = 94;


        hitbox = new Rectangle(16, 38, 32, 56);
        defaultHitboxX = hitbox.x;
        defaultHitboxY = hitbox.y;

        // Positioning (for testing)
        this.x = x;
        this.y = y;
    }

    public void getSprites() {
        idle = loadImage("VAMPIRE_FRONT_IDLE_1", "vampire");
        idleUp = loadImage("VAMPIRE_BACK_IDLE_1", "vampire");
        idleLeft = loadImage("VAMPIRE_LEFT_IDLE_1", "vampire");
        idleRight = loadImage("VAMPIRE_RIGHT_IDLE_1", "vampire");

        upArr[0] = loadImage("VAMPIRE_BACK_WALK_1", "vampire");
        upArr[1] = loadImage("VAMPIRE_BACK_WALK_2", "vampire");
        upArr[2] = loadImage("VAMPIRE_BACK_WALK_1", "vampire");
        upArr[3] = loadImage("VAMPIRE_BACK_WALK_2", "vampire");

        downArr[0] = loadImage("VAMPIRE_FRONT_WALK_1", "vampire");
        downArr[1] = loadImage("VAMPIRE_FRONT_WALK_2", "vampire");
        downArr[2] = loadImage("VAMPIRE_FRONT_WALK_1", "vampire");
        downArr[3] = loadImage("VAMPIRE_FRONT_WALK_2", "vampire");

        rightArr[0] = loadImage("VAMPIRE_RIGHT_IDLE_1", "vampire");
        rightArr[1] = loadImage("VAMPIRE_RIGHT_WALK_1", "vampire");
        rightArr[2] = loadImage("VAMPIRE_RIGHT_IDLE_1", "vampire");
        rightArr[3] = loadImage("VAMPIRE_RIGHT_WALK_1", "vampire");


        leftArr[0] = loadImage("VAMPIRE_LEFT_IDLE_1", "vampire");
        leftArr[1] = loadImage("VAMPIRE_LEFT_WALK_1", "vampire");
        leftArr[2] = loadImage("VAMPIRE_LEFT_IDLE_1", "vampire");
        leftArr[3] = loadImage("VAMPIRE_LEFT_WALK_1", "vampire");
    }
    @Override
    public void draw(Graphics2D g2D) {

        BufferedImage img = null;

        // might add idle animations if necessary
        switch (direction) {
            case "up" -> img = upArr[spriteNum - 1];
            case "down" -> img = downArr[spriteNum - 1];
            case "left" -> img = leftArr[spriteNum - 1];
            case "right" -> img = rightArr[spriteNum - 1];
        }

        if (invincible) g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2D.drawImage(img, x, y, null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void behavior() {
        actionCounter++;

        if (actionCounter == 60) {         // Every 2 seconds, choose an action

            Random rand = new Random();
            int i = rand.nextInt(100) + 1;      // Pick a number from 1 to 100

            if (i <= 25) { direction = "up";}
            if (i > 25 && i <= 50) { direction = "down";}
            if (i > 50 && i <= 75) { direction = "left";}
            if (i > 75) { direction = "right";}

            actionCounter = 0;
        }

    }
}
