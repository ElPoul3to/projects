package com.company.role;

import com.company.Player;

public class Guerrier implements Role {
    public Guerrier() {
    }

    @Override
    public void basicalAttack(Player source, Player target) {
        target.setLife(target.getLife() - source.getStrengh());
        if(target.getLife() > 0) {
            System.out.println("\r\n" + source.getId() + " utilise <Coup d'Épée>, " + target.getId() +
                    " a désormais " + target.getLife() + " point(s) de vie.");
        }else {
            System.out.println("\r\n" + source.getId() + " utilise <Coup d'Épée> sur "+target.getId()+".");
        }
    }

    @Override
    public void specialAttack(Player source, Player target) {
        target.setLife(target.getLife() - source.getStrengh() * 2);
        source.setStrengh(source.getStrengh() / 2);
        if(target.getLife() > 0) {
            System.out.println("\r\n" + source.getId() + " utilise <Coup de Rage>, " + target.getId() +
                    " a désormais " + target.getLife() + " point(s) de vie et la force de " + source.getId() + " est divisée par 2, c'est à dire " + source.getStrengh() + ".");
        }else{
            System.out.println("\r\n" + source.getId() + " utilise <Coup de Rage> sur "+target.getId()+".");
        }
    }

    @Override
    public void basicalSumUp(Player source, Player target) {
        if(target.getLife() - source.getStrengh() <= 0){
            System.out.println("- <Coup d'Épée> (Attaque Basique). Vous pouvez infliger "+source.getStrengh()+" dégat(s) sur "+target.getId()+
                    ". Par conséquent vous gagnerez la partie !");
        }else{
            System.out.println("- <Coup d'Épée> (Attaque Basique). Vous pouvez infliger "+source.getStrengh()+" dégat(s) sur "+target.getId()+
                    ". Il aura "+ (target.getLife() - source.getStrengh())+" point(s) de vie.");
        }

    }

    @Override
    public void specialSumUp(Player source, Player target) {
        if((target.getLife()-source.getStrengh()*2) <= 0){
            System.out.println("- <Coup de Rage> (Attaque Spéciale). Vous pouvez infliger "+source.getStrengh()*2+" dégat(s) sur "+target.getId()+
                    ". Par conséquent vous gagnerez la partie !");
        }else{
            System.out.println("- <Coup de Rage> (Attaque Spéciale). Vous pouvez infliger "+source.getStrengh()*2+" dégat(s) sur "+target.getId()+
                    ". Il aura "+(target.getLife()-source.getStrengh()*2)+" point(s) de vie. En revanche, votre force sera divié par 2. Elle sera à "+source.getStrengh()/2+".");
        }

    }
}