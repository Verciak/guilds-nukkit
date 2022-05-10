package pl.vertty.nomenhc.runnable;

import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.DataUtil;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.scoreboard.ScoreboardBuilder;
import pl.vertty.nomenhc.scoreboard.manager.ScoreboardManager;

@AllArgsConstructor
public class ScoreboardRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(plugin,this,0, 20);
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().values().forEach(player -> {
            User user = UserManager.get(player.getUniqueId());
            if(user == null){
                return;
            }
            if(user.canTurbo() == false){
                return;
            }
            if (Long.parseLong(user.getTurbo_drop()) > System.currentTimeMillis() && Long.parseLong(user.getTurbo_exp()) > System.currentTimeMillis()) {
                ScoreboardBuilder scoreboardBuilder = ScoreboardManager.newBuilder(player);
                scoreboardBuilder.setDisplayName(ChatUtil.fixColor("&8   &9&lINFORMACJE   &8"));
                scoreboardBuilder.setLine(1, ChatUtil.fixColor("&8>> &7Aktualnie posiadasz &9TurboDROP             "));
                scoreboardBuilder.addUpdater(scoreboard -> {
                    scoreboardBuilder.setLine(2,ChatUtil.fixColor("&8>> &7Pozostaly czas &9TurboDROP&8: &f" + (DataUtil.secondsToString(Long.parseLong(user.getTurbo_drop())).isEmpty() ? "NIEAKTYWNY" : DataUtil.secondsToString(Long.parseLong(user.getTurbo_drop())))));
                    scoreboardBuilder.setLine(5, ChatUtil.fixColor("&8>> &7Pozostaly czas &9TurboEXP&8: &f" + (DataUtil.secondsToString(Long.parseLong(user.getTurbo_exp())).isEmpty() ? "NIEAKTYWNY" : DataUtil.secondsToString(Long.parseLong(user.getTurbo_exp())))));
                }, 1);
                scoreboardBuilder.setLine(3, ChatUtil.fixColor(""));
                scoreboardBuilder.setLine(4, ChatUtil.fixColor("&8>> &7Aktualnie posiadasz &9TurboEXP   "));
                scoreboardBuilder.show();
            }
            if (Long.parseLong(user.getTurbo_drop()) < System.currentTimeMillis() && Long.parseLong(user.getTurbo_exp()) < System.currentTimeMillis()){
                user.setTurbo(false);
                if(user.canTurbo() == false) {
                    if (ScoreboardManager.getScoreboard(player) != null) {
                        ScoreboardManager.getScoreboard(player).hide();
                    }
                    ScoreboardManager.getScoreboards().clear();
                    ScoreboardManager.removeScoreboard(player);
                    user.setTurbo_exp("0");
                    user.setTurbo_drop("0");
                    UserManager.getAccounts().forEach(User::synchronize);
                }
            }
        });
    }


}
