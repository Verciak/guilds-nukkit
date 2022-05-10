package pl.vertty.nomenhc.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.BanHandler;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.objects.Combat;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.objects.drop.Drop;


@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent event) {
        Ban b = BanHandler.getBan(event.getPlayer().getName());
        for(Ban bans : BanHandler.bans.values()){
            if(event.getPlayer().getAddress().contains(bans.getIp())){
                Ban other = BanHandler.getBan(bans.getName());
                event.setKickMessage(ChatUtil.fixColor(BanHandler.getReason(false, other)));
                event.setCancelled(true);
                return;
            }
            if(String.valueOf(event.getPlayer().getLoginChainData().getClientId()).contains(bans.getClientId())){
                Ban other = BanHandler.getBan(bans.getName());
                event.setKickMessage(ChatUtil.fixColor(BanHandler.getReason(false, other)));
                event.setCancelled(true);
                return;
            }
            if(event.getPlayer().getLoginChainData().getDeviceId().contains(bans.getDeviceid())){
                Ban other = BanHandler.getBan(bans.getName());
                event.setKickMessage(ChatUtil.fixColor(BanHandler.getReason(false, other)));
                event.setCancelled(true);
                return;
            }
            if(event.getPlayer().getLoginChainData().getIdentityPublicKey().contains(bans.getIdentityPublicKey())){
                Ban other = BanHandler.getBan(bans.getName());
                event.setKickMessage(ChatUtil.fixColor(BanHandler.getReason(false, other)));
                event.setCancelled(true);
                return;
            }
        }

        if (b != null) {
            if (!b.isExpired()) {
                event.setKickMessage(ChatUtil.fixColor(BanHandler.getReason(false, b)));
                event.setCancelled(true);
                return;
            }
            b.delete();
            BanHandler.bans.remove(b.getName());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final User account = User.get(player.getUniqueId());
        if(account == null){
            new User(player);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        e.setJoinMessage("");
        final Combat combat = CombatManager.getCombat(p);
        if (combat == null) {
            CombatManager.createCombat(p);
        }
    }
}
