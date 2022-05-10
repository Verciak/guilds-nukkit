package pl.vertty.nomenhc.menu.drop;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.TimeUtils;
import pl.vertty.nomenhc.inventory.FakeSlotChangeEvent;
import pl.vertty.nomenhc.inventory.inventories.ChestFakeInventory;
import pl.vertty.nomenhc.inventory.inventories.DoubleChestFakeInventory;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.drop.Drop;

public class DropInventory extends DoubleChestFakeInventory {

    private final Player player;

    public DropInventory(Player p) {
        super(null, ChatUtil.fixColor("&r&9&lMENU DROPU"));
        this.player = p;
        refreshGui();
        p.addWindow(this);
    }

    protected void onSlotChange(FakeSlotChangeEvent e) {
        int slot = e.getAction().getSlot();
        Player p = e.getPlayer();
        e.setCancelled(true);
        if(e.getAction().getSlot() == 30){
            new StoneInventory(p);
        }
        if(e.getAction().getSlot() == 20){
            new PandoraInventory(p);
        }
    }

    private void refreshGui() {
        clearAll();
        setLargeGui();
        Item stone = new Item(1, 0, 1);
        stone.setCustomName(ChatUtil.fixColor("&r&9&lDROP Z KAMIENIA"));
        stone.setLore(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z kamienia!"}));

        Item mossy = new Item(Item.MOSSY_STONE, 0, 1);
        mossy.setCustomName(ChatUtil.fixColor("&r&9&lDROP Z COBBLEX"));
        mossy.setLore(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z CobbleX!"}));

        Item magic_case = new Item(Item.CHEST, 0, 1);
        magic_case.setCustomName(ChatUtil.fixColor("&r&9&lDROP Z MAGICZNEJ SKRZYNKI"));
        magic_case.setLore(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z Magicznej Skrzynki!"}));

        Item pandora = new Item(Item.DRAGON_EGG, 0, 1);
        pandora.setCustomName(ChatUtil.fixColor("&r&9&lDROP Z PANDORY"));
        pandora.setLore(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z Pandory!"}));

        setItem(20, pandora);
        setItem(24, magic_case);
        setItem(32, mossy);
        setItem(30, stone);
    }
}
