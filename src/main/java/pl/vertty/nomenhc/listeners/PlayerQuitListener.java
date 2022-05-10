package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PlayerQuitListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKick(final PlayerKickEvent e) {
        quitGame(e.getPlayer());
        e.setQuitMessage("");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(final PlayerQuitEvent e) {
        quitGame(e.getPlayer());
        e.setQuitMessage("");
    }

    public static void quitGame(final Player p) {
        final Combat combat = CombatManager.getCombat(p);
        if (!p.getServer().isRunning()) {
            return;
        }
        if(combat == null){
            return;
        }
        if (!combat.hasFight()) {
            return;
        }
        p.setHealth(0.0f);
        combat.setLastAttactTime(0L);
        combat.setLastAsystTime(0L);
        combat.setLastAsystPlayer(null);
        combat.setLastAttactkPlayer(null);
        ChatUtil.sendMessage(Server.getInstance().getOnlinePlayers().values(), Messages.getInstance().wiadomosci_chatlogout.replace("{NICK}", p.getName()));
    }
}
