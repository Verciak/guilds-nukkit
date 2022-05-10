package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.level.Location;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.objects.Teleport;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.helpers.ColorHelper;


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
        if (locFrom.getFloorX() == locTo.getFloorX() && locFrom.getFloorZ() == locTo.getFloorZ()) return;
        final Teleport teleport = Teleport.get(player.getUniqueId());
        if(teleport == null) return;
        if(!teleport.isTeleporting()) return;
        teleport.getTask().cancel();
        Teleport.remove(player.getUniqueId());
        player.sendMessage(ColorHelper.colored("&cTeleportacja zostala przerwana!"));
    }

}
