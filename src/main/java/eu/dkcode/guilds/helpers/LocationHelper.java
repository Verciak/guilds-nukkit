package eu.dkcode.guilds.helpers;

import eu.dkcode.guilds.objects.configs.Config;
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

    public static double getDistanceMin(Location location1, Location location2){
        final double xDiff = Math.abs(location1.getBlockX() - location2.getBlockX()),
                zDiff = Math.abs(location1.getBlockZ() - location2.getBlockZ());
        return Math.min(xDiff,zDiff);
    }

    public static double getDistanceMax(Location location1, Location location2){
        final double xDiff = Math.abs(location1.getBlockX() - location2.getBlockX()),
                zDiff = Math.abs(location1.getBlockZ() - location2.getBlockZ());
        return Math.max(xDiff,zDiff);
    }

    public static double getBorderDistance(Location location){
        final double xDiff = Math.abs((Config.getInstance().worldBorder / 2) - Math.abs(location.getBlockX())),
                zDiff = Math.abs((Config.getInstance().worldBorder / 2) - Math.abs(location.getBlockZ()));
        return Math.min(xDiff,zDiff);
    }

}
