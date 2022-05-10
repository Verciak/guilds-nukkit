package pl.vertty.nomenhc.objects.configs;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class DropConfig {

    private static DropConfig instance;



    public DropConfig() {

    }

    public static DropConfig getInstance() {
        if (DropConfig.instance == null) {
            DropConfig.instance = fromDefaults();
        }
        return DropConfig.instance;
    }

    public static void init(File filePath){
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public static void load(final File file) {
        DropConfig.instance = fromFile(file);
        if (DropConfig.instance == null) {
            DropConfig.instance = fromDefaults();
        }
    }

    public static void load(final String file) {
        load(new File(file));
    }

    private static DropConfig fromDefaults() {
        final DropConfig config = new DropConfig();
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

    private static DropConfig fromFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, DropConfig.class);
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

