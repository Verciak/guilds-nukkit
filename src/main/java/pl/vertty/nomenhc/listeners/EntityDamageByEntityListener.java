package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.TimeUtil;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class EntityDamageByEntityListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player d = ChatUtil.getDamager(e);
        if (d == null) {
            return;
        }
        final Player p = (Player)e.getEntity();
        if (p.equals(d)) {
            return;
        }
        final Combat entity = CombatManager.getCombat(p);
        if (entity == null) {
            return;
        }
        if (!entity.hasFight()) {
            ChatUtil.sendMessage(p, Messages.getInstance().wiadomosci_chatdamager.replace("{TIME}", String.valueOf(Config.getInstance().wiadomosci_time)));
        }
        entity.setLastAttactTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(Config.getInstance().wiadomosci_time));
        if (entity.getLastAttactkPlayer() != d) {
            entity.setLastAsystPlayer(entity.getLastAttactkPlayer());
            entity.setLastAsystTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(Config.getInstance().wiadomosci_time));
        }
        entity.setLastAttactkPlayer(d);
        if(e.getDamager() instanceof Player) {
            final Combat atacker = CombatManager.getCombat(d);
            if (atacker == null) {
                return;
            }
            if (!atacker.hasFight()) {
                ChatUtil.sendMessage((Player)e.getDamager(), Messages.getInstance().wiadomosci_chatplayer.replace("{TIME}", String.valueOf(Config.getInstance().wiadomosci_time)));
            }
            atacker.setLastAttactTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(Config.getInstance().wiadomosci_time));
            if (atacker.getLastAttactkPlayer() != d) {
                atacker.setLastAsystPlayer(atacker.getLastAttactkPlayer());
                atacker.setLastAsystTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(Config.getInstance().wiadomosci_time));
            }
            atacker.setLastAttactkPlayer(d);
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        final Entity entity = event.getEntity(), damageEntity = event.getDamager();
        if(!(entity instanceof Player) || !(damageEntity instanceof Player)) return;
        final Player player = (Player) entity, damagePlayer = (Player) damageEntity;
        final Guild pGuild = Guild.get(player.getName()), dGuild = Guild.get(damagePlayer.getName());
        if(pGuild == null || dGuild == null) return;
        if(pGuild.getUuid().equals(dGuild.getUuid()) && !pGuild.getGuildPvp()) {
            event.setDamage(0.0f);
            event.setCancelled(true);
            return;
        }
        if(!pGuild.getAllies().contains(dGuild.getUuid())) return;
        if(dGuild.getAllyPvp()) return;
        event.setDamage(0.0f);
        event.setCancelled(true);
    }

}
