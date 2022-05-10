package pl.vertty.nomenhc.listeners;

import cn.nukkit.event.Listener;
import pl.vertty.nomenhc.GuildPlugin;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class WeatherListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

//    @EventHandler
//    public void onWeatherChange(WeatherChangeEvent e) {
//        e.setCancelled(true);
//    }
}
