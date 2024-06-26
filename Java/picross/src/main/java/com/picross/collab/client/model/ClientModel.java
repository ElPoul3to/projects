package com.picross.collab.client.model;

import com.picross.collab.shared.Level;
import com.picross.collab.shared.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientModel {

    /*
    * Le client garde une copie de la grille actuelle, de la liste des erreurs et du nombre de vies restantes
    * */

    private final ArrayList<Position> positionOfErrorsLocal = new ArrayList<>();
    private int[][] playerBoardLocal;
    private int livesLeft;

    /**
     * Default constructor for the ClientModel class.
     */
    public ClientModel(Level level) {
        if(level.getBoard() == null){
            initLevelAndCreateLocalBoard(level);
            System.out.println("\nCréation board de 0\n");
        }else {
            setPlayerBoardLocal(level.getBoard());
            livesLeft = level.getLives();
            System.out.println("\nCréation avec la vraie board\n");
        }

    }

    /**
     * Initializes the level and creates a local board.
     *
     * @param level The Level object to be initialized.
     */
    public void initLevelAndCreateLocalBoard(Level level) {
        int width = (int) level.getWidth();
        int height = (int) level.getHeight();
        livesLeft = level.getLives();
        playerBoardLocal = level.getBoard();

        createEmptyBoard(width, height);
    }

    /**
     * Sets the local lives count.
     *
     * @param lives The number of lives to be set.
     */
    public void setLivesLocal(int lives) {
        livesLeft = lives;
    }

    /**
     * Gets the local lives count.
     *
     * @return The number of lives left.
     */
    public int getLivesLeft() {
        return livesLeft;
    }


    /**
     * Gets the local positions of errors.
     *
     * @return An ArrayList of Position objects representing the positions of errors.
     */
    public ArrayList<Position> getPositionOfErrorsLocal() {
        return positionOfErrorsLocal;
    }

    /**
     * Adds a position of error.
     *
     * @param position The Position object to be added.
     */
    public void addPositionOfError(Position position) {
        positionOfErrorsLocal.add(position);
    }

    /**
     * Updates the state of a position.
     *
     * @param position The Position object containing the coordinates of the update.
     * @param state The state to be set.
     */
    public void updatePosition(Position position, int state) {
        playerBoardLocal[position.x()][position.y()] = state;
    }

    /**
     * Gets the local player board.
     *
     * @return A 2D array representing the local player board.
     */
    public int[][] getPlayerBoardLocal() {
        return playerBoardLocal;
    }

    /**
     * Sets the local player board.
     *
     * @param board The 2D array to be set as the local player board.
     */
    public void setPlayerBoardLocal(int[][] board) {
        this.playerBoardLocal = board;
    }

    /**
     * Creates an empty board with the specified width and height.
     * This method initializes the local player board as a 2D array with the specified width and height.
     * It then fills the board with zeros, representing an empty state for each position.
     *
     * @param width The width of the board to be created.
     * @param height The height of the board to be created.
     */
    private void createEmptyBoard(int width, int height){
        this.playerBoardLocal = new int[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++) this.playerBoardLocal[i][j]=0;
        }
    }

}
