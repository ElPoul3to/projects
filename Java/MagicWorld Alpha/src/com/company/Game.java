package com.company;

import java.util.Scanner;

public class Game {
    Scanner sc = new Scanner(System.in);
    private Player player1 = new Player("");
    private Player player2 = new Player("");

    public Game() {
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void rules(){
        String str ="";
        while(!(str.equalsIgnoreCase("Oui") || str.equalsIgnoreCase("Non"))) {
            System.out.println("Bienvenue dans MagicWorld. Avant de commancer, voulez vous avoir les règles ? (Oui / Non)");
            str = sc.nextLine();
            if (str.equalsIgnoreCase("Oui")) {
                System.out.println("\r\nMagicWorld est un jeu de comabt épique dnas le but de tuer son adversaire." +
                        " Quand vous entrez dans l'arène, vous pouvez choisir entre 3 rôles: Guerrier, Rôdeur et Mage. " +
                        "\r\nAprès vous choisissez le niveau, la force, l'agilité et l'intelligence de votre personnage. La vie de votre personnage sera alors égale à 5 fois son niveau. " +
                        "\r\nEnsuite il vous suffit de chosir entre 2 types d'attaque: l'Attaque Basique et l'Attaque Spéciale." +
                        "\r\nCependant pour chaque personnage il y a des spécificités. Le Guerrier utilise la force, le Rôdeur l'agilité et le Mage l'intelligence." +
                        "\r\nMaintenant que vous avez les règles, vous n'avez plus qu'a jouer. Bon jeu à tous ;) !");
            }else if(str.equalsIgnoreCase("Non")){
                System.out.println("Bon jeu à tous ;) !");
            }else {
                System.out.println("Veuillez répondre par Oui ou par Non ! S.V.P");
            }
        }
    }


    public void runGame() {
        this.rules();
        this.player1.chooseName();
        this.player1.chooseCarc();
        this.player1.chooseFeature();
        System.out.println("\n\rCréation du personnage de "+this.player1.getId()+". C'est un " + this.player1.getNameCaracter() + ". Il a " + this.player1.getLevel() + " niveaux, " + this.player1.getStrengh() +
                " de force, " + this.player1.getAgility() + " d'agilité et " + this.player1.getIntelligence() + " d'intelligence et "+this.player1.getLife()+" point(s) de vie.");
        this.player2.chooseName();
        this.player2.chooseCarc();
        this.player2.chooseFeature();
        System.out.println("\n\rCréation du personnage de "+this.player1.getId()+". C'est un " + this.player2.getNameCaracter() + ". Il a " + this.player2.getLevel() + " niveaux, " + this.player2.getStrengh() +
                " de force, " + this.player2.getAgility() + " d'agilité et " + this.player2.getIntelligence() + " d'intelligence et "+this.player2.getLife()+" point(s) de vie.");
        this.actions();
        this.end();
    }

    public void actions() {
        Player currentPlayer = this.getPlayer1();

        Player change;
        for(Player otherPlayer = this.getPlayer2(); this.player1.getLife() > 0 && this.player2.getLife() > 0; otherPlayer = change) {
            System.out.println("\r\n\r\nC'est au Joueur " + currentPlayer.getId() + " de jouer. Vous avez le choix entre deux types d'attaque:");
            currentPlayer.getRole().basicalSumUp(currentPlayer,otherPlayer);
            currentPlayer.getRole().specialSumUp(currentPlayer,otherPlayer);
            currentPlayer.choiseAttack(currentPlayer, otherPlayer);
            change = currentPlayer;
            currentPlayer = otherPlayer;
        }

    }

    public void end() {
        if (this.player1.getLife() <= 0) {
            System.out.println("\r\n"+this.getPlayer2().getId()+" a gagné la partie!");
        } else if (this.player2.getLife() <= 0) {
            System.out.println("\r\n"+ this.getPlayer1().getId()+" a gagné la partie!");
        }

    }
}
