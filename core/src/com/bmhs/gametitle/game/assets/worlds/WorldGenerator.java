package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    private int seedColor, grass, sand, trees, stone, bush;

    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        seedColor = 15;
        sand = 29;
        grass = 68;
        trees = 86;
        stone = 106;
        bush = 96;

                Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        System.out.println(mapSeed.y + " "+ mapSeed.x);


        worldIntMap[(int)mapSeed.y][(int)mapSeed.x] = 4;
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = 85;
            }
        }

        seedIsland(5);
        //call methods to build 2D array
        // randomize();
        // generateWorldTextFile();
        searchAndExpand(26, seedColor, 45, 0.03);
        searchAndExpand(25, seedColor, sand, 0.03);
        searchAndExpand(20, seedColor, grass, 0.10);
        searchAndExpand(20, seedColor, trees, 0.85);
        searchAndExpand(20, seedColor, stone, 0.95);
        searchAndExpand(20, seedColor, bush, 0.95);
        searchAndExpand(15, seedColor, 5, 0.10);
        searchAndExpand(13, seedColor, 6, 0.35);
        searchAndExpand(6, seedColor, 14, 0.00);
        searchAndExpand(6, seedColor, 16, 0.90);

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }

    private void seedIsland(int num) {
        for(int i = 0; i < num; i++) {
            int rSeed = MathUtils.random(worldIntMap.length-1);
            int cSeed = MathUtils.random(worldIntMap[0].length-1);
            worldIntMap[rSeed][cSeed] = seedColor;
        }
    }

    private void searchAndExpand(int radius, int numToFind, int numToWrite, double probability) {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                if(worldIntMap[r][c] == numToFind) {
                    for(int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for(int subCol = c-radius; subCol <= c+radius; subCol++) {
                            if(subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length-1 && subCol <= worldIntMap[0].length-1 && worldIntMap[subRow][subCol] != numToFind) {
                                if(Math.random() > probability) {
                                    worldIntMap[subRow][subCol] = numToWrite;
                                }
                            }

                        }
                    }

                }


            }
        }
    }
    /*
    private void searchAndExpand(int radius) {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {

                if(worldIntMap[r][c] == seedColor) {

                    for(int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for(int subCol = c-radius; subCol <= c+radius; subCol++) {

                            if(subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length-1 && subCol <= worldIntMap[0].length-1 && worldIntMap[subRow][subCol] != seedColor) {
                                worldIntMap[subRow][subCol] = 67;
                            }
                        }
                    }
                }
            }
        }
    }

     */

    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

    private void generateWorldTextFile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }
}
