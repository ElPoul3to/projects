package com.company.vehicule;

import java.io.Serializable;

public enum Marque implements Serializable {

    RENO("Reno"),
    PIGEOT("Pigot") ,
    TROEN("Troen");


    private String nom;

    Marque(String nom) {
        this.nom = nom;

    }

    public String getNom() {return nom;}
}
