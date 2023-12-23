package com.company.vehicule;

import com.company.vehicule.moteur.Moteur;
import com.company.vehicule.option.Option;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vehicule implements Serializable {

    private Double prix;
    private String nom;
    private List<Option> options = new ArrayList<Option>();
    protected Marque nomMarque;
    private Moteur moteur;

    public Vehicule(){
        this.prix = 0.0;
        this.nom = "";
        this.options = new ArrayList<Option>();
        this.nomMarque = null;

    }
    public Double getPrix() {return prix;}
    public Marque getNomMarque() {return nomMarque;}
    public String getNom(){return nom;}
    public List<Option> getOptions() {return options;}
    public Moteur getMoteur(){return moteur;}

    public void setPrix (Double prix){this.prix = prix;}
    public void setMoteur(Moteur moteur){this.moteur = moteur;}
    public void setMarque(Marque marque){this.nomMarque = marque;}
    public void setNom(String nom){this.nom = nom;}

    public String toString(){
        return "+ Voiture "+ this.getNomMarque()+" "+this.getNom()+this.getMoteur().toString()+this.getOptions()+". Prix total : "+this.getPrixTotal()+".";
    }

    public void addOption(Option opt){
        this.options.add(opt);
    }

    public Double getPrixTotal(){
        double pt = 0.0;
        for (int i = 0 ; i < this.getOptions().size(); i++){
            pt += this.getOptions().get(i).getPrix();
        }
        this.setPrix((this.moteur.getPrix() + pt));
        return this.getPrix();
    }
}
