package pl.vertty.nomenhc.runnable;

import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.helpers.*;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.Teleport;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


@AllArgsConstructor
public class ActionBarRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(plugin,this,0, 20);
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().values().forEach(player -> {
            final Combat u = CombatManager.getCombat(player);
            final Guild pGuild = Guild.get(player.getName()),
                    lGuild = Guild.get(player.getLocation());
            final Teleport teleport = Teleport.get(player.getUniqueId());

            StringBuilder builder = new StringBuilder();
            if (u != null) {
                if (u.hasFight()) {
                    if (Config.getInstance().wiadomosci_actionbar) {
                        append(builder, Messages.getInstance().wiadomosci_actionbarpvp.replace("{TIME}", (DataUtil.secondsToString(u.getLastAttactTime()).isEmpty() ? "0s" : (DataUtil.secondsToString(u.getLastAttactTime())))));
                    }
                } else {
                    if (!u.wasFight() || u.hasFight()) {
                        return;
                    }
                    append(builder, Messages.getInstance().wiadomosci_actionbarendpvp);
                    ChatUtil.sendMessage(player, ChatUtil.fixColor(Messages.getInstance().wiadomosci_actionbarendpvpchat));
                    u.setLastAttactTime(0L);
                    u.setLastAsystTime(0L);
                    u.setLastAttactkPlayer(null);
                    u.setLastAsystPlayer(null);
                }
            }
            if(lGuild != null){
                final String guildMessageColor = pGuild == null ? "4" : pGuild.getAllies().contains(lGuild.getUuid()) ? "9" : pGuild.getUuid().equals(lGuild.getUuid()) ? "2" : "4";
                final double guildCenterDist = LocationHelper.getDistanceMax(player.getLocation(),lGuild.getCenterLocation());
                append(builder,Messages.getInstance().guildActionBar_terrain
                        .replace("{COLOR}", guildMessageColor)
                        .replace("{TAG}", lGuild.getTag())
                        .replace("{DISTANCE}" , String.valueOf(guildCenterDist))
                );

            }

            if(teleport != null) {
                append(builder, Messages.getInstance().teleport.replace("{TIME}", DateHelper.format(teleport.getEnd())));
            }

            if(builder.length() != 0) MessageHelper.sendActionbar(player,colored(builder.toString()));
        });
    }

    private void append(StringBuilder builder, String string){
        if(builder.length() != 0) builder.append(Messages.getInstance().actionbar_append);
        builder.append(string);
    }

}
