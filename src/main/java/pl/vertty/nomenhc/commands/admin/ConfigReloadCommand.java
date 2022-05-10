package pl.vertty.nomenhc.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ColorHelper;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.helpers.TimeUtils;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.DiscordConfig;
import pl.vertty.nomenhc.objects.configs.DropConfig;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.scoreboard.ScoreboardBuilder;
import pl.vertty.nomenhc.scoreboard.manager.ScoreboardManager;

public class ConfigReloadCommand extends PlayerCommand {

    public ConfigReloadCommand() {
        super("config", "Przeladowanie configu", "config", "cmd.admin.config", new String[] { "cr" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        for(Player player : Server.getInstance().getOnlinePlayers().values()) {
            ScoreboardBuilder scoreboardBuilder = ScoreboardManager.getScoreboard(player);
            if (scoreboardBuilder != null) {
                scoreboardBuilder.hide();
            }
            long time = TimeUtils.getTime("10sek");
            long timee = TimeUtils.getTime("20sek");
            User user = UserManager.get(p.getUniqueId());
            time += System.currentTimeMillis();
            timee += System.currentTimeMillis();
            user.setTurbo_exp(String.valueOf(time));
            user.setTurbo_drop(String.valueOf(timee));
            user.setTurbo(true);
        }
        Messages.load("./plugins/guilds/messages.json");
        Config.load("./plugins/guilds/config.json");
        DropConfig.load("./plugins/guilds/DropConfig.json");
        DiscordConfig.load("./plugins/guilds/DiscordConfig.json");
        DropManager.loadDrops();
        p.sendMessage(ColorHelper.colored("&aPrzeladowano config!"));
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        Messages.load("./plugins/guilds/messages.json");
        Config.load("./plugins/guilds/config.json");
        DropConfig.load("./plugins/guilds/DropConfig.json");
        DiscordConfig.load("./plugins/guilds/DiscordConfig.json");
        DropManager.loadDrops();
        p.sendMessage(ColorHelper.colored("&aPrzeladowano config!"));
        return false;
    }


}

