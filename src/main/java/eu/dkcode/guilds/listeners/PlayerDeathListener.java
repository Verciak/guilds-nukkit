package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: PlayerDeathListener
 **/

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        final Player player = event.getEntity(), killer = event.getEntity().getKiller();
        if(killer == null) return;
        final Guild pGuild = Guild.get(player.getName()), kGuild = Guild.get(killer.getName());

        int guildPointsAdd = 120, guildPointsTake = -120; // todo: count on behalf off player rank

        if(pGuild != null && kGuild != null && (pGuild.getUuid().equals(kGuild.getUuid()) || pGuild.getAllies().contains(kGuild.getUuid()))){
            guildPointsAdd = -50;
            guildPointsTake = 0;
        }

        if(pGuild != null) pGuild.statIncrement(guildPointsAdd, 1, 0);
        if(kGuild != null) kGuild.statIncrement(guildPointsTake, 0, 1);
    }

}
