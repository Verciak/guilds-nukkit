package pl.vertty.nomenhc.objects.drop;

import cn.nukkit.item.Item;

public class CobbleXDrop {
    private final Integer name;

    private final String message;

    private final int Amount;

    private final Item what;

    public CobbleXDrop(int name, String string, Item what, int Amount) {
        this.name = name;
        this.message = string;
        this.Amount = Amount;
        this.what = what;
    }

    public Integer getName() {
        return this.name;
    }

    public String getMessage() {
        return this.message;
    }

    public int getAmount() {
        return this.Amount;
    }

    public Item getWhat() {
        return this.what;
    }
}

