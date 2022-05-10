package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityMinecartHopper;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryOpenListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void FrameRotate(PlayerInteractEntityEvent e) {
        final Player p = (Player) e.getPlayer();
        final Combat combat = CombatManager.getCombat(p);
        if (combat != null && combat.hasFight()) {
            Entity entity = e.getEntity();
            if(entity instanceof EntityMinecartHopper) {
                if (Config.getInstance().HOPPER_MINECART_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().HOPPER_MINECART_MESSAGE);
                }
            }
        }
    }

    @EventHandler
    public void open(final PlayerInteractEvent e) {
        final Player p = (Player) e.getPlayer();
        final Combat combat = CombatManager.getCombat(p);
        if (combat != null && combat.hasFight()) {
            if (e.getBlock().getId() == Block.ANVIL) {
                if (Config.getInstance().ANVIL_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().ANVIL_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.BEACON) {
                if (Config.getInstance().BEACON_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().BEACON_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.BREWING_STAND_BLOCK) {
                if (Config.getInstance().BREWING_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().BREWING_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.CHEST) {
                if (Config.getInstance().CHEST_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().CHEST_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.DISPENSER) {
                if (Config.getInstance().DISPENSER_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().DISPENSER_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.DROPPER) {
                if (Config.getInstance().DROPPER_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().DROPPER_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.ENCHANTMENT_TABLE) {
                if (Config.getInstance().ENCHANTING_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().ENCHANTING_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.ENDER_CHEST) {
                if (Config.getInstance().ENDER_CHEST_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().ENDER_CHEST_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.FURNACE) {
                if (Config.getInstance().FURNACE_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().FURNACE_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.HOPPER_BLOCK) {
                if (Config.getInstance().HOPPER_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().HOPPER_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.WORKBENCH) {
                if (Config.getInstance().WORKBENCH_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().WORKBENCH_MESSAGE);
                }
            }
            if (e.getBlock().getId() == Block.TRAPPED_CHEST) {
                if (Config.getInstance().TRAPPED_CHEST_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().TRAPPED_CHEST_MESSAGE);
                }
            }
        }
    }

}
