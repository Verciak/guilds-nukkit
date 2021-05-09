package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: EntityDamageByEntityListener
 **/

@AllArgsConstructor
public class EntityDamageByEntityListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        final Entity entity = event.getEntity(), damageEntity = event.getDamager();
        if(!(entity instanceof Player) || !(damageEntity instanceof Player)) return;
        final Player player = (Player) entity, damagePlayer = (Player) damageEntity;
        final Guild pGuild = Guild.get(player.getName()), dGuild = Guild.get(damagePlayer.getName());
        if(pGuild == null || dGuild == null) return;
        if(pGuild.getUuid().equals(dGuild.getUuid()) && !pGuild.getGuildPvp()) {
            event.setDamage(0.0);
            event.setCancelled(true);
            return;
        }
        if(!pGuild.getAllies().contains(dGuild.getUuid())) return;
        if(dGuild.getAllyPvp()) return;
        event.setDamage(0.0);
        event.setCancelled(true);
    }

}
