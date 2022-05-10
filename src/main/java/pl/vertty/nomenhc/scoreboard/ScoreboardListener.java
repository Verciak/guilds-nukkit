package pl.vertty.nomenhc.scoreboard;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import pl.vertty.nomenhc.scoreboard.manager.ScoreboardManager;


public class ScoreboardListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		ScoreboardBuilder scoreboardBuilder = ScoreboardManager.getScoreboard(event.getPlayer());
		if (scoreboardBuilder != null) {
			scoreboardBuilder.hide();
		}
	}
}