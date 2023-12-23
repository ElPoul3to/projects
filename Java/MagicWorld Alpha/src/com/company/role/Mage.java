package com.company.role;

import com.company.Player;

public class Mage implements Role {
    public Mage() { }

    @Override
    public void basicalAttack(Player source, Player target) {
        target.setLife(target.getLife()-source.getIntelligence());
        if(target.getLife() > 0) {
            System.out.println("\r\n" + source.getId() + " utilise <Boule de Feu>, " + target.getId() +
                    " a désormais " + target.getLife() + " point(s) de vie.");
        }else{
            System.out.println("\r\n" + source.getId() + " utilise <Boule de Feu> sur "+target.getId()+".");
        }
    }

    @Override
    public void specialAttack(Player source, Player target) {
        source.setLife(source.getLife()+source.getIntelligence()*2);
        if(source.getLife() > source.getLifeOrigin()){
            source.setLife(source.getLifeOrigin());
        }
        System.out.println("\r\n"+source.getId()+" utilise <Soin>. Il a désormais "+source.getLife()+" point(s) de vie.");
    }

    @Override
    public void basicalSumUp(Player source, Player target) {
        if(target.getLife() - source.getIntelligence() <= 0) {
            System.out.println("- <Boule de Feu> (Attaque Basique). Vous pouvez infliger " + source.getIntelligence() + " dégat(s) sur " + target.getId() +
                    ". Par conséquent vous gagnerez la partie !");
        }else{
            System.out.println("- <Boule de Feu> (Attaque Basique). Vous pouvez infliger " + source.getIntelligence() + " dégat(s) sur " + target.getId() +
                    ". Il aura " + (target.getLife() - source.getIntelligence()) + " point(s) de vie.");
        }
    }

    @Override
    public void specialSumUp(Player source, Player target) {
        int life = 0;
        life = source.getLife()+source.getIntelligence()*2;
        if(life > source.getLifeOrigin()){
            life = source.getLifeOrigin();
        }

        System.out.println("- <Soin> (Attaque Spéciale). Vous pouvez regagner de la vie (votre intelligence fois 2). Vous aurez alors "+life+" point(s) de vie.");
    }
}
