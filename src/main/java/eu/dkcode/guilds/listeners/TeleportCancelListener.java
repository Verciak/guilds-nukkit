package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Teleport;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: TeleportCancelListener
 **/

@AllArgsConstructor
public class TeleportCancelListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final Location locFrom = event.getFrom(), locTo = event.getTo();
        if (locFrom.getBlockX() == locTo.getBlockX() && locFrom.getBlockZ() == locTo.getBlockZ()) return;
        final Teleport teleport = Teleport.get(player.getUniqueId());
        if(teleport == null) return;
        if(!teleport.isTeleporting()) return;
        teleport.getTask().cancel();
        Teleport.remove(player.getUniqueId());
        player.sendMessage(colored("&cTeleportacja zostala przerwana!"));
    }

}
