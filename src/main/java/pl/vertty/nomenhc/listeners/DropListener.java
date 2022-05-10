package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Sound;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.DataUtil;
import pl.vertty.nomenhc.helpers.LocationHelper;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.objects.drop.Drop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


@AllArgsConstructor
public class DropListener implements Listener {

    private final GuildPlugin plugin;

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Player player = event.getPlayer();
        if (event.getBlock().getId() == 1) {
            if (player.getGamemode() == 0) {
                event.setDrops(new Item[0]);
                if (!DropManager.isNoCobble(player.getUniqueId())) {
                    if (player.getInventory().canAddItem(Item.get(4, 0, 1))) {
                        player.getInventory().addItem(Item.get(4, 0, 1));
                    } else {
                        player.dropItem(Item.get(4, 0, 1));
                    }
                }
                User user = UserManager.get(player.getUniqueId());
                int exp = 3;
                if (!user.getTurbo_exp().contains("0")) {
                    exp += 10;
                } else {
                    exp = 3;
                }
                player.addExperience(exp);
                for (Drop d : DropManager.drops) {
                    Item itemDrop = d.getWhat().clone();
                    if (event.getBlock().getId() != 1) {
                        return;
                    }
                    if (d.isDisabled(player.getUniqueId())) {
                        return;
                    }
                    double chance = d.getChance();
                    if (player.hasPermission("sponsor")) {
                        chance += 1;
                    } else if (player.hasPermission("svip")) {
                        chance += 0.50;
                    } else if (player.hasPermission("vip")) {
                        chance += 0.25;
                    } else {
                        chance = d.getChance();
                    }
                    if (!user.getTurbo_drop().contains("0")) {
                        chance += 1.5;
                    }
                    if (!RandomHelper.getChance(chance)) {
                        continue;
                    }
                    Enchantment enchantment = player.getInventory().getItemInHand().getEnchantment(Enchantment.ID_FORTUNE_DIGGING);
                    if (enchantment != null) {
                        int anInt = RandomHelper.getRandomInt(d.getMinAmount(), d.getMaxAmount());
                        itemDrop.setCount(anInt);
                    }
                    if (!d.isDisabled(player.getUniqueId())) {
                        if (player.getInventory().canAddItem(itemDrop)) {
                            player.getInventory().addItem(itemDrop);
                        } else {
                            player.dropItem(itemDrop);
                        }

                        int texp = d.getExp();
                        if (!user.getTurbo_exp().contains("0")) {
                            texp += 10;
                        } else {
                            texp = d.getExp();
                        }
                        player.addExperience(texp);
                    }
                    if (!DropManager.isNoMsg(player.getUniqueId()) && !d.getDisabled().contains(player.getUniqueId())) {

                        ChatUtil.sendActionBar(player, ("&8* &7Trafiles na &l&9{ITEM} &fx{AMOUNT} &r&8*")
                                .replace("{ITEM}", d.getMessage())
                                .replace("{AMOUNT}", Integer.toString(itemDrop.getCount()))
                        );
                    }
                }
            }
        }
    }
}
