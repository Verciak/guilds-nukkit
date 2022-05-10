package pl.vertty.nomenhc.commands.guilds;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.helpers.ColorHelper;
import pl.vertty.nomenhc.helpers.GuildHelper;
import pl.vertty.nomenhc.helpers.LocationHelper;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.Teleport;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.Messages;

import java.util.Date;
import java.util.HashMap;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;

public class GuildCommand extends PlayerCommand {


    public GuildCommand() {
        super("g", "Komendy gildii", "gildie", "", new String[] { "guild" });
    }


    private static final HashMap<Guild,String> to_delete = new HashMap<>();


    @Override
    public boolean onCommand(Player p, final String[] args) {
        final Player player = p;
        final Guild guild = Guild.get(player.getName());
        if(args.length < 1) return usage(p);
        switch (args[0]){
            case "zaloz":
            case "create": {
                if(guild != null) {
                    player.sendMessage(colored(Messages.getInstance().have_guild));
                    return false;
                }
                if(args.length < 3) return usage(p,"/g zaloz [tag] [nazwa]");
                final String tag = args[1].toUpperCase();
                final StringBuilder name = new StringBuilder();
                for(int i=2;i<args.length;i++) name.append(args[i]).append(" ");
                final double spawnDist = player.getLocation().distance(Server.getInstance().getDefaultLevel().getSpawnLocation().getLocation());
                if(spawnDist < Config.getInstance().guildSpawnDistance){
                    player.sendMessage(colored(Messages.getInstance().next_to_spawn)
                            .replace("{DISTANCE}", String.valueOf(spawnDist))
                            .replace("{ALLOW_DISTANCE}", String.valueOf(Config.getInstance().guildSpawnDistance)));

                    return false;
                }
//                if(!LocationHelper.getBorderDistance(p.getLocation())){
//                    player.sendMessage(colored(Messages.getInstance().next_to_border)
//                            .replace("{ALLOW_DISTANCE}", String.valueOf(Config.getInstance().guildBorderDistance)));
//                    return false;
//                }
                if(GuildHelper.isTooCloseToGuild(player.getLocation())){
                    player.sendMessage(colored(Messages.getInstance().next_to_guild));
                    return false;
                }

                if(Guild.getByTag(tag) != null){
                    player.sendMessage(colored(Messages.getInstance().create_name_istnieje));
                    return false;
                }
                if(tag.length() > 5 || tag.length() < 2 || name.toString().length() < 5 || name.toString().length() > 30 || !tag.matches("^[a-zA-Z0-9]*$") || !name.toString().replace(" ", "").matches("^[a-zA-Z0-9]*$")){
                    player.sendMessage(colored(Messages.getInstance().create_name_error));
                    return false;
                }
                final Guild createdGuild = new Guild(tag,name.toString(),player);
                GuildHelper.createRoom(createdGuild);
                player.teleport(createdGuild.getCenterLocation());
                Server.getInstance().getOnlinePlayers().values().forEach(target ->
                        target.sendMessage(colored(Messages.getInstance().create_name_bc)
                                .replace("{TAG}", tag)
                                .replace("{G-NAME}", name)
                                .replace("{P-NAME}", player.getName())));
                return false;
            }
            case "delete":
            case "usun":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return false;
                }
                if(!guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_no_lider));
                    return false;
                }
                if(!to_delete.containsKey(guild) || args.length == 1){
                    to_delete.put(guild, RandomHelper.getRandomString(5));
                    player.sendMessage(colored(Messages.getInstance().guild_delete_code.replace("{CODE}", to_delete.get(guild))));
                    return false;
                }
                if(args.length == 2 && args[1].equals(to_delete.get(guild)))
                    guild.delete("&cLider usunal!");
                else player.sendMessage(colored(Messages.getInstance().guild_delete_code_error));
                return true;
            }
            case "vlider":
            case "coowner":
            case "zastepca":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return false;
                }
                if(!guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_no_lider));
                    return false;
                }
                if(args.length != 2)
                    return usage(p,"/g zastepca [nick]");
                final String target = args[1];
                if(guild.getCoOwner().contains(target)){
                    guild.getCoOwner().remove(target);
                    player.sendMessage(colored(Messages.getInstance().guild_vleader_succes_remove.replace("{NICK}", target)));
                    return false;
                }

                if(guild.getCoOwner().size() > 2){
                    player.sendMessage(colored(Messages.getInstance().guild_vleader_limit));
                    return false;
                }
                guild.getCoOwner().add(target);
                player.sendMessage(colored(Messages.getInstance().guild_vleader_succes.replace("{NICK}", target)));
                return true;
            }
            case "mistrz":
            case "master":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return false;
                }
                if(!guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_no_lider));
                    return false;
                }
                if(args.length != 2)
                    return usage(p,"/g mistrz [nick]");
                final String target = args[1];
                if(guild.getMasters().contains(target)){
                    guild.getMasters().remove(target);
                    player.sendMessage(colored(Messages.getInstance().guild_officer_succes_remove.replace("{NAME}", target)));
                    return false;
                }

                if(guild.getMasters().size() > 5){
                    player.sendMessage(colored(Messages.getInstance().guild_officer_limit));
                    return false;
                }
                guild.getMasters().add(target);
                player.sendMessage(colored(Messages.getInstance().guild_officer_succes.replace("{NICK}", target)));
                return true;
            }
            case "invite":
            case "zapros":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader));
                    return false;
                }
                if(args.length != 2) return usage(p, "/g zapros [name]");
                final String target = args[1];
                Player player1 = Server.getInstance().getPlayer(target);
                if(player1 == null){
                    p.sendMessage(ColorHelper.colored(Messages.getInstance().guild_player_offline));
                    return false;
                }
                if(guild.getMembersInvites().contains(target.toUpperCase())){
                    guild.getMembersInvites().remove(target.toUpperCase());
                    player.sendMessage(colored(Messages.getInstance().guild_invite_back_request.replace("{NICK}", target)));
                    return false;
                }
                guild.getMembersInvites().add(target.toUpperCase());
                player.sendMessage(colored(Messages.getInstance().guild_invite.replace("{NICK}", target.toUpperCase())));
                if(player1 != null){
                    player1.sendMessage(ColorHelper.colored(Messages.getInstance().guild_invite_request).replace("{TAG}", guild.getTag()));
                }

                return true;
            }
            case "join":
            case "dolacz":{
                if(guild != null){
                    player.sendMessage(colored(Messages.getInstance().have_guild));
                    return false;
                }
                if(args.length != 2){
                    usage(p,"/g dolacz [tag]");
                    return false;
                }
                final Guild tGuild = Guild.getByTag(args[1].toUpperCase());
                if(tGuild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_exists));
                    return false;
                }
                if(!tGuild.getMembersInvites().contains(player.getName().toUpperCase())){
                    player.sendMessage(colored(Messages.getInstance().guild_invite_nohave));
                    return false;
                }
                tGuild.getMembersInvites().remove(player.getName().toUpperCase());
                tGuild.getMembers().add(player.getName());
                
                Server.getInstance().getOnlinePlayers().values().forEach(target -> 
                        target.sendMessage(colored(Messages.getInstance().guild_invite_accept_bc.replace("{NAME}", player.getName()).replace("{TAG}", tGuild.getTag()))));
                return true;
            }
            case "leave":
            case "opusc":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return false;
                }
                if(guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_leave_error));
                    return false;
                }
                guild.getMembers().remove(player.getName());
                guild.getCoOwner().remove(player.getName());
                guild.getMasters().remove(player.getName());
                Server.getInstance().getOnlinePlayers().values().forEach(target ->
                        target.sendMessage(colored(Messages.getInstance().guild_leave_succes_bc.replace("{NICK}", player.getName()).replace("{TAG}", guild.getTag()))));
                return true;
            }
            case "kick":
            case "wyrzuc":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader));
                    return false;
                }
                if(args.length != 2) return usage(p, "/g wyrzuc [name]");
                final String target = args[1];
                if(!guild.getMembers().contains(target)){
                    player.sendMessage(colored(Messages.getInstance().guild_player_no));
                    return false;
                }
                guild.getMembers().remove(target);
                guild.getCoOwner().remove(target);
                guild.getMasters().remove(target);
                Server.getInstance().getOnlinePlayers().values().forEach(tPlayer ->
                        tPlayer.sendMessage(colored(Messages.getInstance().guild_kick_succes_bc.replace("{NICK}", target).replace("{TAG}", guild.getTag()))));
                return true;
            }
            case "home":
            case "dom":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                new Teleport(player.getUniqueId(),System.currentTimeMillis() + 10 * 1000, Server.getInstance().getScheduler().scheduleDelayedTask(GuildPlugin.getPlugin(),() -> {
                    Teleport.remove(player.getUniqueId());
                    player.teleport(guild.getHomeLocation());
                },10*20));
                return false;
            }
            case "sethome":
            case "ustawdom":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader_mistrz));
                    return false;
                }
                final Guild lGuild = Guild.get(player.getLocation());
                if(lGuild == null || !lGuild.getUuid().equals(guild.getUuid())){
                    player.sendMessage(colored(Messages.getInstance().guild_sethome_error));
                    return false;
                }
                guild.setHomeX(player.getLocation().getFloorX());
                guild.setHomeY(player.getLocation().getFloorY());
                guild.setHomeZ(player.getLocation().getFloorZ());
                player.sendMessage(colored(Messages.getInstance().guild_sethome_succes));
                return true;
            }
            case "extend":
            case "pay":
            case "przedluz":
            case "oplac":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader_mistrz));
                    return false;
                }
                if(guild.getExpireDate().getTime() + (1000 * 60 * 60 * 24 * 2) > System.currentTimeMillis() + + (1000 * 60 * 60 * 24 * 6)){
                    player.sendMessage(colored(Messages.getInstance().guild_prolong_error));
                    return true;
                }
                if(!player.getInventory().contains(new Item(Item.EMERALD_BLOCK,0, Config.getInstance().guildExtendPrice))){
                    player.sendMessage(colored(Messages.getInstance().guild_prolong_error1.replace("{COST}", String.valueOf(Config.getInstance().guildExtendPrice))));
                    return false;
                }
                player.getInventory().removeItem(new Item(Item.EMERALD_BLOCK,0, Config.getInstance().guildExtendPrice));
                guild.setExpireDate(new Date(guild.getExpireDate().getTime() + (1000 * 60 * 60 * 24 * 2)));
                player.sendMessage(colored(Messages.getInstance().guild_prolong_succes));
                return true;
            }
            case "enlarge":
            case "powieksz":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader_mistrz));
                    return false;
                }
                if(guild.getGuildSize().equals(Config.getInstance().guildMaxSize)){
                    player.sendMessage(colored(Messages.getInstance().guild_large_error));
                    return true;
                }
                final int price = Config.getInstance().guildEnlargePrice * ((guild.getGuildSize() + 2) - (Config.getInstance().guildDefaultSize + 1));
                if(!player.getInventory().contains(new Item(Item.EMERALD_BLOCK,0, price))){
                    player.sendMessage(colored(Messages.getInstance().guild_large_error1.replace("{COST}", String.valueOf(price))));
                    return false;
                }
                player.getInventory().removeItem(new Item(Item.EMERALD_BLOCK,0,price));
                guild.setGuildSize(guild.getGuildSize() + 2);
                player.sendMessage(colored(Messages.getInstance().guild_large_succes.replace("{SIZE}", String.valueOf(guild.getGuildSize()))));
                return true;
            }
            case "ally":
            case "sojusz":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader));
                    return false;
                }
                if(args.length != 2) return usage(p, "/g sojusz [tag]");
                final Guild tGuild = Guild.getByTag(args[1]);
                if(tGuild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_exists));
                    return false;
                }
                if(!player.getInventory().contains(new Item(Item.EMERALD_BLOCK,0,16))){
                    player.sendMessage(colored(Messages.getInstance().guild_ally_error1));
                    return false;
                }
                player.getInventory().removeItem(new Item(Item.EMERALD_BLOCK,0,16));
                if(guild.getAllies().contains(tGuild.getUuid())){
                    guild.getAllies().remove(tGuild.getUuid());
                    tGuild.getAllies().remove(guild.getUuid());
                    Server.getInstance().getOnlinePlayers().values().forEach(tPlayer -> tPlayer.sendMessage(colored(Messages.getInstance().guild_ally_remove.replace("{TAG}", guild.getTag()).replace("{O-TAG}", tGuild.getTag()))));
                    return true;
                }
                if(guild.getAllies().size() == 3){
                    player.sendMessage(colored(Messages.getInstance().guild_ally_error2));
                    return true;
                }
                if(guild.getAlliesInvites().contains(tGuild.getUuid())){
                    guild.getAllies().add(tGuild.getUuid());
                    tGuild.getAllies().add(guild.getUuid());
                    guild.getAlliesInvites().remove(tGuild.getUuid());
                    tGuild.getAlliesInvites().remove(guild.getUuid());
                    Server.getInstance().getOnlinePlayers().values().forEach(tPlayer -> tPlayer.sendMessage(colored(Messages.getInstance().guild_ally_succes.replace("{TAG}", guild.getTag()).replace("{O-TAG}", tGuild.getTag()))));
                    return true;
                }

                guild.getAlliesInvites().add(tGuild.getUuid());
                tGuild.getAlliesInvites().add(guild.getUuid());
                player.sendMessage(colored(Messages.getInstance().guild_ally_invite.replace("{TAG}", tGuild.getTag())));
                GuildHelper.getGuildOnlinePlayers(tGuild).forEach(target -> {
                    target.sendMessage(colored(Messages.getInstance().guild_ally_invite_members_notify).replace("{TAG}", guild.getTag()));
                });
                return true;
            }
            case "fight":
            case "pvp":{
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored(Messages.getInstance().guild_onlyleader_vleader_mistrz));
                    return false;
                }
                if(args.length == 2 && (args[1].equalsIgnoreCase("sojusz")
                        || args[1].equalsIgnoreCase("s") || args[1].equalsIgnoreCase("ally") || args[1].equalsIgnoreCase("a"))){
                    guild.setAllyPvp(!guild.getAllyPvp());
                    GuildHelper.getGuildOnlinePlayers(guild).forEach(target -> player.sendMessage(colored(Messages.getInstance().guild_pvp_ally.replace("{STATUS}", (guild.getAllyPvp() ? "awlaczone" : "&4wylaczone")))));
                    return true;
                }
                guild.setGuildPvp(!guild.getGuildPvp());
                GuildHelper.getGuildOnlinePlayers(guild).forEach(target -> player.sendMessage(colored(Messages.getInstance().guild_pvp.replace("{STATUS}", (guild.getAllyPvp() ? "awlaczone" : "&4wylaczone")))));
                return true;
            }
            case "info":{
                if(args.length == 2){
                    final Guild tGuild = Guild.getByTag(args[1]);
                    return guildInfo(p, tGuild);
                }
                if(guild == null){
                    player.sendMessage(colored(Messages.getInstance().guild_no_have));
                    return true;
                }
                return guildInfo(p,guild);
            }
            default: return usage(p);
        }
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p0, String[] p1) {
        return false;
    }

    public boolean guildInfo(CommandSender sender, Guild guild){
        sender.sendMessage(colored(guild.getTag() + guild.getPoints()));
        return true;
    }

    public boolean usage(CommandSender sender){
        sender.sendMessage(colored(Messages.getInstance().guild).replace("{N}", "\n"));
        return true;
    }

    public boolean usage(CommandSender sender, String usage){
        sender.sendMessage(colored("&7Poprawne uzycie: &9"+usage));
        return true;
    }

}
