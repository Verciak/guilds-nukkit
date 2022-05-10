package pl.vertty.nomenhc.inventory.inventories;

import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.*;
import cn.nukkit.*;
import cn.nukkit.math.*;

import java.util.*;
import cn.nukkit.level.*;
import cn.nukkit.network.protocol.*;
import cn.nukkit.nbt.tag.*;
import java.nio.*;
import cn.nukkit.nbt.*;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.GlassColor;

import java.io.*;

public abstract class ChestFakeInventory extends FakeInventory
{
    public ChestFakeInventory(final InventoryHolder holder, final String title) {
        super(InventoryType.CHEST, holder, title);
    }
    
    ChestFakeInventory(final InventoryType type, final InventoryHolder holder, final String title) {
        super(type, holder, title);
    }
    
    @Override
    public void onOpen(final Player who) {
        this.viewers.add(who);
        final List<BlockVector3> blocks = this.onOpenBlock(who);
        this.blockPositions.put(who, blocks);
        Server.getInstance().getScheduler().scheduleDelayedTask(() -> this.onFakeOpen(who, blocks), 3);
    }
    
    @Override
    protected List<BlockVector3> onOpenBlock(final Player who) {
        final BlockVector3 blockPosition = new BlockVector3((int)who.x, (int)who.y + 2, (int)who.z);
        this.placeChest(who, blockPosition);
        return Collections.singletonList(blockPosition);
    }
    
    protected void placeChest(final Player who, final BlockVector3 pos) {
        final UpdateBlockPacket updateBlock = new UpdateBlockPacket();
        updateBlock.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(who.protocol, BlockID.CHEST, 0);
        updateBlock.flags = 11;
        updateBlock.x = pos.x;
        updateBlock.y = pos.y;
        updateBlock.z = pos.z;
        who.dataPacket(updateBlock);
        final BlockEntityDataPacket blockEntityData = new BlockEntityDataPacket();
        blockEntityData.x = pos.x;
        blockEntityData.y = pos.y;
        blockEntityData.z = pos.z;
        blockEntityData.namedTag = this.getNbt(pos);
        who.dataPacket(blockEntityData);
    }
    
    private byte[] getNbt(final BlockVector3 pos) {
        final CompoundTag tag = new CompoundTag().putString("id", "Chest").putInt("x", pos.x).putInt("y", pos.y).putInt("z", pos.z).putString("CustomName", (this.title == null) ? "Chest" : this.title);
        try {
            return NBTIO.write(tag, ByteOrder.LITTLE_ENDIAN, true);
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to create NBT for chest");
        }
    }


    public void setLargeGui() {
        int[] black = {
                1, 2, 3, 4, 5, 6, 7, 9, 17,
                18,26,27,35,36,44,46,47,48,49,50,51,52 };
        int[] blue = { 0, 8, 45, 53 };
        for (int b : black)
            setItem(b, GlassColor.get(GlassColor.BLACK).setCustomName(ChatUtil.fixColor("&r")));
        for (int b : blue)
            setItem(b, GlassColor.get(GlassColor.BLUE).setCustomName(ChatUtil.fixColor("&r")));
    }

    public void setSmallGui() {
        int[] black = {
                1, 2, 3, 4, 5, 6, 7, 9, 17,
                19, 20, 21, 23, 24, 25, 22 };
        int[] blue = { 0, 8, 18, 26 };
        for (int b : black)
            setItem(b, GlassColor.get(GlassColor.BLACK).setCustomName(ChatUtil.fixColor("&r")));
        for (int b : blue)
            setItem(b, GlassColor.get(GlassColor.BLUE).setCustomName(ChatUtil.fixColor("&r")));
    }
}
