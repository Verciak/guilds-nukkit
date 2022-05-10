package pl.vertty.nomenhc.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.handlers.BanHandler;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.objects.configs.Messages;

public class UnBanCommand extends PlayerCommand {

    public UnBanCommand() {
        super("unban", "Odbanowywanie graczy", "/unban <gracz>", "cmd.admin.unban", new String[] { "" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length != 1) {
            ChatUtil.sendMessage(p, "&cPoprawne uzycie komendy: &7/unban <name>");
            return true;
        }
        Ban b = BanHandler.getBan(args[0]);
        if (b == null) {
            ChatUtil.sendMessage(p, Messages.getInstance().user_nohave_ban);
            return true;
        }
        BanHandler.bans.remove(b.getName());
        b.delete();
        String mess = Messages.getInstance().unban_reason;
        ChatUtil.sendMessage(Server.getInstance().getOnlinePlayers().values(), mess.replace("{ADMIN}", p.getName()).replace("{NAME}", b.getName()));
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        if (args.length != 1) {
            ChatUtil.sendMessage(p, "&cPoprawne uzycie komendy: &7/unban <name>");
            return true;
        }
        Ban b = BanHandler.getBan(args[0]);
        if (b == null) {
            ChatUtil.sendMessage(p, Messages.getInstance().user_nohave_ban);
            return true;
        }
        BanHandler.bans.remove(b.getName());
        b.delete();
        String mess = Messages.getInstance().unban_reason;
        ChatUtil.sendMessage(Server.getInstance().getOnlinePlayers().values(), mess.replace("{ADMIN}", p.getName()).replace("{NAME}", b.getName()));
        return false;
    }


}

