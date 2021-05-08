package eu.dkcode.guilds.runnable;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.helpers.DatabaseHelper;
import lombok.AllArgsConstructor;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: DatabaseRunnable
 **/


@AllArgsConstructor
public class DatabaseRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,this,0,20*90);
    }

    @Override
    public void run() {
        DatabaseHelper.synchronize();
    }

}
