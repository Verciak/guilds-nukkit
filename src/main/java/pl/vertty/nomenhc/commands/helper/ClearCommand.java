package pl.vertty.nomenhc.commands.helper;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.objects.drop.Drop;

public class ClearCommand extends PlayerCommand {

    public ClearCommand() {
        super("clear", "Czyszczenie EQ", "clear", "cmd.admin.clear", new String[] { "clear" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length == 0) {
            p.getInventory().setHelmet(new Item(0));
            p.getInventory().setChestplate(new Item(0));
            p.getInventory().setLeggings(new Item(0));
            p.getInventory().setBoots(new Item(0));
            p.getInventory().clearAll();
            ChatUtil.sendMessage(p, Messages.getInstance().clear_eq);
            return true;
        }
        Player o = Server.getInstance().getPlayer(args[0]);
        if (o == null) {
            ChatUtil.sendMessage(p, Messages.getInstance().clear_error);
            return true;
        }
        o.getInventory().clearAll();
        o.getInventory().setHelmet(new Item(0));
        o.getInventory().setChestplate(new Item(0));
        o.getInventory().setLeggings(new Item(0));
        o.getInventory().setBoots(new Item(0));
        ChatUtil.sendMessage(p, Messages.getInstance().clear_eq);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p0, String[] p1) {
        return false;
    }


}

