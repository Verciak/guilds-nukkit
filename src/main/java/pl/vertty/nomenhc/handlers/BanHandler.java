package pl.vertty.nomenhc.handlers;

import cn.nukkit.Player;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.DataUtil;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.objects.configs.Messages;

import java.util.HashMap;
import java.util.UUID;

public class BanHandler {

    public static HashMap<String, Ban> bans = new HashMap<>();


    public static Ban createBan(Ban b) {
        bans.put(b.getName(), b);
        return b;
    }

    public static Ban createBan(Player p, Player admin, String reason, long time) {
        Ban b = new Ban(p.getUniqueId().toString(), p.getName(), p.getAddress(), reason, admin.getName(), time, p.getLoginChainData().getDeviceId(), p.getLoginChainData().getIdentityPublicKey(), String.valueOf(p.getLoginChainData().getClientId()));
        bans.put(p.getName(), b);
        return b;
    }

    public static Ban getBan(Player p) {
        for (Ban b : bans.values()) {
            if (b.getUuid().equals(p.getUniqueId().toString()) || b.getName().equals(p.getName()))
                return b;
        }
        return null;
    }

    public static Ban getBan(UUID uniqueId) {
        for (Ban b : bans.values()) {
            if (b.getUuid().equals(uniqueId.toString()))
                return b;
        }
        return null;
    }

    public static Ban getBan(String name) {
        for (Ban b : bans.values()) {
            if (b.getName().equals(name))
                return b;
        }
        return null;
    }

    public static String getReason(boolean b, Ban b1) {
        String reason = Messages.getInstance().ban_window;
        return ChatUtil.fixColor(reason.replace("{N}", "\n").replace("{NAME}", b1.getName()).replace("{ADMIN}", b1.getAdmin()).replace("{REASON}", b1.getReason()).replace("{EXPIRE}", (b1.getExire() == 0L) ? "Nigdy" : DataUtil.getDate(b1.getExire())));
    }


}
