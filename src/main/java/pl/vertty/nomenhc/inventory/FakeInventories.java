package pl.vertty.nomenhc.inventory;

import cn.nukkit.*;
import cn.nukkit.math.*;
import pl.vertty.nomenhc.inventory.inventories.FakeInventory;

import java.util.*;

public class FakeInventories
{
    public static List<BlockVector3> getFakeInventoryPositions(final Player player) {
        final FakeInventory inventory = FakeInventory.open.get(player);
        if (inventory == null) {
            return null;
        }
        return inventory.getPosition(player);
    }
    
    public static Optional<FakeInventory> getFakeInventory(final Player player) {
        return Optional.ofNullable(FakeInventory.open.get(player));
    }
    
    public static void removeFakeInventory(final FakeInventory inventory) {
        inventory.close();
    }
}
