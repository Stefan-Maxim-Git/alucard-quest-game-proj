package Entity;

import main.ContextSettings;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Player extends Entity {
    ContextSettings cs;
    KeyHandler kh;
    public BufferedImage idle, idleUp, idleLeft, idleRight;
    public BufferedImage[] downArr = new BufferedImage[4];
    public BufferedImage[] leftArr = new BufferedImage[4];
    public BufferedImage[] rightArr = new BufferedImage[4];
    public BufferedImage[] upArr = new BufferedImage[4];

    public BufferedImage[] attackleft = new BufferedImage[4];
    public BufferedImage[] attackRight = new BufferedImage[4];
    public BufferedImage[] attackUp = new BufferedImage[3];
    public BufferedImage[] attackDown = new BufferedImage[3];
    public int MoonStage = 0;


    public Player(ContextSettings cs, KeyHandler kh) {
        super(cs);
        this.cs = cs;
        this.kh = kh;
        setDefaultValues();
        getSprites();
        getAttackSprites();
    }

    public void setDefaultValues () {
        x = 100;
        y = 100;
        speed = 6;
        maxLife = 3;
        life = maxLife;
        width = 64;
        height = 94;
        hitbox = new Rectangle(16, 38, 32, 56);
        defaultHitboxX = hitbox.x;
        defaultHitboxY = hitbox.y;
        direction = "down";

        attackArea.width = 48;
        attackArea.height = 64;
    }

    public void getSprites (){
            idle = loadImage("Idle_1", "player");
            idleUp = loadImage("Back_Idle_1", "player");
            idleRight = loadImage("Right_Idle_1", "player");
            idleLeft = loadImage("Left_idle_1", "player");

            upArr[0] = loadImage("Back_Run_1", "player");
            upArr[1] = loadImage("Back_Run_2", "player");
            upArr[2] = loadImage("Back_Run_3", "player");
            upArr[3] = loadImage("Back_Run_4", "player");

            downArr[0] = loadImage("Front_Run_1", "player");
            downArr[1] = loadImage("Front_Run_2", "player");
            downArr[2] = loadImage("Front_Run_3", "player");
            downArr[3] = loadImage("Front_Run_4", "player");

            rightArr[0] = loadImage("Right_Run_1", "player");
            rightArr[1] = loadImage("Right_Run_2", "player");
            rightArr[2] = loadImage("Right_Run_3", "player");
            rightArr[3] = loadImage("Right_Run_4", "player");

            leftArr[0] = loadImage("Left_Run_1", "player");
            leftArr[1] = loadImage("Left_Run_2", "player");
            leftArr[2] = loadImage("Left_Run_3", "player");
            leftArr[3] = loadImage("Left_Run_4", "player");
    }

    // will be handled when attack will be implemented

    public void getAttackSprites() {
            attackleft[0] = loadImage("Attack_Left_Idle", "player/attack", 96, 96);
            attackleft[1] = loadImage("Attack_Left_1", "player/attack", 96, 96);
            attackleft[2] = loadImage("Attack_Left_2", "player/attack", 96, 96);
            attackleft[3] = loadImage("Attack_Left_3", "player/attack", 96, 96);

            attackRight[0] = loadImage("Attack_Right_Idle", "player/attack", 96, 96);
            attackRight[1] = loadImage("Attack_Right_1", "player/attack", 96, 96);
            attackRight[2] = loadImage("Attack_Right_2", "player/attack", 96, 96);
            attackRight[3] = loadImage("Attack_Right_3", "player/attack", 96, 96);

            attackUp[0] = loadImage("ATTACK_ANIM_BACK_1", "player/attack", 64, 94);
            attackUp[1] = loadImage("ATTACK_ANIM_BACK_2", "player/attack", 64, 94);
            attackUp[2] = loadImage("ATTACK_ANIM_BACK_3", "player/attack", 64, 94);

            attackDown[0] = loadImage("ATTACK_ANIM_FRONT_1", "player/attack", 64, 94);
            attackDown[1] = loadImage("ATTACK_ANIM_FRONT_2", "player/attack", 64, 94);
            attackDown[2] = loadImage("ATTACK_ANIM_FRONT_3", "player/attack", 64, 94);


    }
    public void update() {
        if (attacking) {
            attack();
        }else if (kh.rightPressed || kh.leftPressed || kh.upPressed || kh.downPressed || kh.attackPressed) {
            if (kh.upPressed) { direction = "up";}
            if (kh.downPressed) { direction = "down";}
            if (kh.rightPressed) { direction = "right";}
            if (kh.leftPressed) { direction = "left";}
            if (kh.attackPressed) { attacking = true;} // DELETE IF NOT WORKING

            // Check tile collisions:
            colliding = false;
            cs.colls.checkTile(this);

            // Check object collisions:
            int objIndex = cs.colls.checkObject(this, true);
            int mobIndex = cs.colls.checkEntity(this, cs.mobs);
            contactDamage(mobIndex);
            invTime(120);
            pickupObject(objIndex);


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
    } else idleState = true;

    int mobIndex = cs.colls.checkEntity(this, cs.mobs);
    contactDamage(mobIndex);
    invTime(120);
}

    private void attack() {
        spriteCounter++;

        // Frame 1:
        if (spriteCounter <= 5) spriteNum = 1;
        // Frame 2:
        if (spriteCounter > 5 && spriteCounter <= 15) spriteNum = 2;
        // Frame 3:
        if (spriteCounter > 15 && spriteCounter <= 20) spriteNum = 3;
        // Frame 4:
        if (spriteCounter > 20 && spriteCounter <= 25) {
            spriteNum = 4;

            int backupX = x;
            int backupY = y;
            int backupHitboxWidth = hitbox.width;
            int backupHitboxHeight = hitbox.height;

            switch(direction){
                case "up" -> y -= attackArea.height;
                case "down" -> y += attackArea.height;
                case "left" -> x -= attackArea.width;
                case "right" -> x += attackArea.width;
            }

            hitbox.width = attackArea.width;
            hitbox.height = attackArea.height;

            int mobIndex = cs.colls.checkEntity(this, cs.mobs);
            inflictDamage(mobIndex);
            x = backupX;
            y = backupY;
            hitbox.height = backupHitboxHeight;
            hitbox.width = backupHitboxWidth;
        }

        // Now, reverse frames

        // Frame 5:
        if (spriteCounter > 25 && spriteCounter <= 30) spriteNum = 3;
        // Frame 6:
        if (spriteCounter > 30 && spriteCounter <= 35) spriteNum = 2;
        // Frame 7:
        if (spriteCounter > 35 && spriteCounter <= 40) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void inflictDamage(int mobIndex) {
        if (mobIndex != 999){
            if (!cs.mobs[cs.currentLevel][mobIndex].invincible){
                cs.mobs[cs.currentLevel][mobIndex].life--;
                cs.mobs[cs.currentLevel][mobIndex].invincible = true;

                if (cs.mobs[cs.currentLevel][mobIndex].life <= 0) {
                    if (cs.mobCount[cs.currentLevel] > 0)
                        cs.mobCount[cs.currentLevel]--;
                    cs.mobs[cs.currentLevel][mobIndex] = null;
                }

            }
        }

        else
            System.out.println("Miss!");
    }

    private void contactDamage(int mobIndex) {

        if (mobIndex != 999 && life > 0) {
            if (!invincible) {
                life--;
                invincible = true;
            }
        }
    }

    public void pickupObject(int i) {
        if (i != 999) {
            String objName = cs.obj[cs.currentLevel][i].name;
            if (objName.equals("Moonlight")) {
                MoonStage++;
                cs.obj[cs.currentLevel][i] = null;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2D) {

        BufferedImage img = null;

        // Test code for attack anims:
        if (attacking)
            switch (direction) {
                case "up" -> img = attackUp[spriteNum % attackUp.length];
                case "down" -> img = attackDown[spriteNum % attackDown.length];
                case "left" -> img = attackleft[spriteNum-1];       // Only this attacks for now
                case "right" -> img = attackRight[spriteNum-1];     // Only this attacks or now
            }
        else if (idleState){
            switch (direction) {
                case "up" -> img = idleUp;
                case "down" -> img = idle;
                case "left" -> img = idleLeft;
                case "right" -> img = idleRight;
            }
        } else switch (direction) {
            case "up" -> img = upArr[spriteNum-1];
            case "down" -> img = downArr[spriteNum-1];
            case "left" -> img = leftArr[spriteNum-1];
            case "right" -> img = rightArr[spriteNum-1];
        }

        if (invincible) g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2D.drawImage(img, x, y,null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}


