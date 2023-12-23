package com.company.vehicule.option;

import java.io.Serializable;

public class BarreDeToit implements Option, Serializable {
    @Override
    public Double getPrix() {
        return 29.9;
    }

    @Override
    public String toString() {
        return "Barre de toit ("+this.getPrix()+" €)";
    }
}
