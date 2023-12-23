package com.company.vehicule.moteur;

import java.io.Serializable;

public class Moteur implements Serializable {
    protected TypeMoteur type;
    private String cylindre;
    private Double prix;

    public Moteur(String cylindre, Double prix){
        this.type = null;
        this.cylindre = cylindre;
        this.prix = prix;
    }

    public Double getPrix() {return prix;}
    public String getCylindre(){return cylindre;}

    public String toString(){
        return " "+this.type.getNom()+" "+this.getCylindre()+" ("+this.getPrix()+" €) ";
    }
}
