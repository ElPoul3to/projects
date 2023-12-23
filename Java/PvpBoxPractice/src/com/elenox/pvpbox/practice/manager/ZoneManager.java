package com.elenox.pvpbox.practice.manager;

import com.elenox.api.block.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ZoneManager {
    private  static Location spawn = new Location(Bukkit.getWorld("world"),-274.5,65,100.5);
    public static Location getSpawn(){return spawn;}

    private static Location editKit = new Location(Bukkit.getWorld("world"),-264.5,64,107.5);
    public static Location getEditKit(){return editKit;}

    public static Cuboid cuboidEditKit = new Cuboid(new Location(Bukkit.getWorld("world"),-266.5,67,109.5),
            new Location(Bukkit.getWorld("world"),-259.5,64,105.5) );
}
