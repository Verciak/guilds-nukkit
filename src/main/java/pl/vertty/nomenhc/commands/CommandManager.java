package pl.vertty.nomenhc.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandMap;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.plugin.PluginManager;
import pl.vertty.nomenhc.helpers.Reflection;
import pl.vertty.nomenhc.listeners.UnknownCommandListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class CommandManager
{
    public static final HashMap<String, Command> commands;

    public static void register(final Command cmd) {
        Server.getInstance().getCommandMap().register(cmd.getName(), cmd);
        CommandManager.commands.put(cmd.getName(), cmd);
        UnknownCommandListener.registeredCommands.addAll(Server.getInstance().getCommandMap().getCommands().keySet());
        UnknownCommandListener.registeredCommands.add(cmd.getName());
        if (cmd.getAliases() != null) {
            UnknownCommandListener.registeredCommands.addAll(Collections.singleton(Arrays.toString(cmd.getAliases())));
        }
    }

    static {
        commands = new HashMap<>();
    }
}

