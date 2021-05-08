package eu.dkcode.guilds.helpers;

import eu.dkcode.guilds.objects.Guild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: GuildRoomHelper
 **/

public class GuildHelper {

    public static void createRoom(Guild guild){
        final Location center = new Location(Bukkit.getWorld("world"),guild.getCenterX(),guild.getCenterY(),guild.getCenterZ());

        final int minX = Math.min(center.getBlockX() + 3, center.getBlockX() - 3),
                maxX = Math.max(center.getBlockX() + 3, center.getBlockX() - 3),
                minZ = Math.min(center.getBlockZ() + 3, center.getBlockZ() - 3),
                maxZ = Math.max(center.getBlockZ() + 3, center.getBlockZ() - 3),
                minY = guild.getCenterY(),
                maxY = guild.getCenterY() + 3;

        for (int y = minY; y <= maxY; y++)
            for(int x = minX; x <= maxX; x++)
                for(int z = minZ; z <= maxZ; z++)
                    Bukkit.getWorld("world").getBlockAt(new Location(center.getWorld(),x,y,z)).setType(Material.AIR);

        Bukkit.getWorld("world").getBlockAt(center).setType(Material.SPONGE);

        center.setY(center.getY()-1);
        Bukkit.getWorld("world").getBlockAt(center).setType(Material.BEDROCK);
    }

    public static void removeRoom(Guild guild){
        final Location center = new Location(Bukkit.getWorld("world"),guild.getCenterX(),guild.getCenterY(),guild.getCenterZ());
        Bukkit.getWorld("world").getBlockAt(center).setType(Material.AIR);

        center.setY(center.getY()-1);
        Bukkit.getWorld("world").getBlockAt(center).setType(Material.AIR);

    }

}
