package pl.vertty.nomenhc.runnable;

import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.DatabaseHelper;


@AllArgsConstructor
public class DatabaseRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(plugin,this,0,20*90);
    }

    @Override
    public void run() {
        DatabaseHelper.synchronize();
    }

}
