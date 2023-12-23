package com.company;

import com.company.vehicule.Vehicule;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Garage {
    private List<Vehicule> voitures = new ArrayList<>();
    private int i = 0;


    public Garage(){
        File file = new File("garage.txt");
        if (file.length() <= 0) {
            System.out.println("Aucune voiture sauvegardée");
            this.setI(1);
        }
    }

    public int getI(){return i;}
    public void setI(int i){this.i =i;}

    public String toString(){
        String listVoiture ="";
        listVoiture += "\r\n************************";
        listVoiture += "\r\n  Garage Openclassroom  ";
        listVoiture += "\r\n************************";
        return listVoiture;
    }

    public void addVoiture(Vehicule voit)  {
        voitures.add(voit);

        ObjectInputStream ois;
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("garage.txt"))));
            oos.writeObject(voit);
            oos.close();

            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("garage.txt"))));

            try {
                if(this.getI() !=1) System.out.println(((Vehicule)ois.readObject()).toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


