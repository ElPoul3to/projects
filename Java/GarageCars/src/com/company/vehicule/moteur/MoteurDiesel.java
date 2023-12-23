package com.company.vehicule.moteur;

public class MoteurDiesel extends Moteur {

    public MoteurDiesel(String cylindre, Double prix) {
        super( cylindre, prix);
        super.type = TypeMoteur.DIESEL;
    }
}
