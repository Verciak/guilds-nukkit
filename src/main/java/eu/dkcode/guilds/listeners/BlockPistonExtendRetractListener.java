package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: BlockPistonExtendRetractListener
 **/

@AllArgsConstructor
public class BlockPistonExtendRetractListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event){
        if(event.getBlocks().stream().noneMatch(block -> block.getType().equals(Material.SPONGE))) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event){
        if(event.getBlocks().stream().noneMatch(block -> block.getType().equals(Material.SPONGE))) return;
        event.setCancelled(true);
    }

}
