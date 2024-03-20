package main;

import Entity.PowerUp;
import Entity.Vampire;
import Entity.Zombie;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EntityFactory {
    ContextSettings cs;

    public EntityFactory(ContextSettings cs) {
        this.cs = cs;
    }

    public void placeObj() {
        cs.obj[cs.currentLevel][0] =  new PowerUp(cs);
        System.out.println("PowerUp  created");

        Random rand = new Random();

        int col, row;

        col = rand.nextInt(15);
        row = rand.nextInt(11);

        int index = cs.map.mapNums[cs.currentLevel][col][row];

        while (cs.map.map[index].collisions) {
            col = rand.nextInt(15);
            row = rand.nextInt(11);
            index = cs.map.mapNums[cs.currentLevel][col][row];
        }

        cs.obj[cs.currentLevel][0].x = col * cs.tileSize;
        cs.obj[cs.currentLevel][0].y = row * cs.tileSize;
    }

    public void placePowerUp() {
        System.out.println("Called Place Powerup.");
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
                if (cs.player.MoonStage != 3) {
                    placeObj();
                    System.out.println("Method executed on level " + cs.currentLevel);
                }
            }, 3, 15, TimeUnit.SECONDS);
        }

    public void placeZombie() {
        int map = 0;
        cs.mobs[map][0] = new Zombie(cs, cs.tileSize * 11, cs.tileSize);
        cs.mobs[map][1] = new Zombie(cs, cs.tileSize * 11, cs.tileSize * 9);

        map = 1;
        cs.mobs[map][0] = new Vampire(cs, cs.tileSize * 14, cs.tileSize);
        cs.mobs[map][1] = new Vampire(cs, cs.tileSize * 14, cs.tileSize * 3);
        cs.mobs[map][2] = new Vampire(cs, cs.tileSize * 14, cs.tileSize * 6);
        cs.mobs[map][3] = new Vampire(cs, cs.tileSize * 14, cs.tileSize * 9);

        map = 2;
        cs.mobs[map][0] = new Zombie(cs, cs.tileSize * 11, cs.tileSize*3);
        cs.mobs[map][1] = new Zombie(cs, cs.tileSize * 11, cs.tileSize*6);
        cs.mobs[map][2] = new Zombie(cs, cs.tileSize * 11, cs.tileSize * 9);
        cs.mobs[map][3] = new Vampire(cs, cs.tileSize * 9, cs.tileSize * 3);
        cs.mobs[map][4] = new Vampire(cs, cs.tileSize * 14, cs.tileSize * 6);
        cs.mobs[map][5] = new Vampire(cs, cs.tileSize * 6, cs.tileSize * 9);
    }
    }

