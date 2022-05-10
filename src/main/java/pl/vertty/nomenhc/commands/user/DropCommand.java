package pl.vertty.nomenhc.commands.user;

import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.TimeUtils;
import pl.vertty.nomenhc.menu.drop.DropInventory;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.User;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;

public class DropCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public DropCommand() {
        super("drop", "Komenda dropu", "drop", "", new String[] { "drop" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        new DropInventory(p);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
