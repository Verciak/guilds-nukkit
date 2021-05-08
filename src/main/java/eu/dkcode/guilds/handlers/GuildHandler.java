package eu.dkcode.guilds.handlers;

import com.google.common.collect.Sets;
import eu.dkcode.guilds.objects.Guild;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Set;
import java.util.UUID;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: GuildHandler
 **/

public class GuildHandler {

    @Getter
    private static final Set<Guild> guilds = Sets.newConcurrentHashSet();

    protected void add(Guild guild){
        guilds.add(guild);
    }

    protected void remove(Guild guild){
        guilds.remove(guild);
    }

    public static Guild get(UUID uuid){
        return guilds.stream().filter(guild -> guild.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public static Guild getByTag(String tag){
        return guilds.stream().filter(guild -> guild.getTag().equals(tag)).findFirst().orElse(null);
    }

    public static Guild get(String member){
        return guilds.stream().filter(guild -> guild.getMembers().contains(member)).findFirst().orElse(null);
    }

    public static Guild get(Location location){
        return guilds.stream().filter(guild -> guild.isOnCuboid(location)).findFirst().orElse(null);
    }

}
