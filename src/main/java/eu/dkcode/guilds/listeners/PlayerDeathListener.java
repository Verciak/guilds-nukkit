package eu.dkcode.guilds.listeners;

import eu.dkcode.guilds.GuildPlugin;
import eu.dkcode.guilds.objects.Account;
import eu.dkcode.guilds.objects.DeathAction;
import eu.dkcode.guilds.objects.Guild;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: PlayerDeathListener
 **/

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        final Player player = event.getEntity(), killer = event.getEntity().getKiller();
        if(killer == null) return;
        final Account pAccount = Account.get(player.getUniqueId()), kAccount = Account.get(player.getUniqueId());
        final Guild pGuild = Guild.get(player.getName()), kGuild = Guild.get(killer.getName());
        if(pAccount == null || kAccount == null) return;

        int accountPointsAdd = (int) (30 + (pAccount.getPoints() - kAccount.getPoints()) * 0.2),
                accountPointsTake = (accountPointsAdd / 6) * 2;

        final DeathAction deathAction = new DeathAction(
                killer.getUniqueId(),
                player.getUniqueId(),
                killer.getName(),
                killer.getName(),
                accountPointsAdd,
                accountPointsTake,
                new Date(System.currentTimeMillis())
        );

        pAccount.getDeathActions().add(deathAction);
        kAccount.getDeathActions().add(deathAction);

        pAccount.statsIncrement(accountPointsTake,0,1);
        kAccount.statsIncrement(accountPointsAdd,1,0);

        int guildPointsAdd = 30, guildPointsTake = -30;

        if(pGuild != null && kGuild != null && (pGuild.getUuid().equals(kGuild.getUuid()) || pGuild.getAllies().contains(kGuild.getUuid()))){
            guildPointsAdd = -guildPointsAdd;
            guildPointsTake = 0;
        }

        if(pGuild != null) pGuild.statIncrement(guildPointsTake, 0, 1);
        if(kGuild != null) kGuild.statIncrement(guildPointsAdd, 1, 0);
    }

}
