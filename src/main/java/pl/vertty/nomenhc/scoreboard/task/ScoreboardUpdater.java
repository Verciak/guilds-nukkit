package pl.vertty.nomenhc.scoreboard.task;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.scoreboard.ScoreboardBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ScoreboardUpdater extends AsyncTask {

	private final ScoreboardBuilder scoreboardBuilder;
	private final Map<Integer, Consumer<ScoreboardBuilder>> TASKS;
	private int time;
	private boolean starting;

	public ScoreboardUpdater(ScoreboardBuilder scoreboardBuilder) {
		this.scoreboardBuilder = scoreboardBuilder;
		this.TASKS = new HashMap<>();
	}

	public ScoreboardBuilder addUpdater(Consumer<ScoreboardBuilder> scoreboardBuilder, int seconds) {
		if (seconds <= 0) {
			throw new IllegalArgumentException("Время задержки должно быть > 0");
		}
		this.TASKS.put(seconds, scoreboardBuilder);
		return this.scoreboardBuilder;
	}

	public void start() {
		if (!this.starting) {
			Server.getInstance().getScheduler().scheduleRepeatingTask(GuildPlugin.getPlugin(), this, 20);
			this.starting = true;
		}
	}

	public void stop() {
		if (this.starting) {
			Server.getInstance().getScheduler().cancelTask(this.getTaskId());
		}
	}

	@Override
	public void onRun() {
		if (this.starting) {
			if (this.scoreboardBuilder.getPlayer().spawned) {
				CompletableFuture.runAsync(() -> {
					this.TASKS.entrySet().stream().filter(entry -> this.time % entry.getKey() == 0).forEach(entry -> {
						entry.getValue().accept(this.scoreboardBuilder);
						this.time = 0;
					});
					this.time++;
				});
			}
		}
	}
}