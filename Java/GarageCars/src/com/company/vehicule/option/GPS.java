package com.company.vehicule.option;

import java.io.Serializable;

public class GPS implements Option, Serializable {
    @Override
    public Double getPrix() {
        return 113.5;
    }

    @Override
    public String toString() {
        return "GPS ("+this.getPrix()+" €)";
    }
}
