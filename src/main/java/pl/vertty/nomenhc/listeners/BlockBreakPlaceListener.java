package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.LocationHelper;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

import java.util.Date;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


@AllArgsConstructor
public class BlockBreakPlaceListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        final Player player = event.getPlayer();
        final Guild pGuild = Guild.get(player.getName());
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())
                && !LocationHelper.isBlockLocation(event.getBlock().getLocation(), guild.getCenterLocation())) return;
        if(LocationHelper.isBlockLocation(event.getBlock().getLocation(), guild.getCenterLocation())
                && pGuild != null
                && !guild.getUuid().equals(pGuild.getUuid())){
            if(guild.getNextConquerDate().getTime() < System.currentTimeMillis()){
                guild.setLives(guild.getLives() - 1);
                guild.setNextConquerDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
                if(guild.getLives() == 0) guild.delete("Brak zyc!");
                if(pGuild.getLives() < 5) pGuild.setLives(pGuild.getLives() + 1);
                plugin.getServer().getOnlinePlayers().values().forEach(target -> target.sendMessage(colored(
                        Messages.getInstance().guildRemoveLife)
                        .replace("{TAG}", guild.getTag())
                        .replace("{OTAG}", pGuild.getTag())
                ));
            }
            else player.sendMessage(colored(Messages.getInstance().guildRemoveLifeError));
        }
        if(!player.isOp()){
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())) return;
        if(!player.isOp()){
            event.setCancelled(true);
        }
    }

}
