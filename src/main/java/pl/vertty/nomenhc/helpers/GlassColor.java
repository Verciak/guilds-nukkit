package pl.vertty.nomenhc.helpers;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

public final class GlassColor {
    public static Item get(int color) {
        return Item.get(160, Integer.valueOf(color));
    }

    public static Block getWool(int color) {
        return Block.get(35, color);
    }

    public static int WHITE = 0;

    public static int ORANGE = 1;

    public static int MAGENTA = 2;

    public static int LIGHT_BLUE = 3;

    public static int YELLOW = 4;

    public static int LIME = 5;

    public static int PINK = 6;

    public static int GRAY = 7;

    public static int LIGHT_GRAY = 8;

    public static int CYAN = 9;

    public static int PURPLE = 10;

    public static int BLUE = 11;

    public static int BROWN = 12;

    public static int GREEN = 13;

    public static int RED = 14;

    public static int BLACK = 15;
}

