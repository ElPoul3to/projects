package com.company.vehicule.moteur;

public class MoteurHybride extends Moteur {

    public MoteurHybride(String cylindre, Double prix) {
        super(cylindre, prix);
        super.type = TypeMoteur.HYBRIDE;
    }
}
