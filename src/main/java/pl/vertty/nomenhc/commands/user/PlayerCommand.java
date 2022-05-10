package pl.vertty.nomenhc.commands.user;

import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.Guild;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;

public class PlayerCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public PlayerCommand() {
        super("gracz", "Statystyki gracza", "gracz", "", new String[] { "gracz" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if(args.length == 1) {
            Player other = Server.getInstance().getPlayer(args[0]);
            if(other == null){
                OfflinePlayer player = (OfflinePlayer) Server.getInstance().getOfflinePlayer(args[0]);
                playerInfo(p, User.get(player.getUniqueId()), Guild.get(player.getName()));
                return false;
            }
            playerInfo(p, User.get(other.getUniqueId()), Guild.get(other.getName()));
            return false;
        }
        return playerInfo(p, User.get(p.getUniqueId()), Guild.get(p.getName()));
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        if(args.length == 1) {
            Player other = Server.getInstance().getPlayer(args[0]);
            if(other == null){
                OfflinePlayer player = (OfflinePlayer) Server.getInstance().getOfflinePlayer(args[0]);
                playerInfo(p, User.get(player.getUniqueId()), Guild.get(player.getName()));
                return false;
            }
            playerInfo(p, User.get(other.getUniqueId()), Guild.get(other.getName()));
            return false;
        }
        return ChatUtil.sendMessage(p, "Uzyj: /gracz <NICK>");
    }

    public boolean playerInfo(CommandSender sender, User account, Guild guild){
        if(account == null){
            sender.sendMessage(colored("&cTaki gracz nie istnieje!"));
            return false;
        }
        sender.sendMessage(colored("&fGracz &c"+ account.getName() +" &fposiada &4" + account.getPoints() + "&f punktow!" + (guild != null ? " &8(&cCzlonek gildii: &4"+guild.getTag()+"&8)" : "")));
        return true;
    }

}
