package main;

import Entity.Entity;

public class Collisions {

    ContextSettings cs;

    public Collisions(ContextSettings cs) {
        this.cs = cs;
    }

    public void checkTile(Entity entity) {
        int entityLX = entity.x + entity.hitbox.x;
        int entityRX = entityLX + entity.hitbox.width;
        int entityTY = entity.y + entity.hitbox.y;
        int entityBY = entityTY + entity.hitbox.height;

        // Can subtract 1 if collisions dont work properly
        int entityLC = entityLX/cs.tileSize;
        int entityRC = entityRX/cs.tileSize;
        int entityTR = entityTY/cs.tileSize;
        int entityBR = entityBY/cs.tileSize;

        int tile1Index, tile2Index;

        switch (entity.direction) {
            case "up" -> {
                // Need offscreen control too
                entityTR = (entityTY - entity.speed) / cs.tileSize;
                tile1Index = cs.map.mapNums[cs.currentLevel][entityLC][entityTR];
                tile2Index = cs.map.mapNums[cs.currentLevel][entityRC][entityTR];
                if (cs.map.map[tile1Index].collisions || cs.map.map[tile2Index].collisions)
                    entity.colliding = true;
            }
            case "down" -> {
                if (entityBY + entity.speed >= cs.tileSize * cs.tileNrHeight) {
                    entity.colliding = true;
                } else {
                    entityBR = (entityBY + entity.speed) / cs.tileSize;
                    tile1Index = cs.map.mapNums[cs.currentLevel][entityLC][entityBR];
                    tile2Index = cs.map.mapNums[cs.currentLevel][entityRC][entityBR];

                    if (cs.map.map[tile1Index].collisions || cs.map.map[tile2Index].collisions)
                        entity.colliding = true;
                }
            }
            case "left" -> {
                if (entityLX - entity.speed < 0) {
                    entity.colliding = true;
                } else {
                    entityLC = (entityLX - entity.speed) / cs.tileSize;
                    tile1Index = cs.map.mapNums[cs.currentLevel][entityLC][entityTR];
                    tile2Index = cs.map.mapNums[cs.currentLevel][entityLC][entityBR];

                    if (cs.map.map[tile1Index].collisions || cs.map.map[tile2Index].collisions)
                        entity.colliding = true;
                }
            }
            case "right" -> {
                if (entityRX + entity.speed >= cs.tileSize * cs.tileNrWidth) {
                    entity.colliding = true;
                } else {
                    entityRC = (entityRX + entity.speed) / cs.tileSize;
                    tile1Index = cs.map.mapNums[cs.currentLevel][entityRC][entityTR];
                    tile2Index = cs.map.mapNums[cs.currentLevel][entityRC][entityBR];

                    if (cs.map.map[tile1Index].collisions || cs.map.map[tile2Index].collisions)
                        entity.colliding = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < cs.obj[1].length; i++) {

            if (cs.obj[cs.currentLevel][i] != null) {

                // entity hitbox position on map:
                entity.hitbox.x = entity.x + entity.hitbox.x;
                entity.hitbox.y = entity.y +entity.hitbox.y;

                // object hitbox position on map:
                cs.obj[cs.currentLevel][i].hitbox.x = cs.obj[cs.currentLevel][i].x + cs.obj[cs.currentLevel][i].hitbox.x;
                cs.obj[cs.currentLevel][i].hitbox.y = cs.obj[cs.currentLevel][i].y + cs.obj[cs.currentLevel][i].hitbox.y;

                // check collision with intersects() method in Rectangle class:
                nextHitboxPos(entity);
                if (entity.hitbox.intersects(cs.obj[cs.currentLevel][i].hitbox)) {
                    if (cs.obj[cs.currentLevel][i].collision) {
                        entity.colliding = true;
                    }

                    if (player) {
                        index = i;
                    }
                }

                entity.hitbox.x = entity.defaultHitboxX;
                entity.hitbox.y = entity.defaultHitboxY;

                cs.obj[cs.currentLevel][i].hitbox.x = cs.obj[cs.currentLevel][i].defaultHitboxX;
                cs.obj[cs.currentLevel][i].hitbox.y = cs.obj[cs.currentLevel][i].defaultHitboxY;

            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for (int i = 0; i < target[1].length; i++) {

            if (target[cs.currentLevel][i] != null) {

                // entity hitbox position on map:
                entity.hitbox.x = entity.x + entity.hitbox.x;
                entity.hitbox.y = entity.y +entity.hitbox.y;

                // object hitbox position on map:
                target[cs.currentLevel][i].hitbox.x = target[cs.currentLevel][i].x + target[cs.currentLevel][i].hitbox.x;
                target[cs.currentLevel][i].hitbox.y = target[cs.currentLevel][i].y + target[cs.currentLevel][i].hitbox.y;

                // check collision with intersects() method in Rectangle class:
                nextHitboxPos(entity);
                if (entity.hitbox.intersects(target[cs.currentLevel][i].hitbox)) {
                    if (target[cs.currentLevel][i] != entity) {
                        entity.colliding = true;
                        index = i;
                    }
                }

                entity.hitbox.x = entity.defaultHitboxX;
                entity.hitbox.y = entity.defaultHitboxY;

                target[cs.currentLevel][i].hitbox.x = target[cs.currentLevel][i].defaultHitboxX;
                target[cs.currentLevel][i].hitbox.y = target[cs.currentLevel][i].defaultHitboxY;

            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {

            boolean damagePlayer = false;
            // entity hitbox position on map:
            entity.hitbox.x = entity.x + entity.hitbox.x;
            entity.hitbox.y = entity.y +entity.hitbox.y;

            // object hitbox position on map:
            cs.player.hitbox.x = cs.player.x + cs.player.hitbox.x;
            cs.player.hitbox.y = cs.player.y + cs.player.hitbox.y;

            // check collision with intersects() method in Rectangle class:
            nextHitboxPos(entity);
            if (entity.hitbox.intersects(cs.player.hitbox)) {
                entity.colliding = true;
                damagePlayer = true;
            }

            entity.hitbox.x = entity.defaultHitboxX;
            entity.hitbox.y = entity.defaultHitboxY;

            cs.player.hitbox.x = cs.player.defaultHitboxX;
            cs.player.hitbox.y = cs.player.defaultHitboxY;

        return damagePlayer;
    }

    public void nextHitboxPos (Entity entity) {
        switch (entity.direction) {
            case "up" -> entity.hitbox.y -= entity.speed;
            case "down" -> entity.hitbox.y += entity.speed;
            case "left" -> entity.hitbox.x -= entity.speed;
            case "right" -> entity.hitbox.x += entity.speed;
        }
    }

}
