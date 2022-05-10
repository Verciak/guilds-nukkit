package pl.vertty.nomenhc.helpers;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Config;

import java.util.Set;
import java.util.stream.Collectors;


public class GuildHelper {

    public static void createRoom(Guild guild){
        final Location center = new Location(guild.getCenterX(),guild.getCenterY(),guild.getCenterZ());

        final int minX = Math.min(center.getFloorX() + 3, center.getFloorX() - 3),
                maxX = Math.max(center.getFloorX() + 3, center.getFloorX() - 3),
                minZ = Math.min(center.getFloorZ() + 3, center.getFloorZ() - 3),
                maxZ = Math.max(center.getFloorZ() + 3, center.getFloorZ() - 3),
                minY = guild.getCenterY(),
                maxY = guild.getCenterY() + 3;

        for (int y = minY; y <= maxY; y++)
            for(int x = minX; x <= maxX; x++)
                for(int z = minZ; z <= maxZ; z++) {
                    final Level la = Server.getInstance().getDefaultLevel();
                    la.setBlock(new Vector3(x, y, z), Block.get(0));
                }
        final Level la = Server.getInstance().getDefaultLevel();
        la.setBlock(new Vector3(center.getFloorX(), center.getY(), center.getFloorZ()), Block.get(Block.SPONGE));
        center.setY(center.getY()-1);
        la.setBlock(new Vector3(center.getFloorX(), center.getY(), center.getFloorZ()), Block.get(Block.BEDROCK));
    }

    public static void removeRoom(Guild guild){
        final Location center = new Location(guild.getCenterX(),guild.getCenterY(),guild.getCenterZ());
        final Level la = Server.getInstance().getDefaultLevel();
        la.setBlock(new Vector3(center.getFloorX(), center.getY(), center.getFloorZ()), Block.get(Block.AIR));
        center.setY(center.getY()-1);
        la.setBlock(new Vector3(center.getFloorX(), center.getY(), center.getFloorZ()), Block.get(Block.AIR));
    }

    public static boolean isTooCloseToGuild(Location location){
        return GuildHandler.getGuilds().stream().anyMatch(guild -> LocationHelper.getDistanceMin(location,guild.getCenterLocation()) < ((Config.getInstance().guildDefaultSize * 2) + 15));
    }

    public static Set<Player> getGuildOnlinePlayers(Guild guild){
        return Server.getInstance().getOnlinePlayers().values().stream()
                .filter(player -> guild.getMembers().contains(player.getName()))
                .collect(Collectors.toSet());
    }

}
