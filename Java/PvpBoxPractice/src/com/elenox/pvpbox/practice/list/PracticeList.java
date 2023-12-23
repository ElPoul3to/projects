package com.elenox.pvpbox.practice.list;

import java.util.ArrayList;

public class PracticeList<E> extends ArrayList{
    private String nom;

    public PracticeList(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }
}
