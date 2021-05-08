package eu.dkcode.guilds.commands;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.helpers.GuildHelper;
import eu.dkcode.guilds.helpers.RandomHelper;
import eu.dkcode.guilds.objects.Guild;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: GuildCommand
 **/

public class GuildCommand implements CommandExecutor {

    public GuildCommand(String command, GuildPlugin plugin){
        plugin.getCommand(command).setExecutor(this);
    }

    private static final HashMap<Guild,String> to_delete = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        final Player player = (Player) sender;
        final Guild guild = Guild.get(player.getName());
        if(args.length < 1) return usage(sender);
        switch (args[0]){
            case "zaloz":
            case "create": {
                if(guild != null) {
                    player.sendMessage(colored("&cPosiadasz juz gildie!"));
                    return false;
                }
                if(args.length < 3) return usage(sender,"/g zaloz [tag] [nazwa]");
                final String tag = args[1].toUpperCase();
                final StringBuilder name = new StringBuilder();
                for(int i=2;i<args.length;i++) name.append(args[i]).append(" ");
                if(Guild.getByTag(tag) != null){
                    player.sendMessage(colored("&cGildia o takim tagu juz istnieje!"));
                    return false;
                }
                final Guild createdGuild = new Guild(tag,name.toString(),player);
                GuildHelper.createRoom(createdGuild);
                player.teleport(createdGuild.getCenterLocation());
                Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(colored("&cGildia &8[&4"+tag+"&8] &4"+name+" &czostala zalozona przez &4"+ player.getName())));
                return false;
            }
            case "delete":
            case "usun":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return false;
                }
                if(!guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored("&cNie jestes liderem gildii!"));
                    return false;
                }
                if(!to_delete.containsKey(guild) || args.length == 1){
                    to_delete.put(guild, RandomHelper.getRandomString(5));
                    player.sendMessage(colored("&cJezeli jestes pewny ze chcesz usunac gildie napisz: &4/g usun "+to_delete.get(guild)));
                    return false;
                }
                if(args.length == 2 && args[1].equals(to_delete.get(guild)))
                    guild.delete("&cLider usunal!");
                else player.sendMessage(colored("&cPodany kod jest nieprawidlowy!"));
                return true;
            }
            default: return usage(sender);
        }
    }

    public boolean usage(CommandSender sender){
        sender.sendMessage(colored("&cPoprawne uzycie: &f" +
                "\n&8» &4zaloz [tag] [nazwa]" +
                "\n&8» &4usun [kod]"));
        return true;
    }

    public boolean usage(CommandSender sender, String usage){
        sender.sendMessage(colored("&cPoprawne uzycie: &f"+usage));
        return true;
    }

}
