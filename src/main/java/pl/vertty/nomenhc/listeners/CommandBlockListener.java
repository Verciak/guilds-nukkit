package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandBlockListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String pcmd = e.getMessage();
        final Combat combat = CombatManager.getCombat(p);
        if (!p.hasPermission(Messages.getInstance().komendy_permission) && (combat != null && combat.hasFight())) {
            for (final String cmd : Messages.getInstance().komendy_pvp) {
                if (pcmd.toLowerCase().contains("/" + cmd)) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().komendy_error);
                    return;
                }
            }
        }
    }

}
