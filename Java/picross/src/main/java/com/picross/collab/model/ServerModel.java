package com.picross.collab.model;

import com.picross.collab.shared.Position;
import com.picross.collab.shared.Level;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerModel {

    public JSONObject levelJSON;
    private final ArrayList<Position> positionOfErrors = new ArrayList<>();
    private int[][] solvedBoard;
    private int[][] serverBoard;

    public ServerModel() {
    }

    public void initLevelAndCreateBoard(Level dp) {
        System.out.println("\nLevel received, initializing board...");
        int width = (int) dp.getWidth();
        int height = (int) dp.getHeight();
        this.solvedBoard = dp.getSolve();

        createBoard(width, height);
    }

    private int livesLeft = 5;

    public void initLives(int lives) {
        livesLeft = lives;
    }

    public int getLivesLeft() {
        return livesLeft;
    }


    /* positionsOfErrors : le serveur se souvient des erreurs faites, et il avertit les clients : nouvelle erreur faite ici, ne pas accepter de nouveau clic sur cette case*/
    public ArrayList<Position> getPositionOfErrors() {
        return positionOfErrors;
    }

    public void addPositionOfError(Position position) {
        positionOfErrors.add(position);
    }

    public void updateBoard(int coordX, int coordY, int state) {
        serverBoard[coordX][coordY] = state;
    }

    // TODO : Idéalement on devrait avoir des tests unitaires pour toute cette classe
    // Junit : facilite le travail de ouf
    // TODO : METTRE EN PLACE 2 TESTS UNITAIRES (test Faux et Vrai fonctionnent)
    public boolean checkTile(int coordX, int coordY) {
        /* Renvoie si la case du joueur correspond à la solution  */
        return solvedBoard[coordX][coordY] == 1;
    }

    public int[][] getServerBoard() {
        return serverBoard;
    }

    /**
     * renvoie la board sous forme d'une string constitué de tous les éléments à la chaine
     * @return
     *          string constitué de tous les éléments de la board à la chaine
     */
    public String getServerBoardString(){
        String res="";
        for(int i=0; i < this.serverBoard.length; i++){
            for(int j=0; j<this.serverBoard[0].length; j++){
                res += serverBoard[i][j];
            }
        }
        return res;
    }

    public int[][] getSolvedBoard() {
        return solvedBoard;
    }

    /**
     * Créer une board selon une largeur/longueur
     *
     * @param width
     * @param height
     */
    private void createBoard(int width, int height){
        this.serverBoard = new int[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                this.serverBoard[i][j]=0;
            }
        }
    }

    /**
     * Vérifie si la board du joueur est résolue
     *
     * @return
     *          le booléen correspondant
     */
    public boolean isSolved() {
        return Arrays.deepEquals(serverBoard, solvedBoard);
    }

    public void lostLife() {
        livesLeft--;
    }

    /**
     * Met à jour levelJSON avec la bonne board du server
     */
    public void updateJSON(){
        String currentBoard = "";
        for (int i = 0; i < this.serverBoard.length; i++ ){
            for (int j = 0; j < this.serverBoard[0].length; j++) {
                currentBoard+= (serverBoard[i][j]);
            }
        }
        levelJSON.put("board", currentBoard);
        System.out.println("Mise à jour du JSON "+levelJSON.toJSONString()+"\n");
    }
}
