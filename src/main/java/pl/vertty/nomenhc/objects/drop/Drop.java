package pl.vertty.nomenhc.objects.drop;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Drop {
    private final Integer name;

    private final double chance;


    private final int exp;

    private final String message;

    private final boolean fortune;

    private final int minAmount;

    private final int maxAmount;

    private final Item what;

    private final Block from;

    private final Set<UUID> disabled;

    public Drop(int name, double chance, String string, Item what, Block from, int maxAmount, int minAmount, boolean fortune, int exp) {
        this.disabled = new HashSet<>();
        this.name = name;
        this.chance = chance;
        this.exp = exp;
        this.message = string;
        this.fortune = fortune;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.from = from;
        this.what = what;
    }

    public void changeStatus(UUID uuid) {
        if (this.disabled.contains(uuid)) {
            this.disabled.remove(uuid);
        } else {
            this.disabled.add(uuid);
        }
    }

    public void setStatus(UUID uuid, boolean b) {
        if (b) {
            if (this.disabled.contains(uuid))
                this.disabled.remove(uuid);
        } else if (!this.disabled.contains(uuid)) {
            this.disabled.add(uuid);
        }
    }

    public boolean isDisabled(UUID uuid) {
        return this.disabled.contains(uuid);
    }

    public Integer getName() {
        return this.name;
    }

    public double getChance() {
        return this.chance;
    }

    public int getExp() {
        return this.exp;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isFortune() {
        return this.fortune;
    }


    public int getMinAmount() {
        return this.minAmount;
    }

    public int getMaxAmount() {
        return this.maxAmount;
    }

    public Item getWhat() {
        return this.what;
    }

    public Block getFrom() {
        return this.from;
    }

    public Set<UUID> getDisabled() {
        return this.disabled;
    }

}

