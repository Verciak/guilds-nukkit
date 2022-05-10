package pl.vertty.nomenhc.runnable;

import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.GuildHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuildExpireRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(plugin,this,0,20*120);
    }

    @Override
    public void run() {
        GuildHandler.getGuilds().forEach(guild -> {
            if(guild.getExpireDate().getTime() > System.currentTimeMillis()) return;
            guild.delete("Brak oplaty!");
        });
    }
}
