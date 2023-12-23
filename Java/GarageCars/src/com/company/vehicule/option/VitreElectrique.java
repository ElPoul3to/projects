package com.company.vehicule.option;

import java.io.Serializable;

public class VitreElectrique implements Option, Serializable {

    @Override
    public Double getPrix() {
        return 212.35;
    }

    @Override
    public String toString() {
        return "Vitre électrique ("+this.getPrix()+" €)";
    }
}
