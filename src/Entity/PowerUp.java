package Entity;

import main.ContextSettings;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
*   Change this to extend Entity after ObjFrame will be deleted.
*   Change parameters accordingly for code to work correctly
*/
public class PowerUp extends Entity {
    public BufferedImage img1, img2, img3;
    public boolean collision;

    public PowerUp(ContextSettings cs) {
        super(cs);
        name = "Moonlight";
        hitbox = new Rectangle(8, 8, 48, 48);
        width = 64;
        height = 64;
        collision = true;
        loadSprites();
    }

    public void loadSprites() {
        img1 = loadImage("Floor_Sparkle_1", "objects");
        img2 = loadImage("Floor_Sparkle_2", "objects");
        img3 = loadImage("Floor_Sparkle_3", "objects");
    }

    @Override
    public void update() {
        spriteCounter++;

        if (spriteCounter > 10) {
            if (spriteNum == 1)     spriteNum = 2;
            else if (spriteNum == 2)    spriteNum = 3;
            else if (spriteNum == 3)    spriteNum = 1;

            spriteCounter = 0;
        }

    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage img = null;

        switch (spriteNum) {
            case 1 -> img = img1;
            case 2 -> img = img2;
            case 3 -> img = img3;
        }

        g2d.drawImage(img, x, y, null);
    }
}
