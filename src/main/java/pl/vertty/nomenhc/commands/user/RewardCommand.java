package pl.vertty.nomenhc.commands.user;

import cn.nukkit.Nukkit;
import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.DiscordConfig;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;

public class RewardCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public RewardCommand() {
        super("odbierz", "Odbiera nagrode z discord", "odbierz", "", new String[] { "reward" });
    }

    @Override
    public boolean onCommand(final Player player, final String[] args) {
            User userData = UserManager.get(player.getUniqueId());
            if (userData.isTaken()) {
                player.sendMessage(ChatUtil.fixColor(DiscordConfig.getInstance().Minecraft_Taken));
                return false;
            }
            if (userData.canTake()) {
                userData.setTaken(true);
                Server.getInstance().dispatchCommand(Server.getInstance().getConsoleSender(), DiscordConfig.getInstance().Nagroda.replace("{NICK}", player.getName()));
                player.sendMessage(ChatUtil.fixColor(DiscordConfig.getInstance().Minecraft_Take).replace("{NICK}", player.getName()));
                for(Player p : Server.getInstance().getOnlinePlayers().values()){
                    p.sendMessage(ChatUtil.fixColor(DiscordConfig.getInstance().Minecraft_BC.replace("{NICK}", player.getName()).replace("{N}", "\n")));
                }
                return true;
            }
            player.sendMessage(ChatUtil.fixColor(DiscordConfig.getInstance().Minecraft_NoReward));
            return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
