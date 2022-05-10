package pl.vertty.nomenhc.menu.drop;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.DyeColor;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.inventory.FakeSlotChangeEvent;
import pl.vertty.nomenhc.inventory.inventories.ChestFakeInventory;
import pl.vertty.nomenhc.inventory.inventories.DoubleChestFakeInventory;
import pl.vertty.nomenhc.objects.drop.Drop;

import java.text.DecimalFormat;

public class StoneInventory extends DoubleChestFakeInventory {

    private final Player player;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public StoneInventory(Player p) {
        super(null, ChatUtil.fixColor("&r&9&lMENU DROPU"));
        this.player = p;
        refreshGui();
        p.addWindow(this);
    }

    protected void onSlotChange(FakeSlotChangeEvent e) {
        int slot = e.getAction().getSlot();
        Player p = e.getPlayer();
        e.setCancelled(true);
        if(slot == 49){
            new DropInventory(p);
        }
        if(DropManager.getDrop(e.getAction().getSourceItem()) != null){
            DropManager.getDrop(e.getAction().getSourceItem()).changeStatus(player.getUniqueId());
            refreshGui();
        }
        if (e.getAction().getSourceItem().getCustomName() != null && e.getAction().getSourceItem().getCustomName().startsWith(ChatUtil.fixColor("&r&8&7Cobblestone"))) {
            DropManager.changeNoCobble(player.getUniqueId());
            refreshGui();
            return;
        }
        if (e.getAction().getSourceItem().getCustomName() != null && e.getAction().getSourceItem().getCustomName().startsWith(ChatUtil.fixColor("&r&8&7Wiadomosci"))) {
            DropManager.changeNoMsg(player.getUniqueId());
            refreshGui();
            return;
        }
        if (e.getAction().getSourceItem().getCustomName() != null && e.getAction().getSourceItem().getCustomName().startsWith(ChatUtil.fixColor("&r&8&aWlacz caly drop!"))) {
            for(Drop drop : DropManager.drops){
                drop.setStatus(player.getUniqueId(), true);
                refreshGui();
            }
        }
        if (e.getAction().getSourceItem().getCustomName() != null && e.getAction().getSourceItem().getCustomName().startsWith(ChatUtil.fixColor("&r&8&cWylacz caly drop!"))) {
            for(Drop drop : DropManager.drops){
                drop.setStatus(player.getUniqueId(), false);
                refreshGui();
            }
        }

    }

    private void refreshGui() {
        clearAll();
        setLargeGui();

        for (Drop drop : DropManager.drops) {

            Item dropItem = Item.get(drop.getName(), 0, 1);
            dropItem.setCustomName(ChatUtil.fixColor("&r&9&l" + drop.getMessage()));
            double chance = drop.getChance();
            if(player.hasPermission("sponsor")){
                chance += 1;
                dropItem.setLore(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "% &8[&l&bSPONSOR &a+1.0%&r&8]",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9"+drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" +(drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                }));
            }else if(player.hasPermission("svip")){
                chance += 0.50;
                dropItem.setLore(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "% &8[&l&6SVIP &a+0.5%&r&8]",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9"+drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" +(drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                }));
            }else if(player.hasPermission("vip")){
                chance += 0.25;
                dropItem.setLore(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "% &8[&l&eVIP &a+0.25%&r&8]",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9"+drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" +(drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                }));
            }else {
                dropItem.setLore(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "%",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9"+drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" +(drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                }));
            }
            if(!drop.isDisabled(player.getUniqueId())){
                dropItem.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_DURABILITY));
            }
            addItem(dropItem);
        }

        Item cbl = new Item(Item.COBBLESTONE);
        cbl.setCustomName(ChatUtil.fixColor("&r&8&7Cobblestone &8(" + (DropManager.isNoCobble(player.getUniqueId()) ? "&c✖" : "&a✔") + "&8)"));
        if(!DropManager.isNoCobble(player.getUniqueId())){
            cbl.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_DURABILITY));
        }


        Item msg = new Item(Item.PAPER);
        msg.setCustomName(ChatUtil.fixColor("&r&8&7Wiadomosci &8(" + (DropManager.isNoMsg(player.getUniqueId()) ? "&c✖" : "&a✔") + "&8)"));
        if(!DropManager.isNoMsg(player.getUniqueId())){
            msg.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_DURABILITY));
        }

        Item on = new Item(Item.DYE, DyeColor.LIME.getDyeData(), 1);
        on.setCustomName(ChatUtil.fixColor("&r&8&aWlacz caly drop!"));
        Item off = new Item(Item.DYE, DyeColor.RED.getDyeData(), 1);
        off.setCustomName(ChatUtil.fixColor("&r&8&cWylacz caly drop!"));

        Item back = new Item(Item.NETHER_STAR, 0, 1);
        back.setCustomName(ChatUtil.fixColor("&r&4&lPOWROT"));
        back.setLore(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby wrocic!"}));
        setItem(49, back);
        setItem(37, on);
        setItem(38, off);
        setItem(42, msg);
        setItem(43, cbl);
    }
}
