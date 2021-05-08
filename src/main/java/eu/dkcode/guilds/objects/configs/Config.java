package eu.dkcode.guilds.objects.configs;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: Config
 **/

public class Config {

    private static Config instance;

    public String mongoUri;
    public String mongoDatabase;


    public Integer guildDefaultLives;
    public Integer guildDefaultSize;
    public Integer guildDefaultPoints;



    public Config() {
        mongoUri = "mongodb://localhost:27017";
        mongoDatabase = "guilds";

        guildDefaultLives = 3;
        guildDefaultSize = 30;
        guildDefaultPoints = 1000;
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

