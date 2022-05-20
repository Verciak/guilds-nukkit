package pl.vertty.nomenhc.objects.configs;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Config {

    private static Config instance;

    public String mongoUri,mongoDatabase;
    public boolean spawn_status,wiadomosci_actionbar, ENABLE_TNT;
    public int wiadomosci_time,spawnx,spawnz,spawndistance;
    public boolean ANVIL_STATUS,BEACON_STATUS,BREWING_STATUS,CHEST_STATUS,DISPENSER_STATUS,DROPPER_STATUS,ENCHANTING_STATUS,ENDER_CHEST_STATUS,FURNACE_STATUS, HOPPER_STATUS,WORKBENCH_STATUS,HOPPER_MINECART_STATUS,STORAGE_MINECART_STATUS,TRAPPED_CHEST_STATUS;

    public Integer guildCenterY, guildDefaultLives,guildDefaultSize,guildMaxSize,guildDefaultPoints,guildMinimumDistance,guildExtendPrice,guildEnlargePrice,guildSpawnDistance,guildBorderDistance;
    public Integer playerStartPoints;
    public Double worldBorder;



    public Config() {
        mongoUri = "mongodb://localhost:27017";
        mongoDatabase = "guilds";

        playerStartPoints = 1000;

        wiadomosci_actionbar = true;
        wiadomosci_time = 20;
        spawndistance = 10;
        ANVIL_STATUS = true;
        BEACON_STATUS = true;
        BREWING_STATUS = true;
        CHEST_STATUS = true;
        DISPENSER_STATUS = true;
        DROPPER_STATUS = true;
        ENCHANTING_STATUS = true;
        ENDER_CHEST_STATUS = true;
        FURNACE_STATUS = true;
        HOPPER_STATUS = true;
        WORKBENCH_STATUS = true;
        HOPPER_MINECART_STATUS = true;
        STORAGE_MINECART_STATUS = true;
        TRAPPED_CHEST_STATUS = true;

        guildDefaultLives = 3;
        guildDefaultSize = 20;
        guildMaxSize = 40;
        guildCenterY = 30;
        guildDefaultPoints = 1000;
        guildMinimumDistance = 80;
        guildExtendPrice = 16;
        guildEnlargePrice = 16;
        guildSpawnDistance = 250;
        guildBorderDistance = 200;
        worldBorder = 5000.0;

        ENABLE_TNT = false;
    }

    public static Config getInstance() {
        if (Config.instance == null) {
            Config.instance = fromDefaults();
        }
        return Config.instance;
    }

    public static void init(File filePath){
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public static void load(final File file) {
        Config.instance = fromFile(file);
        if (Config.instance == null) {
            Config.instance = fromDefaults();
        }
    }

    public static void load(final String file) {
        load(new File(file));
    }

    private static Config fromDefaults() {
        final Config config = new Config();
        return config;
    }

    public void toFile(final String file) {
        this.toFile(new File(file));
    }

    public void toFile(final File file) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String jsonconfig = gson.toJson(this);
        try {
            final FileWriter writer = new FileWriter(file);
            writer.write(jsonconfig);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Config fromFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, Config.class);
        }
        catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

