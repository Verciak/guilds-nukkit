package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ColorHelper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
@AllArgsConstructor
public class UnknownCommandListener implements Listener
{

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }


    public static ArrayList<String> registeredCommands;

    @EventHandler
    private void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        boolean valid = false;
        for (final String cmd : UnknownCommandListener.registeredCommands) {
            final String[] args = e.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + cmd) || args[0].equalsIgnoreCase("/:" + cmd)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            p.sendMessage(ColorHelper.colored("&4Komenda nie istnieje!"));
            e.setCancelled(true);
        }
    }

    static {
        UnknownCommandListener.registeredCommands = new ArrayList<String>();
    }
}

