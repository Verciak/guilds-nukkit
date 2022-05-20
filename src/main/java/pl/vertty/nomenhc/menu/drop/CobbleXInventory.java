package pl.vertty.nomenhc.menu.drop;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.inventory.FakeSlotChangeEvent;
import pl.vertty.nomenhc.inventory.inventories.DoubleChestFakeInventory;
import pl.vertty.nomenhc.objects.drop.CobbleXDrop;
import pl.vertty.nomenhc.objects.drop.PandoraDrop;

import java.text.DecimalFormat;

public class CobbleXInventory extends DoubleChestFakeInventory {

    private final Player player;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public CobbleXInventory(Player p) {
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


    }

    private void refreshGui() {
        clearAll();
        setLargeGui();

        for (CobbleXDrop drop : DropManager.drops_cx) {

            Item dropItem = drop.getWhat();
            dropItem.setCustomName(ChatUtil.fixColor("&r&9&l" + drop.getMessage()));
                dropItem.setLore(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Ilosc: &9" + drop.getAmount(),
                }));
            addItem(dropItem);
        }


        Item back = new Item(Item.NETHER_STAR, 0, 1);
        back.setCustomName(ChatUtil.fixColor("&r&4&lPOWROT"));
        back.setLore(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby wrocic!"}));
        setItem(49, back);
    }
}
