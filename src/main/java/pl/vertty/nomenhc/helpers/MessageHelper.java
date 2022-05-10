package pl.vertty.nomenhc.helpers;

import cn.nukkit.Player;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


public class MessageHelper {

    public static void sendActionbar(Player player, String message) {
        player.sendActionBar(colored(message));
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        if (title == null) title = "";
        if (subtitle == null) subtitle = "";

       player.sendTitle(colored(title), colored(subtitle), 20, 60, 20);
    }

}
