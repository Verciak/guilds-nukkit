package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static eu.dkcode.guilds.helpers.MessageHelper.sendTitle;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: PlayerMoveListener
 **/

@AllArgsConstructor
public class PlayerMoveListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(player.getName()),guildFrom = Guild.get(event.getFrom()), guildTo = Guild.get(event.getTo());
        if(guildTo == guildFrom) return;
        String messageColor;
        if(guildTo == null){
            messageColor = guild == null ? "4" : guild.getAllies().contains(guildFrom.getTag()) ? "9" : guild.getTag().equals(guildFrom.getTag()) ? "2" : "4";
            sendTitle(player,"&"+messageColor+"Gildie","&"+messageColor+"Opuscicles teren gildii &8[&"+messageColor+guildFrom.getTag()+"&8]");
            return;
        }
        messageColor = guild == null ? "4" : guild.getAllies().contains(guildTo.getTag()) ? "9" : guild.getTag().equals(guildTo.getTag()) ? "2" : "4";
        sendTitle(player,"&"+messageColor+"Gildie","&"+messageColor+"Wkroczyles na teren gildii &8[&"+messageColor+guildTo.getTag()+"&8]");
    }
}
