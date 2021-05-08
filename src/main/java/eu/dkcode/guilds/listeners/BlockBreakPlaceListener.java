package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.helpers.LocationHelper;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Date;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: BlockBreakPlaceListener
 **/

@AllArgsConstructor
public class BlockBreakPlaceListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        final Player player = event.getPlayer();
        final Guild pGuild = Guild.get(player.getName());
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())
                && !LocationHelper.isBlockLocation(event.getBlock().getLocation(), guild.getCenterLocation())) return;
        if(LocationHelper.isBlockLocation(event.getBlock().getLocation(), guild.getCenterLocation())
                && pGuild != null
                && !guild.getUuid().equals(pGuild.getUuid())){
            if(guild.getNextConquerDate().getTime() < System.currentTimeMillis()){
                guild.setLives(guild.getLives() - 1);
                guild.setNextConquerDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
                if(guild.getLives() == 0) guild.delete("Brak zyc!");
                if(pGuild.getLives() < 5) pGuild.setLives(pGuild.getLives() + 1);
                plugin.getServer().getOnlinePlayers().forEach(target -> target.sendMessage(colored("&cGildia &8[&4"+guild.getTag()+"&8] &cstracila jedo serce na rzecz gidlii &8[&4"+pGuild.getTag()+"&8]")));
            }
            else player.sendMessage(colored("&cWyglada na to ze ta gildia byla podbita w ostatnim czasie!"));
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())) return;
        event.setCancelled(true);
    }

}
