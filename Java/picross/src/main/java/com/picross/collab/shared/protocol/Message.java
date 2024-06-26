package com.picross.collab.shared.protocol;

/**
 * Gestion du protocole réseau
 * Les informations transises seront de la forme suivante: type$taille$donnees avec type et taille des int
 * @author Thomas Fargues
 */

public class Message {
    private String type ="";
    private String size ="";
    private String data ="";


    public static final int LEVEL_DIGIT_TYPE = 0;
    public static final int LEVEL_DATA_TYPE = 1;
    public static final int CLICK_SENT_TYPE = 2;
    public static final int CLICK_FEEDBACK_TYPE = 3;
    public static final int NEW_PLAYER_TYPE = 4;
    public static final int GAME_NOT_CREATED_ERROR = 5;

    public Message(){
    }
    public void reset(){
        this.data ="";
    }
    public void readMsg(String msg){
        //Si il y a un header dans le paquet, cela veut dire que c'est un nouveau message. Donc rénitilisation des variables
        if(msg.contains("$")) parseMsg(msg);
        else addData(msg);
    }

    /**
     * Lecture du premier buffer arrivé
     * @param msg
     */
    private void parseMsg(String msg) {
        String[] parts = msg.split("\\$");

        if (parts.length >= 2) {
            type = parts[0];
            size = parts[1];
        }
    }

    /**
     * ajoute les données à la variable donnees
     * @param msg
     */
    private void addData(String msg){
        data +=msg;
    }

    public String getData() {
        return data;
    }

    /**
     * Retourne la taille des donnees en la convertissant en entier
     * @return
     *          la taille des données
     */
    public int getSize() {
        return Integer.parseInt(size);
    }

    /**
     * Retourne le type des donnees en le convertissant en entier
     * @return
     *          le type des données (voir variable plus haut)
     */
    public int getType() {
        return Integer.parseInt(type);
    }
}

