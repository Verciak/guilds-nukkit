package eu.dkcode.guilds.listeners;

import com.google.common.collect.Sets;
import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.helpers.RandomHelper;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: Kacper 'DeeKaPPy' HorbacenterZ
 * @Created 07.05.2021
 * @Class: EntityExplodeListener
 **/

@AllArgsConstructor
public class EntityExplodeListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event){
        event.setCancelled(true);

        new ArrayList<Location>(){{
            final int centerX = event.getLocation().getBlockX(),
                    centerY = event.getLocation().getBlockY(),
                    centerZ = event.getLocation().getBlockZ();

            for (int x = centerX - 5; x <= centerX + 5; x++)
                for (int y = (centerY - 5); y < (centerY + 5); y++)
                    for (int z = centerZ - 5; z <= centerZ + 5; z++)
                        if ((centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + ((centerY - y) * (centerY - y)) < 5 * 5)
                            add(new Location(event.getLocation().getWorld(), x, y, z));
        }}.forEach(block -> {
            final Guild guild = Guild.get(block);
            if((guild != null
                    && guild.getTntProtectionExpireDate().getTime() >= System.currentTimeMillis())
                    || block.getBlock().getType().equals(Material.SPONGE)) return;
            final Material material = block.getBlock().getType();
            switch (material){
                case BEDROCK: return;
                case ENDER_CHEST:
                case OBSIDIAN:{
                    if(RandomHelper.getChance(40)) {
                        block.getBlock().setType(Material.AIR);
                    }
                    return;
                }
                case WATER:
                case LAVA:{
                    if(RandomHelper.getChance(60)) block.getBlock().setType(Material.AIR);
                    return;
                }
                default: if(RandomHelper.getChance(90)) block.getBlock().setType(Material.AIR);
            }
        });
    }

}
