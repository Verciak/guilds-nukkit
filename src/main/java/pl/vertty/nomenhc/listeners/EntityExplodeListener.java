package pl.vertty.nomenhc.listeners;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.Guild;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

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
            final int centerX = event.getEntity().getLocation().getFloorX(),
                    centerY = event.getEntity().getLocation().getFloorY(),
                    centerZ = event.getEntity().getLocation().getFloorZ();

            for (int x = centerX - 5; x <= centerX + 5; x++)
                for (int y = (centerY - 5); y < (centerY + 5); y++)
                    for (int z = centerZ - 5; z <= centerZ + 5; z++)
                        if ((centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + ((centerY - y) * (centerY - y)) < 5 * 5)
                            add(new Location(x, y, z));
        }}.forEach(block -> {
            final Guild guild = Guild.get(block);
            if((guild != null
                    && guild.getTntProtectionExpireDate().getTime() >= System.currentTimeMillis())
                    || block.getLevelBlock().getId() == (Block.SPONGE)) return;
            final Block material = block.getLevelBlock();
            if (Block.get(Block.BEDROCK).equals(material)) {
                return;
            } else if (Block.get(Block.ENDER_CHEST).equals(material) || Block.get(Block.OBSIDIAN).equals(material)) {
                if (RandomHelper.getChance(40)) {
                    final Level l = Server.getInstance().getDefaultLevel();
                    l.setBlock(new Vector3(block.getX(), block.getY(), block.getZ()), Block.get(0));
                }
                return;
            } else if (Block.get(Block.WATER).equals(material) || Block.get(Block.LAVA).equals(material)) {
                if (RandomHelper.getChance(60)) {
                    final Level l = Server.getInstance().getDefaultLevel();
                    l.setBlock(new Vector3(block.getX(), block.getY(), block.getZ()), Block.get(0));
                }
                return;
            } else {
                if (RandomHelper.getChance(90)) {
                    final Level l = Server.getInstance().getDefaultLevel();
                    l.setBlock(new Vector3(block.getX(), block.getY(), block.getZ()), Block.get(0));
                }
            }
        });
    }

}
