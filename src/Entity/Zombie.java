package Entity;

import main.ContextSettings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Zombie extends Entity {

    public BufferedImage idle, idleUp, idleLeft, idleRight;
    public BufferedImage[] downArr = new BufferedImage[4];
    public BufferedImage[] leftArr = new BufferedImage[4];
    public BufferedImage[] rightArr = new BufferedImage[4];
    public BufferedImage[] upArr = new BufferedImage[4];

    public Zombie (ContextSettings cs, int x, int y) {
        super(cs);
        setDefaultValues(x, y);
        getSprites();
    }

    // Might make a parameter constructor for wave placement (x and y)

    public void setDefaultValues(int x, int y){
        name = "Zombie";
        speed = 3;
        maxLife = 1;
        life = maxLife;
        width = 64;
        height = 94;

        // Hitbox:
        // Zombie Dimensions = Player Dimensions. Using same hitbox size;

        hitbox = new Rectangle(16, 38, 32, 56);
        defaultHitboxX = hitbox.x;
        defaultHitboxY = hitbox.y;

        // Positioning (for testing)
        this.x = x;
        this.y = y;
    }

    public void getSprites() {
        idle = loadImage("Zombie_Front_Idle", "zombie");
        idleUp = loadImage("Zombie_Back_Idle", "zombie");
        idleRight = loadImage("Zombie_Right_1", "zombie");
        idleLeft = loadImage("Zombie_Left_1", "zombie");

        upArr[0] = loadImage("Zombie_Back_1", "zombie");
        upArr[1] = loadImage("Zombie_Back_2", "zombie");
        upArr[2] = loadImage("Zombie_Back_1", "zombie");
        upArr[3] = loadImage("Zombie_Back_2", "zombie");

        downArr[0] = loadImage("Zombie_Front_1", "zombie");
        downArr[1] = loadImage("Zombie_Front_2", "zombie");
        downArr[2] = loadImage("Zombie_Front_1", "zombie");
        downArr[3] = loadImage("Zombie_Front_2", "zombie");

        rightArr[0] = loadImage("Zombie_Right_1", "zombie");
        rightArr[1] = loadImage("Zombie_Right_2", "zombie");
        rightArr[2] = loadImage("Zombie_Right_3", "zombie");
        rightArr[3] = loadImage("Zombie_Right_4", "zombie");

        leftArr[0] = loadImage("Zombie_Left_1", "zombie");
        leftArr[1] = loadImage("Zombie_Left_2", "zombie");
        leftArr[2] = loadImage("Zombie_Left_3", "zombie");
        leftArr[3] = loadImage("Zombie_Left_4", "zombie");

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
