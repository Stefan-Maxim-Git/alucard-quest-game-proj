package tile;

import main.ContextSettings;
import main.ToolBox;

import javax.imageio.ImageIO;
import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MapBuilder {
    ContextSettings cs;
    public Tile[] map;
    public int[][][] mapNums;
    public int[][][] decor;

    public MapBuilder(ContextSettings cs) {
        this.cs = cs;
        map = new Tile[30];
        mapNums = new int[cs.maxLvl][cs.tileNrWidth][cs.tileNrHeight];
        decor = new int[cs.maxLvl][cs.tileNrWidth][cs.tileNrHeight];
        getmapTiles();
        loadMap("/maps/Map1.txt",0, null);
        loadMap("/maps/Map2.txt",1, "/maps/Decors.txt");
        loadMap("/maps/Map3.txt",2, "/maps/Decors2.txt");
    }

    public void getmapTiles() {
            loadTile(0, "Level1", "Grass_Tile", false);
            loadTile(1, "Level1", "Grass_Tile_Divider", false);
            loadTile(2, "Level1", "Grass_Tile_Divider_Down", false);
            loadTile(3, "Level1", "Grass_Tile_Flowers", false);
            loadTile(4, "Level1", "Grass_Tile_Pebble", false);
            loadTile(5, "Level1", "Path_Tile", false);
            loadTile(6, "Level1", "Path_Tile_Pebble", false);
            loadTile(7, "Level1", "Path_Tile_Weed", false);
            loadTile(8, "Level1", "Tree_Tile_1", true);
            loadTile(9, "Level1", "Tree_Tile_2", true);

            // Map 2 and 3 tiles
            loadTile(10, "Level2", "CARPET_1", false);
            loadTile(11, "Level2", "CARPET_2", false);
            loadTile(12, "Level2", "CARPET_3", false);
            loadTile(13, "Level2", "FLOOR_TILE_1", false);
            loadTile(14, "Level2", "FLOOR_TILE_2", false);
            loadTile(15, "Level2", "FLOOR_TILE_3", false);
            loadTile(16, "Level2", "WALL_TILE_1", true);

            // Map decoration tiles
            loadTile(17, "Decorations", "KNIGHT_1", false);
            loadTile(18, "Decorations", "PILLAR_1", true);
            loadTile(19, "Decorations", "THRONE_1", true);
            loadTile(20, "Decorations", "TORCH_1", true);

            // Map 3 extra tiles
            loadTile(21, "Level2", "CARPET_4", false);
            loadTile(22, "Level2", "CARPET_5", false);
            loadTile(23, "Level2", "CARPET_CORNER_1", false);
            loadTile(24, "Level2", "CARPET_CORNER_2", false);
            loadTile(25, "Level2", "CARPET_CORNER_3", false);
            loadTile(26, "Level2", "CARPET_CORNER_4", false);
    }

    public void loadTile(int index, String Level, String imageName, boolean collisions){
        ToolBox tools = new ToolBox();

        try{
            map[index] = new Tile();
            map[index].image = ImageIO.read (new FileInputStream("res/tiles/" + Level + "/" + imageName + ".png"));
            map[index].image = tools.scaleImage(map[index].image, cs.tileSize, cs.tileSize);
            map[index].collisions = collisions;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int level, String decsPath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            if (decsPath != null) {
                InputStream is2 = getClass().getResourceAsStream(decsPath);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

                int i = 0;      // Row
                int j = 0;      // Column

                while (j < cs.tileNrWidth&& i < cs.tileNrHeight) {             // while max nr of rows and columns haven't been reached
                    String line = br.readLine();                            // Reads one line from text file
                    String line2 = br2.readLine();

                    while(j < cs.tileNrWidth) {
                        String[] indexString = line.split(" ");
                        String[] indexString2 = line2.split(" ");
                        int index = Integer.parseInt(indexString[j]);
                        int index2 = Integer.parseInt(indexString2[j]);
                        if (index2 != 0) System.out.println(index2);
                        mapNums[level][j][i] = index;
                        decor[level][j][i] = index2;
                        j++;
                    }

                    if (j == cs.tileNrWidth) {
                        j = 0;
                        i++;
                    }
                }
                br.close();
            } else if (decsPath ==  null) {
                int i = 0;      // Row
                int j = 0;      // Column

                while (j < cs.tileNrWidth&& i < cs.tileNrHeight) {             // while max nr of rows and columns haven't been reached
                    String line = br.readLine();                            // Reads one line from text file

                    while(j < cs.tileNrWidth) {
                        String[] indexString = line.split(" ");
                        int index = Integer.parseInt(indexString[j]);
                        mapNums[level][j][i] = index;
                        j++;
                    }

                    if (j == cs.tileNrWidth) {
                        j = 0;
                        i++;
                    }
                }
                br.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // DRAW METHOD:
    // Finds all the indexes previously defined in the map vector and draws the image corresponding each tile;
    public void draw (Graphics2D g2D) {
        int i = 0;
        int j = 0;
        int x = 0;
        int y = 0;

        while (i < cs.tileNrHeight && j < cs.tileNrWidth) {
            int drawIndex = mapNums[cs.currentLevel][j][i];
            int objIndex = decor[cs.currentLevel][j][i];
                g2D.drawImage(map[drawIndex].image, x, y,null);
                if (objIndex != 0) g2D.drawImage(map[objIndex].image, x, y,null);
                j++;
                x += cs.tileSize;

                if (j == cs.tileNrWidth) {
                    j = 0;
                    x = 0;
                    i++;
                    y += cs.tileSize;
                }
        }
    }
}

