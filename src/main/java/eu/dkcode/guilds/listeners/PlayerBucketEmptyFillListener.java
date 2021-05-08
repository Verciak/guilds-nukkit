package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: PlayerBucketEmptyFillListener
 **/

@AllArgsConstructor
public class PlayerBucketEmptyFillListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlockClicked().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlockClicked().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())) return;
        event.setCancelled(true);
    }

}
