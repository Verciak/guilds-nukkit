package pl.vertty.nomenhc.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public abstract class PlayerCommand extends Command
{
    public PlayerCommand(final String name, final String description, final String usage, final String permission, final String[] aliases) {
        super(name, description, usage, permission, aliases);
    }

    @Override
    public boolean onExecute(final CommandSender sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender){
            return this.onCommand((ConsoleCommandSender) sender, args);
        }
        return this.onCommand((Player)sender, args);
    }

    public abstract boolean onCommand(final Player p, final String[] args);

    public abstract boolean onCommand(final ConsoleCommandSender p, final String[] args);
}

