package com.company.vehicule.option;

import java.io.Serializable;

public class SiegeChauffant implements Option, Serializable {
    @Override
    public Double getPrix() {
        return 562.9;
    }

    @Override
    public String toString() {
        return "Siège chauffant ("+this.getPrix()+" €)";
    }
}
