package pl.vertty.nomenhc.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.handlers.BanHandler;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.DataUtil;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.objects.configs.Messages;
import org.apache.commons.lang3.StringUtils;

public class BanCommand extends PlayerCommand {

    public BanCommand() {
        super("ban", "Banowanie graczy", "/ban <name> <time/perm> [reason]", "cmd.admin.ban", new String[] { "" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        String name, uuid, ip, deviceid, IdentityPublicKey, ClientId;
        Ban b;
        if (args.length < 2) {
            ChatUtil.sendMessage(p, "&cPoprawne uzycie komendy: &7/ban <name> <time/perm> [reason]");
            return true;
        }
        String powod = Messages.getInstance().ban_no_reason;
        if (args.length > 2) {
            powod = StringUtils.join((Object[]) args, " ", 2, args.length);
        }
        Player tar = Server.getInstance().getPlayer(args[0]);
        if (tar == null) {
            name = args[0];
            ip = "";
            uuid = "";
            deviceid = "";
            IdentityPublicKey = "";
            ClientId = "";
        } else {
            name = tar.getName();
            uuid = tar.getUniqueId().toString();
            ip = tar.getAddress();
            deviceid = tar.getLoginChainData().getDeviceId();
            IdentityPublicKey = tar.getLoginChainData().getIdentityPublicKey();
            ClientId = String.valueOf(tar.getLoginChainData().getClientId());
        }
        if (BanHandler.getBan(name) != null) {
           ChatUtil.sendMessage(p, Messages.getInstance().user_have_ban);
            return true;
        }
        long time = DataUtil.parseDateDiff(args[1], true);
        if (!args[1].equalsIgnoreCase("perm")) {
            b = new Ban(uuid, name, ip, powod, p.getName(), time, deviceid, IdentityPublicKey, ClientId);
        } else {
            b = new Ban(uuid, name, ip, powod, p.getName(), DataUtil.parseDateDiff("1000y", true), deviceid, IdentityPublicKey, ClientId);
        }
        BanHandler.createBan(b);
        String reason = Messages.getInstance().ban_reason;
        ChatUtil.sendMessage(Server.getInstance().getOnlinePlayers().values(), reason.replace("{ADMIN}", p.getName()).replace("{NAME}", b.getName()).replace("{REASON}", b.getReason()).replace("{EXPIRE}", (b.getExire() == 0L) ? "Nigdy" : DataUtil.getDate(b.getExire())));
        if (tar != null) {
            tar.kick(BanHandler.getReason(true, b));
        }
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        String name, uuid, ip, deviceid, IdentityPublicKey, ClientId;
        Ban b;
        if (args.length < 2) {
            ChatUtil.sendMessage(p, "&cPoprawne uzycie komendy: &7/ban <name> <time/perm> [reason]");
            return true;
        }
        String powod = Messages.getInstance().ban_no_reason;
        if (args.length > 2) {
            powod = StringUtils.join((Object[]) args, " ", 2, args.length);
        }
        Player tar = Server.getInstance().getPlayer(args[0]);
        if (tar == null) {
            name = args[0];
            ip = "";
            uuid = "";
            deviceid = "";
            IdentityPublicKey = "";
            ClientId = "";
        } else {
            name = tar.getName();
            uuid = tar.getUniqueId().toString();
            ip = tar.getAddress();
            deviceid = tar.getLoginChainData().getDeviceId();
            IdentityPublicKey = tar.getLoginChainData().getIdentityPublicKey();
            ClientId = String.valueOf(tar.getLoginChainData().getClientId());
        }
        if (BanHandler.getBan(name) != null) {
            ChatUtil.sendMessage(p, Messages.getInstance().user_have_ban);
            return true;
        }
        long time = DataUtil.parseDateDiff(args[1], true);
        if (!args[1].equalsIgnoreCase("perm")) {
            b = new Ban(uuid, name, ip, powod, p.getName(), time, deviceid, IdentityPublicKey, ClientId);
        } else {
            b = new Ban(uuid, name, ip, powod, p.getName(), DataUtil.parseDateDiff("1000y", true), deviceid, IdentityPublicKey, ClientId);
        }
        BanHandler.createBan(b);
        String reason = Messages.getInstance().ban_reason;
        ChatUtil.sendMessage(Server.getInstance().getOnlinePlayers().values(), reason.replace("{ADMIN}", p.getName()).replace("{NAME}", b.getName()).replace("{REASON}", b.getReason()).replace("{EXPIRE}", (b.getExire() == 0L) ? "Nigdy" : DataUtil.getDate(b.getExire())));
        if (tar != null) {
            tar.kick(BanHandler.getReason(true, b));
        }
        return false;
    }


}

