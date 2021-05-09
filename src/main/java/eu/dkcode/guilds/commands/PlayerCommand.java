package eu.dkcode.guilds.commands;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Account;
import eu.dkcode.guilds.objects.Guild;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: PlayerCommand
 **/

public class PlayerCommand implements CommandExecutor {

    public PlayerCommand(String command, GuildPlugin plugin){
        plugin.getCommand(command).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        OfflinePlayer player = (OfflinePlayer) sender;
        if(args.length == 1) player = Bukkit.getOfflinePlayer(args[0]);
        return playerInfo(sender, Account.get(player.getUniqueId()), Guild.get(player.getName()));
    }

    public boolean playerInfo(CommandSender sender, Account account, Guild guild){
        if(account == null){
            sender.sendMessage(colored("&cTaki gracz nie istnieje!"));
            return false;
        }
        sender.sendMessage(colored("&fGracz &c"+ account.getName() +" &fposiada &4" + account.getPoints() + "&f punktow!" + (guild != null ? " &8(&cCzlonek gildii: &4"+guild.getTag()+"&8)" : "")));
        return true;
    }

}
