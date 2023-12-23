package com.company.vehicule.option;

import java.io.Serializable;

public class Climatisation implements Option, Serializable {
    @Override
    public Double getPrix() {
        return 347.3;
    }

    @Override
    public String toString() {
        return "Climatisation ("+this.getPrix()+" €)";
    }
}
