package com.company;

import com.company.role.Guerrier;
import com.company.role.Mage;
import com.company.role.Rodeur;
import com.company.role.Role;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    Scanner sc;
    private String id;
    private int level;
    private int life;
    private int strengh;
    private int agility;
    private int intelligence;
    private int lifeOrigin;
    private String nameCaracter;
    private Role role;

    public Player(String id) {
        this.sc = new Scanner(System.in);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public int getLifeOrigin() {
        return this.lifeOrigin;
    }

    public int getLevel() {
        return this.level;
    }

    public int getLife() {
        return this.life;
    }

    public int getStrengh() {
        return this.strengh;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public String getNameCaracter() {
        return this.nameCaracter;
    }

    public Role getRole() {
        return this.role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setStrengh(int strengh) {
        this.strengh = strengh;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }


    public void chooseName() {
        do {
            System.out.println("\r\nBonjour nouveau Joueur, avant de rentrer dans l'arène, veuillez  choisir un pseudo, s'il vous plait.");
            setId(this.sc.nextLine());
        } while (id.length() <= 0);
    }

    public void chooseCarc() {
        String str = "";

        while (!str.equals("1") && !str.equals("2") && !str.equals("3")) {
            System.out.println("\r\n" + this.getId() + ", choisissez un personnage: 1.Guerrier; 2.Rodeur; 3.Mage.");
            str = this.sc.nextLine();
            if (str.equals("1")) {
                this.role = new Guerrier();
                this.nameCaracter = "Guerrier";
            } else if (str.equals("2")) {
                this.role = new Rodeur();
                this.nameCaracter = "Rodeur";
            } else if (str.equals("3")) {
                this.role = new Mage();
                this.nameCaracter = "Mage";
            } else {
                System.out.println("Veuillez indiquer un des 3 chiffre ! S.V.P");
            }
        }

    }

    public void chooseFeature() {
        this.setLevel(this.chooseLevel());
        int perks = this.getLevel();
        perks = this.chooseStrength(perks);
        if (perks == 0) {
            this.setAgility(perks);
        } else {
            perks = this.chooseAgility(perks);
        }
        this.setIntelligence(perks);
        this.setLife(this.getLevel() * 5);
        this.lifeOrigin = this.getLife();
    }

    private int chooseLevel() {
        int c = -1;
        int level = 0;
        while (c < 1) {
            try {
                if (level == -1) System.out.println("Problème de niveaux !");
                System.out.println("\r\nChoisissez le niveau de votre personnage." +
                        " \r\nAttention: Le niveau est égal à la force + l'agilité + l'intelligence ! Votre niveau doit être compris entre 0 et 100. " +
                        "\r\nSi il vous reste des points, ils seront verser pour l'intelligence !" + "\r\nAussi, la vie est égale à 5 fois le niveau !");
                level = this.sc.nextInt();
                if (level < 0 || level > 100) {
                    level = -1;
                } else {
                    return level;
                }
            } catch (InputMismatchException e) {
                System.out.println("Vous devez mettre un nombre !");
                sc.next();
            }
        }
        return level;
    }

    private int chooseStrength(int perks) {
        int str;
        for (str = -1; str < 0 || str > perks; str = this.sc.nextInt()) {
            System.out.println("\r\n Choisissez la force de votre personnage. Vous pouvez choisir maximum: " + perks);
        }

        this.setStrengh(str);
        return perks - str;
    }

    private int chooseAgility(int perks) {
        int agi;
        for (agi = -1; agi < 0 || agi > perks; agi = this.sc.nextInt()) {
            System.out.println("\r\n Choisissez l'agilité de votre personnage. Vous pouvez choisir maximum: " + perks);
        }

        this.setAgility(agi);
        return perks - agi;
    }

    public void choiseAttack(Player source, Player target) {
        String rep = "";

        while (!rep.equals("1") && !rep.equals("2")) {
            rep = this.sc.next();
            if (rep.equals("1")) {
                this.role.basicalAttack(source, target);
            } else if (rep.equals("2")) {
                this.role.specialAttack(source, target);
            } else {
                System.out.println("\r\nVeuillez indiquez un des 2 chiffres!");
            }
        }

    }
}
