package eu.dkcode.guilds.commands;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.helpers.GuildHelper;
import eu.dkcode.guilds.helpers.LocationHelper;
import eu.dkcode.guilds.helpers.RandomHelper;
import eu.dkcode.guilds.objects.Guild;
import eu.dkcode.guilds.objects.Teleport;
import eu.dkcode.guilds.objects.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
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
                final double spawnDist = LocationHelper.getDistanceMin(player.getLocation(), player.getWorld().getSpawnLocation()),
                        borderDist = LocationHelper.getBorderDistance(player.getLocation());
                if(spawnDist < 250){
                    player.sendMessage(colored("&cJestes zbyt blisko spawna! (&4Aktualnie: &l"+spawnDist+" &8| &4Minimum: &l250&c)"));
                    return false;
                }
                if(borderDist < 200){
                    player.sendMessage(colored("&cJestes zbyt blisko borderu mapy! (&4Aktualnie: &l"+borderDist+" &8| &4Minimum: &l200&c)"));
                    return false;
                }
                if(GuildHelper.isTooCloseToGuild(player.getLocation())){
                    player.sendMessage(colored("&cjestes zbyt blisko innej gildii!"));
                    return false;
                }

                if(Guild.getByTag(tag) != null){
                    player.sendMessage(colored("&cGildia o takim tagu juz istnieje!"));
                    return false;
                }
                if(tag.length() > 5 || tag.length() < 2 || name.toString().length() < 5 || name.toString().length() > 15 || !tag.matches("^[a-zA-Z0-9]*$") || !name.toString().replace(" ", "").matches("^[a-zA-Z0-9]*$")){
                    player.sendMessage(colored("&cTag gildii musi posiadac od 2 do 5 znakow,\nNazwa gidlii musi posiadac od 5 do 15 znakow,\nTag oraz nazwa gildii moze zawierac tylko liczby i litery!"));
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
            case "vlider":
            case "coowner":
            case "zastepca":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return false;
                }
                if(!guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored("&cNie jestes liderem gildii!"));
                    return false;
                }
                if(args.length != 2)
                    return usage(sender,"/g zastepca [nick]");
                final String target = args[1];
                if(guild.getCoOwner().contains(target)){
                    guild.getCoOwner().remove(target);
                    player.sendMessage(colored("&cGracz &l"+target+" &cnie jest juz vliderem"));
                    return false;
                }

                if(guild.getCoOwner().size() > 2){
                    player.sendMessage(colored("&cOsiagnales juz limit vliderow w gildii!"));
                    return false;
                }
                guild.getCoOwner().add(target);
                player.sendMessage(colored("&cGracz &l"+target+" &czostal nowym vliderem w gildii!"));
                return true;
            }
            case "mistrz":
            case "master":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return false;
                }
                if(!guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored("&cNie jestes liderem gildii!"));
                    return false;
                }
                if(args.length != 2)
                    return usage(sender,"/g mistrz [nick]");
                final String target = args[1];
                if(guild.getMasters().contains(target)){
                    guild.getMasters().remove(target);
                    player.sendMessage(colored("&cGracz &l"+target+" &cnie jest juz mistrzem"));
                    return false;
                }

                if(guild.getMasters().size() > 5){
                    player.sendMessage(colored("&cOsiagnales juz limit mistrzemow w gildii!"));
                    return false;
                }
                guild.getMasters().add(target);
                player.sendMessage(colored("&cGracz &l"+target+" &czostal nowym mistrzem w gildii!"));
                return true;
            }
            case "invite":
            case "zapros":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider i vlider moga to zrobic!"));
                    return false;
                }
                if(args.length != 2) return usage(sender, "/g zapros [name]");
                final String target = args[1];
                if(guild.getMembersInvites().contains(player.getName())){
                    guild.getMembersInvites().remove(player.getName());
                    player.sendMessage(colored("&cCofnales zaproszenie gracza &l" + target + "&c do gildii!"));
                    return false;
                }
                guild.getMembersInvites().add(player.getName());
                player.sendMessage(colored("&cZaprosiles gracza &l" + target + "&c do gildii!"));
                return true;
            }
            case "join":
            case "dolacz":{
                if(guild != null){
                    player.sendMessage(colored("&cPosiadasz juz gildie!"));
                    return false;
                }
                if(args.length != 2){
                    usage(sender,"/g dolacz [tag]");
                    return false;
                }
                final Guild tGuild = Guild.getByTag(args[1].toUpperCase());
                if(tGuild == null){
                    player.sendMessage(colored("&cTaka gildia nie istnieje!"));
                    return false;
                }
                if(!tGuild.getMembersInvites().contains(player.getName())){
                    player.sendMessage(colored("&cNie posiadasz zaproszenia do tej gildii!"));
                    return false;
                }
                tGuild.getMembersInvites().remove(player.getName());
                tGuild.getMembers().add(player.getName());
                Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(colored("&cGracz &4" + player.getName() + " &cdolaczyl do gildii &8[&4"+tGuild.getTag()+"&8]")));
                return true;
            }
            case "leave":
            case "opusc":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return false;
                }
                if(guild.getOwner().equals(player.getName())){
                    player.sendMessage(colored("&cNie mozesz porzucic gildii bedac jej liderem!"));
                    return false;
                }
                guild.getMembers().remove(player.getName());
                guild.getCoOwner().remove(player.getName());
                guild.getMasters().remove(player.getName());
                Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(colored("&cGracz &4" + player.getName() + " &copuscil gildie &8[&4"+guild.getTag()+"&8]")));
                return true;
            }
            case "kick":
            case "wyrzuc":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider i vlider moga to zrobic!"));
                    return false;
                }
                if(args.length != 2) return usage(sender, "/g wyrzuc [name]");
                final String target = args[1];
                if(!guild.getMembers().contains(target)){
                    player.sendMessage(colored("&cNie ma takiego gracza w gildii!"));
                    return false;
                }
                guild.getMembers().remove(target);
                guild.getCoOwner().remove(target);
                guild.getMasters().remove(target);
                Bukkit.getOnlinePlayers().forEach(tPlayer -> tPlayer.sendMessage(colored("&cGracz &4" + target + " &czostal wyrzucony z gildii &8[&4"+guild.getTag()+"&8]")));
                return true;
            }
            case "home":
            case "dom":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                new Teleport(player.getUniqueId(),System.currentTimeMillis() + 10 * 1000, Bukkit.getScheduler().runTaskLater(GuildPlugin.getPlugin(GuildPlugin.class),() -> {
                    Teleport.remove(player.getUniqueId());
                    player.teleport(guild.getHomeLocation());
                },10*20L));
                return false;
            }
            case "sethome":
            case "ustawdom":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider, vlider i mistrz moga to zrobic!"));
                    return false;
                }
                final Guild lGuild = Guild.get(player.getLocation());
                if(lGuild == null || !lGuild.getUuid().equals(guild.getUuid())){
                    player.sendMessage(colored("&cDom gildii mozesz ustawic tylko na terenie twojej gildii!"));
                    return false;
                }
                guild.setHomeX(player.getLocation().getBlockX());
                guild.setHomeY(player.getLocation().getBlockY());
                guild.setHomeZ(player.getLocation().getBlockZ());
                player.sendMessage(colored("&aPoprawnie ustawiono nowy dom gildii!"));
                return true;
            }
            case "extend":
            case "pay":
            case "przedluz":
            case "oplac":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider, vlider i mistrz moga to zrobic!"));
                    return false;
                }
                if(guild.getExpireDate().getTime() + (1000 * 60 * 60 * 24 * 2) > System.currentTimeMillis() + + (1000 * 60 * 60 * 24 * 6)){
                    player.sendMessage(colored("&cTwoja gildia jest juz przedluzona na maksymalny okres czasu!"));
                    return true;
                }
                if(!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK,1),Config.getInstance().guildExtendPrice)){
                    player.sendMessage(colored("&cMusisz posiadac min. "+Config.getInstance().guildExtendPrice+" blokow szmaragdow aby przedluzyc gildie!"));
                    return false;
                }
                player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK,Config.getInstance().guildExtendPrice));
                guild.setExpireDate(new Date(guild.getExpireDate().getTime() + (1000 * 60 * 60 * 24 * 2)));
                player.sendMessage(colored("&aGildia zostala przedluzona o 2 dni"));
                return true;
            }
            case "enlarge":
            case "powieksz":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider, vlider i mistrz moga to zrobic!"));
                    return false;
                }
                if(guild.getGuildSize().equals(Config.getInstance().guildMaxSize)){
                    player.sendMessage(colored("&cTwoja gildia jest juz powiekszona na maksymalny rozmiar"));
                    return true;
                }
                final int price = Config.getInstance().guildEnlargePrice * ((guild.getGuildSize() + 2) - (Config.getInstance().guildDefaultSize + 1));
                if(!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK,1),price)){
                    player.sendMessage(colored("&cMusisz posiadac min. "+price+" blokow szmaragdow aby powiekszyc gildie!"));
                    return false;
                }
                player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK,price));
                guild.setGuildSize(guild.getGuildSize() + 2);
                player.sendMessage(colored("&aPowiekszyles gildie do rozmiaru: &l" + guild.getGuildSize() + " x " + guild.getGuildSize()));
                return true;
            }
            case "ally":
            case "sojusz":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider i vlider moga to zrobic!"));
                    return false;
                }
                if(args.length != 2) return usage(sender, "/g sojusz [tag]");
                final Guild tGuild = Guild.getByTag(args[1]);
                if(tGuild == null){
                    player.sendMessage(colored("&cGildia o takim tagu nie istnieje!"));
                    return false;
                }
                if(!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK,1),16)){
                    player.sendMessage(colored("&cMusisz posiadac min. "+16+" blokow szmaragdow za kazdym razem jak uzywasz komend /g sojusz!"));
                    return false;
                }
                player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK,16));
                if(guild.getAllies().contains(tGuild.getUuid())){
                    guild.getAllies().remove(tGuild.getUuid());
                    tGuild.getAllies().remove(guild.getUuid());
                    Bukkit.getOnlinePlayers().forEach(tPlayer -> tPlayer.sendMessage(colored("&cGildia &8[&4" + guild.getTag() + "&8] &czerwala sojusz z gildia &8[&4"+tGuild.getTag()+"&8]")));
                    return true;
                }
                if(guild.getAllies().size() == 3){
                    player.sendMessage(colored("&cTwoja gildia osiagnela limit sojusznikow!"));
                    return true;
                }
                if(guild.getAlliesInvites().contains(tGuild.getUuid())){
                    guild.getAllies().add(tGuild.getUuid());
                    tGuild.getAllies().add(guild.getUuid());
                    guild.getAlliesInvites().remove(tGuild.getUuid());
                    tGuild.getAlliesInvites().remove(guild.getUuid());
                    Bukkit.getOnlinePlayers().forEach(tPlayer -> tPlayer.sendMessage(colored("&cGildia &8[&4" + guild.getTag() + "&8] &czawarla sojusz z gildia &8[&4"+tGuild.getTag()+"&8]")));
                    return true;
                }

                guild.getAlliesInvites().add(tGuild.getUuid());
                tGuild.getAlliesInvites().add(guild.getUuid());
                player.sendMessage(colored("&aZaprosiles gildie &8[&2" + tGuild.getTag() + "&8] &ado sojuszu!"));
                GuildHelper.getGuildOnlinePlayers(tGuild).forEach(target -> {
                    target.sendMessage(colored("&aTwoja gildia otrzymala zaproszenie do sojuszu od gildii &8[&4"+guild.getTag()+"&8]"));
                });
                return true;
            }
            case "fight":
            case "pvp":{
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                if(!guild.getOwner().equals(player.getName()) && !guild.getCoOwner().contains(player.getName()) && !guild.getMasters().contains(player.getName())){
                    player.sendMessage(colored("&cTylko lider, vlider i mistrz moga to zrobic!"));
                    return false;
                }
                if(args.length == 2 && (args[1].equalsIgnoreCase("sojusz")
                        || args[1].equalsIgnoreCase("s") || args[1].equalsIgnoreCase("ally") || args[1].equalsIgnoreCase("a"))){
                    guild.setAllyPvp(!guild.getAllyPvp());
                    GuildHelper.getGuildOnlinePlayers(guild).forEach(target -> player.sendMessage("&cPVP w sojuszu zostalo: &" + (guild.getAllyPvp() ? "aWlaczone" : "&4Wylaczone")));
                    return true;
                }
                guild.setGuildPvp(!guild.getGuildPvp());
                GuildHelper.getGuildOnlinePlayers(guild).forEach(target -> player.sendMessage("&cPVP w gildii zostalo: &" + (guild.getGuildPvp() ? "aWlaczone" : "&4Wylaczone")));
                return true;
            }
            case "info":{
                if(args.length == 2){
                    final Guild tGuild = Guild.getByTag(args[1]);
                    return guildInfo(sender, tGuild);
                }
                if(guild == null){
                    player.sendMessage(colored("&cNie posiadasz gildii!"));
                    return true;
                }
                return guildInfo(sender,guild);
            }
            default: return usage(sender);
        }
    }

    public boolean guildInfo(CommandSender sender, Guild guild){
        sender.sendMessage(colored(guild.getTag()));
        return true;
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
