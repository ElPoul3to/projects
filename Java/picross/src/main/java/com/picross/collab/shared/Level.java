package com.picross.collab.shared;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Sert à parser les donner du fichier DataBase.json pour un nuiveau de jeu et de pouvoir les acuqérir grâce aux
 * différentes fonctions get.
 *
 * @author Thomas Fargues et Evan Cocain
 */


public class Level  {
    private final int width;
    private final int height;
    private final String title;
    private int[][] solve;
    private final int lives;
    private final String[] rowsIndices;
    private final String[] columnsIndices;

    private int[][] board;
    public JSONObject levelJSON;

    public Level(String pic_id) throws IOException, ParseException {
        JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
        levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(pic_id));
        width = Math.round((long) levelJSON.get("width"));
        height = Math.round((long) levelJSON.get("height"));
        title = (String) levelJSON.get("title");
        solve = parseOneType(levelJSON, "solve");
        rowsIndices = ((String) levelJSON.get("rows")).split("\\|");
        columnsIndices = ((String) levelJSON.get("columns")).split("\\|");
        lives = 5;
    }

    //Constructor for seriazlized level
    public Level(String levelSerialized, boolean serialized) throws ParseException {
        // Constructeur que le client utilise, donc pas besoin de remplir solve
        JSONParser parser = new JSONParser();
        JSONObject levelJSON = (JSONObject) parser.parse(levelSerialized);
        width = Math.round((long) levelJSON.get("width"));
        height = Math.round((long) levelJSON.get("height"));
        title = (String) levelJSON.get("title");
        board = parseOneType(levelJSON,"board");
        lives = Math.round((long) levelJSON.get("lives"));
        rowsIndices = ((String) levelJSON.get("rows")).split("\\|");
        columnsIndices = ((String) levelJSON.get("columns")).split("\\|");
    }


    public long getWidth(){
        return width;
    }

    public long getHeight(){
        return height;
    }

    public String getTitle(){
        return title;
    }

    public int[][] getSolve(){
        return solve;
    }

    /**
     * Parse la solution du format JSON en un tableau à 2 dimensions utilisables
     * levelJSON.get("solve") renvoie une string de la forme "00010101"
     * 1 : case remplie, 0 case vide
     * On convertit la string du JSON en tableau
     *
     * @param levelJSON
     *          levelJSON est un JSONObject contenant les informations d'un niveau
     * @return
     *          le tableau représentant la solution du picross
     */
    private int[][] parseOneType(JSONObject levelJSON, String type) {
        String sol = (String) levelJSON.get(type);

        int[][] tab = new int[width][height];
        int k = 0;

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                tab[i][j] = Character.getNumericValue(sol.charAt(k++));

        return tab;
    }

    public String[] getRowsIndices() {
        return rowsIndices;
    }

    public String[] getColumnsIndices() {
        return columnsIndices;
    }


    public int[][] getBoard() {
        return board;
    }

    public int getLives() {return lives;}
}
