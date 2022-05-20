package pl.vertty.nomenhc.commands.user;

import cn.nukkit.Player;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.menu.drop.DropInventory;

public class TestCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public TestCommand() {
        super("test", "Komenda test", "test", "", new String[] { "drop" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if(p.getInventory().getItemInHand().getId() == 0){
            p.sendMessage("§cDEBIL NIE MASZ NIC W RECE");
            return false;
        }
        p.sendMessage("§8========================");
        p.sendMessage("§7ID ITEMU: §9" + p.getInventory().getItemInHand().getId());
        p.sendMessage("§7META ITEMU: §9" + p.getInventory().getItemInHand().getDamage());
        p.sendMessage("§8========================");
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
