package com.elenox.pvpbox.practice.commands;


import com.elenox.pvpbox.practice.PvpBoxPractice;
import com.elenox.pvpbox.practice.commands.moderateur.Freeze;
import com.elenox.pvpbox.practice.commands.moderateur.TpCommand;
import com.elenox.pvpbox.practice.commands.moderateur.UnFreeze;
import com.elenox.pvpbox.practice.commands.moderateur.Vanish;
import com.elenox.pvpbox.practice.commands.player.Spawn;

public class CommandManger {
    public void runCommand(){
        PvpBoxPractice.INSTANCE.getCommand("spawn").setExecutor(new Spawn());
        PvpBoxPractice.INSTANCE.getCommand("vanish").setExecutor(new Vanish());
        PvpBoxPractice.INSTANCE.getCommand("tp").setExecutor(new TpCommand());
        PvpBoxPractice.INSTANCE.getCommand("freeze").setExecutor(new Freeze());
        PvpBoxPractice.INSTANCE.getCommand("unfreeze").setExecutor(new UnFreeze());
    }
}
