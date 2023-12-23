package com.company.vehicule.moteur;

public enum TypeMoteur {

    DIESEL("Moteur DIESEL"),
    ESSENCE("Moteur ESSENCE"),
    HYBRIDE("Moteur HYBRIDE "),
    ELECTRIQUE("Moteur ELECTRIQUE");

    private String nom;

    TypeMoteur(String nom){
        this.nom = nom;
    }

    public String getNom(){return nom;}

}
