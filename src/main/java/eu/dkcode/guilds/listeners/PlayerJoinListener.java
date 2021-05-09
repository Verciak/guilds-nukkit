package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Account;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: PlayerJoinListener
 **/

@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final Account account = Account.get(player.getUniqueId());
        if(account == null) new Account(player);
    }
}
