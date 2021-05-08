package eu.dkcode.guilds;

import eu.dkcode.guilds.commands.GuildCommand;
import eu.dkcode.guilds.helpers.DatabaseHelper;
import eu.dkcode.guilds.listeners.*;
import eu.dkcode.guilds.objects.configs.Config;
import eu.dkcode.guilds.runnable.DatabaseRunnable;
import eu.dkcode.guilds.runnable.GuildExpireRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: GuildPlugin
 **/

public class GuildPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        Config.init(new File("./plugins/guilds"));
        Config.load("./plugins/guilds/config.json");
        Config.getInstance().toFile("./plugins/guilds/config.json");
    }

    @Override
    public void onEnable() {
        final DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.connect();
        databaseHelper.load();

        new DatabaseRunnable(this).register();
        new GuildExpireRunnable(this).register();

        new BlockBreakPlaceListener(this).register();
        new PlayerBucketEmptyFillListener(this).register();
        new PlayerMoveListener(this).register();
        new EntityExplodeListener(this).register();
        new BlockPistonExtendRetractListener(this).register();

        new GuildCommand("guild",this);
    }

}
