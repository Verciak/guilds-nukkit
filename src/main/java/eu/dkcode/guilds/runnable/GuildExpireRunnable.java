package eu.dkcode.guilds.runnable;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.handlers.GuildHandler;
import lombok.AllArgsConstructor;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: GuildExpireRunnable
 **/

@AllArgsConstructor
public class GuildExpireRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,this,0,20*120);
    }

    @Override
    public void run() {
        GuildHandler.getGuilds().forEach(guild -> {
            if(guild.getExpireDate().getTime() > System.currentTimeMillis()) return;
            guild.delete("Brak oplaty!");
            // todo: remove guild message
        });
    }
}
