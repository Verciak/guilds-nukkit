package pl.vertty.nomenhc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.Entity;
import cn.nukkit.plugin.PluginBase;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import pl.vertty.nomenhc.commands.Command;
import pl.vertty.nomenhc.commands.CommandManager;
import pl.vertty.nomenhc.commands.admin.*;
import pl.vertty.nomenhc.commands.guilds.GuildCommand;
import pl.vertty.nomenhc.commands.helper.ClearCommand;
import pl.vertty.nomenhc.commands.user.DropCommand;
import pl.vertty.nomenhc.commands.user.PlayerCommand;
import pl.vertty.nomenhc.commands.user.RewardCommand;
import pl.vertty.nomenhc.commands.user.TestCommand;
import pl.vertty.nomenhc.entity.QueryThread;
import pl.vertty.nomenhc.entity.blockentity.ChestTile;
import pl.vertty.nomenhc.entity.blockentity.FixedHopperTile;
import pl.vertty.nomenhc.entity.projectile.*;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import pl.vertty.nomenhc.inventory.FakeInventoriesListener;
import pl.vertty.nomenhc.listeners.*;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.DiscordConfig;
import pl.vertty.nomenhc.objects.configs.DropConfig;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.objects.entity.OptimizedDroppedItem;
import pl.vertty.nomenhc.objects.entity.OptimizedXPOrb;
import pl.vertty.nomenhc.runnable.ActionBarRunnable;
import pl.vertty.nomenhc.runnable.DatabaseRunnable;
import pl.vertty.nomenhc.runnable.GuildExpireRunnable;
import pl.vertty.nomenhc.runnable.ScoreboardRunnable;
import pl.vertty.nomenhc.scoreboard.ScoreboardListener;

import javax.security.auth.login.LoginException;
import java.io.File;

public class GuildPlugin extends PluginBase {


    public static GuildPlugin plugin;
    private static QueryThread query;

    public void onLoadDropConfig() {
        DropConfig.init(new File("./plugins/guilds"));
        DropConfig.load("./plugins/guilds/DropConfig.json");
        DropConfig.getInstance().toFile("./plugins/guilds/DropConfig.json");
    }

    public void onLoadConfig() {
        Config.init(new File("./plugins/guilds"));
        Config.load("./plugins/guilds/config.json");
        Config.getInstance().toFile("./plugins/guilds/config.json");
    }

    public void onLoadMessages() {
        Messages.init(new File("./plugins/guilds"));
        Messages.load("./plugins/guilds/messages.json");
        Messages.getInstance().toFile("./plugins/guilds/messages.json");
    }

    public void onLoadDiscord() {
        DiscordConfig.init(new File("./plugins/guilds"));
        DiscordConfig.load("./plugins/guilds/DiscordConfig.json");
        DiscordConfig.getInstance().toFile("./plugins/guilds/DiscordConfig.json");
    }

    public static GuildPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        onLoadConfig();
        onLoadMessages();
        onLoadDropConfig();
        onLoadDiscord();
        final DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.connect();
        databaseHelper.load();
//        DropManager.loadDrops();
//        GuildPlugin.query = new QueryThread();
//        for (Player p : Server.getInstance().getOnlinePlayers().values()) {
//            final Combat c = CombatManager.getCombat(p);
//            if (c == null) {
//                CombatManager.createCombat(p);
//            }
//        }
        // runnable
        new DatabaseRunnable(this).register();
//        new GuildExpireRunnable(this).register();
//        new ActionBarRunnable(this).register();
//        new ScoreboardRunnable(this).register();

        // listeners
//        this.getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
//        new DropListener(this).register();
//        new BlockBreakPlaceListener(this).register();
//        new CommandBlockListener(this).register();
//        new EntityDamageByEntityListener(this).register();
//        new EntityExplodeListener(this).register();
//        new InventoryOpenListener(this).register();
//        new PlayerBucketEmptyFillListener(this).register();
//        new PlayerDeathListener(this).register();
        new PlayerJoinListener(this).register();
//        new PlayerMoveListener(this).register();
//        new PlayerQuitListener(this).register();
//        new TeleportCancelListener(this).register();
//        new UnknownCommandListener(this).register();
//        new WeatherListener(this).register();
//        new FakeInventoriesListener(this).register();
        // commands
//        registerCommand(new PlayerCommand());
//        registerCommand(new GuildCommand());
//        registerCommand(new ConfigReloadCommand());
//        registerCommand(new TeleportCommand());
//        registerCommand(new GamemodeCommand());
        registerCommand(new BanCommand());
        registerCommand(new UnBanCommand());
//        registerCommand(new DeviceCommand());
//        registerCommand(new DropCommand());
//        registerCommand(new RewardCommand());
//        registerCommand(new TestCommand());
//        registerCommand(new ClearCommand());
        //discord
        registerJDA();
        //entity
        Entity.registerEntity("Item", OptimizedDroppedItem.class);
        Entity.registerEntity("XpOrb", OptimizedXPOrb.class);
//        Entity.registerEntity(Snowball.class.getSimpleName(), Snowball.class);
//        Entity.registerEntity(Egg.class.getSimpleName(), Egg.class);
//        Entity.registerEntity(EnderPearl.class.getSimpleName(), EnderPearl.class);
//        Entity.registerEntity("Arrow", Arrow.class);
//        BlockEntity.registerBlockEntity("Chest", ChestTile.class);
//        BlockEntity.registerBlockEntity("Hopper", FixedHopperTile.class);
//        Entity.registerEntity("PrimedTnt", PrimedTNT.class);

    }

    @Override
    public void onDisable() {
        Server.getInstance().getScheduler().cancelAllTasks();
//        for (final Player p : Server.getInstance().getOnlinePlayers().values()) {
//            CombatManager.removeCombat(p);
//        }
        GuildPlugin.query.shutdown();
    }
    public static QueryThread getQuery() {
        return GuildPlugin.query;
    }
    public static void registerCommand(final Command command) {
        CommandManager.register(command);
    }


    private void registerJDA() {
        try {
            JDABuilder jDABuilder = JDABuilder.createDefault(DiscordConfig.getInstance().Token);
            jDABuilder.setBulkDeleteSplittingEnabled(false);
            if (DiscordConfig.getInstance().activity_enable) {
                if (DiscordConfig.getInstance().activity_name == null) {
                    this.getLogger().warning("[!] Napis aktywnosci nie moze byc null, ustaw go lub wylacz ta opcje.");
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
                if (DiscordConfig.getInstance().activity_type.equals("WATCHING"))
                    jDABuilder.setActivity(Activity.watching(DiscordConfig.getInstance().activity_name.replace("{ONLINE}", String.valueOf(Server.getInstance().getOnlinePlayers().size()))));
                if (DiscordConfig.getInstance().activity_type.equals("LISTENING"))
                    jDABuilder.setActivity(Activity.listening(DiscordConfig.getInstance().activity_name.replace("{ONLINE}", String.valueOf(Server.getInstance().getOnlinePlayers().size()))));
                if (DiscordConfig.getInstance().activity_type.equals("PLAYING"))
                    jDABuilder.setActivity(Activity.playing(DiscordConfig.getInstance().activity_name.replace("{ONLINE}", String.valueOf(Server.getInstance().getOnlinePlayers().size()))));
            }
            jDABuilder.addEventListeners(new ChannelListener(this));
            jDABuilder.build();
        } catch (LoginException loginException) {
            this.getLogger().info("[!] Nieprawidlowy token bota! Sprawdz jego poprawnosc i sprobuj ponownie.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

}
