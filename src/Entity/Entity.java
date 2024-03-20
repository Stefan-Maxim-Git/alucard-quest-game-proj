package Entity;

import main.ContextSettings;
import main.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Entity {

    public ContextSettings cs;

    // Positioning parameters
    public int x, y;
    public int width, height;
    public int speed;
    public String direction = "down";

    public String name;

    // Sprites parameters
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean idleState = true;
    public int actionCounter = 0;
    // Might handle sprite images here if I manage to make most entities have the same amount of frames

    // Collision parameters
    public Rectangle hitbox;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int defaultHitboxX, defaultHitboxY;
    public boolean colliding = false;
    public boolean collision = true;

    // Other params
    public int maxLife;
    public int life;
    public boolean invincible = false;
    public int invCounter = 0;
    public boolean attacking = false;


    protected Entity(ContextSettings cs) {this.cs = cs;}

    // For loadImage to work, each Entity has to have the fields width and height initialized;
    public BufferedImage loadImage (String imageName, String entityType) {
        ToolBox tools = new ToolBox();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read (new FileInputStream("res/"+ entityType+ "/" + imageName + ".png"));
            scaledImage = tools.scaleImage(scaledImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledImage;
    }

    public BufferedImage loadImage (String imageName, String entityType, int width, int height) {
        ToolBox tools = new ToolBox();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read (new FileInputStream("res/"+ entityType+ "/" + imageName + ".png"));
            scaledImage = tools.scaleImage(scaledImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledImage;
    }

    public void behavior() {}
    public void update() {

        behavior();

        colliding = false;
        cs.colls.checkTile(this);
        cs.colls.checkEntity(this, cs.mobs);
        boolean damagePlayer = cs.colls.checkPlayer(this);

        if (damagePlayer && cs.player.life > 0) {
            if (!cs.player.invincible) {
                cs.player.life--;
                cs.player.invincible = true;
            }
        }

        if(!colliding) {
            switch (direction) {
                case "up" -> y -= speed;
                case "down" -> y += speed;
                case "left" -> x -= speed;
                case "right" -> x += speed;
            }
        }

        //Sprite display
        idleState = false;
        spriteCounter++;

        if (spriteCounter > 10) {
            if (spriteNum == 1) {spriteNum = 2;}
            else if (spriteNum == 2) {spriteNum = 3;}
            else if (spriteNum == 3) {spriteNum = 4;}
            else if (spriteNum == 4) {spriteNum = 1;}
            spriteCounter = 0;
        }

        invTime(40);
    }
    public void draw(Graphics2D g2D) {}

    public void invTime (int time) {
        if (invincible) {
            invCounter++;
            if (invCounter > time) {
                invincible = false;
                invCounter = 0;
            }
        }
    }
}

