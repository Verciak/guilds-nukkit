package eu.dkcode.guilds.helpers;

import org.bukkit.Location;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: LocationHelper
 **/

public class LocationHelper {

    public static boolean isBlockLocation(Location location1, Location location2){
        return location1.getBlockX() == location2.getBlockX() && location1.getBlockY() == location2.getBlockY() && location1.getBlockZ() == location2.getBlockZ();
    }

}
