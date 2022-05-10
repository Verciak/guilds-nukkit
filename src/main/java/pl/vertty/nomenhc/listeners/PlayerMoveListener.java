package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.item.Item;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.handlers.SpawnManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Config;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.helpers.MessageHelper;


@AllArgsConstructor
public class PlayerMoveListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMovee(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(player.getName()),guildFrom = Guild.get(event.getFrom()), guildTo = Guild.get(event.getTo());
        if(guildTo == guildFrom) return;
        String messageColor;
        if(guildTo == null){
            messageColor = guild == null ? "4" : guild.getAllies().contains(guildFrom.getUuid()) ? "9" : guild.getUuid().equals(guildFrom.getUuid()) ? "2" : "4";
            MessageHelper.sendTitle(player,"&"+messageColor+"Gildie","&"+messageColor+"Opuscicles teren gildii &8[&"+messageColor+guildFrom.getTag()+"&8]");
            return;
        }
        messageColor = guild == null ? "4" : guild.getAllies().contains(guildTo.getUuid()) ? "9" : guild.getUuid().equals(guildTo.getUuid()) ? "2" : "4";
        MessageHelper.sendTitle(player,"&"+messageColor+"Gildie","&"+messageColor+"Wkroczyles na teren gildii &8[&"+messageColor+guildTo.getTag()+"&8]");
    }


    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent e) {
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            if (e.getTo().getX() <= Config.getInstance().spawndistance && e.getTo().getX() >= -Config.getInstance().spawndistance && e.getTo().getZ() <= Config.getInstance().spawndistance && e.getTo().getZ() >= -Config.getInstance().spawndistance) {
                e.setCancelled(true);
                e.getPlayer().getInventory().addItem(new Item(Item.ENDER_PEARL, 0,1));
            }
        }
    }

    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent e) {
        if (Config.getInstance().spawn_status) {
            final Player p = e.getPlayer();
            final Combat combat = CombatManager.getCombat(p);
            if (combat != null && combat.hasFight() && SpawnManager.isNonPvpArea(e.getTo())) {
                SpawnManager.knockLinePvP(p);
                p.sendMessage(ChatUtil.fixColor("&cPodczas pvp nie mozna wejsc na spawn!"));
            }


//            if (user.hasFight()) {
////                if (!p.hasPermission(Messages.getInstance().spawn_permission)) {
//                    if (SpawnManager.isSpawnLiniaKnock(e.getTo()) && !SpawnManager.isSpawnLiniaKnock(e.getFrom())) {
//                        SpawnManager.knockLinePvP(p);
//                    }
////                }
//            }
        }
    }
}
