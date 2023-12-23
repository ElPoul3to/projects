package com.company.vehicule.moteur;

public class MoteurElectrique extends Moteur {
    public MoteurElectrique(String cylindre, Double prix) {
        super(cylindre, prix);
        super.type = TypeMoteur.ELECTRIQUE;
    }

}
