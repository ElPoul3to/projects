package com.company.role;

import com.company.Player;

public class Rodeur implements Role {
    public Rodeur() {
    }

    @Override
    public void basicalAttack(Player source, Player target) {
        target.setLife(target.getLife()-source.getAgility());
        if(target.getLife() > 0) {
            System.out.println("\r\n" + source.getId() + " utilise <Tir à l'Arc>, " + target.getId() +
                    " a désormais " + target.getLife() + " point(s) de vie.");
        }else{
            System.out.println("\r\n" + source.getId() + " utilise <Tir à l'Arc> sur "+target.getId()+".");
        }
    }

    @Override
    public void specialAttack(Player source, Player target) {
        source.setAgility(source.getAgility()+source.getLevel()/2);
        System.out.println("\r\n"+source.getId()+" utilise <Concentration>. Il est désormais à "+source.getAgility()+" d'agilité.");
    }

    @Override
    public void basicalSumUp(Player source, Player target) {
        if(target.getLife() - source.getAgility() <=0){
            System.out.println("- <Tir à l'Arc> (Attaque Basique). Vous pouvez infliger " + source.getAgility() + " dégat(s) sur " + target.getId() +
                    ". Par conséquent vous gagnerez la partie !");
        }else {
            System.out.println("- <Tir à l'Arc> (Attaque Basique). Vous pouvez infliger " + source.getAgility() + " dégat(s) sur " + target.getId() +
                    ". Il aura " + (target.getLife() - source.getAgility()) + " point(s) de vie.");
        }
    }

    @Override
    public void specialSumUp(Player source, Player target) {
        System.out.println("- <Concentration> (Attaque Spéciale). Vous pouvez gagné de l'agilité (votre niveau divisé par 2). Votre agilité sera à "+(source.getAgility()+source.getLevel()/2)+".");

    }
}