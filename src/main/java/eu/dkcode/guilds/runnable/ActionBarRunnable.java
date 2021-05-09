package eu.dkcode.guilds.runnable;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.helpers.DateHelper;
import eu.dkcode.guilds.helpers.LocationHelper;
import eu.dkcode.guilds.helpers.MessageHelper;
import eu.dkcode.guilds.objects.Guild;
import eu.dkcode.guilds.objects.Teleport;
import lombok.AllArgsConstructor;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: ActionBarRunnable
 **/

@AllArgsConstructor
public class ActionBarRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,this,0, 20);
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            final Guild pGuild = Guild.get(player.getName()),
                    lGuild = Guild.get(player.getLocation());
            final Teleport teleport = Teleport.get(player.getUniqueId());

            StringBuilder builder = new StringBuilder();
            if(lGuild != null){
                final String guildMessageColor = pGuild == null ? "4" : pGuild.getAllies().contains(lGuild.getUuid()) ? "9" : pGuild.getUuid().equals(lGuild.getUuid()) ? "2" : "4";
                final double guildCenterDist = LocationHelper.getDistanceMax(player.getLocation(),lGuild.getCenterLocation());
                append(builder,"&fZnajdujesz sie na terenie gildii: &8[&"+guildMessageColor+lGuild.getTag()+" &8:: &"+guildMessageColor +guildCenterDist +"&8]");
            }

            if(teleport != null)
                append(builder,"&6Teleportacja &8(&e "+DateHelper.format(teleport.getEnd())+"&8)");

            if(builder.length() != 0) MessageHelper.sendActionbar(player,colored(builder.toString()));
        });
    }

    private StringBuilder append(StringBuilder builder, String string){
        if(builder.length() != 0) builder.append(" &8:: ");
        builder.append(string);
        return builder;
    }

}
