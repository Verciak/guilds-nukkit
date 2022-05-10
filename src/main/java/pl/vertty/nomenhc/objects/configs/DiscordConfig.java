package pl.vertty.nomenhc.objects.configs;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class DiscordConfig {

    private static DiscordConfig instance;

    public String  ChannelID, Minecraft_Error, Minecraft_Taken, Minecraft_Take, Minecraft_NoReward, Minecraft_BC, NotFound_Title, NotFound_Description,  Ready_Title, Ready_Description, ToTake_Title, ToTake_Description,Taken_Title, Taken_Description, Discord_Color, Token, activity_type, activity_name, Nagroda;
    public int Discord_ToDelete;
    public boolean activity_enable;




    public DiscordConfig() {
        Token = "";
        activity_enable = true;
        activity_type = "WATCHING";
        activity_name = "test <3";
        ChannelID = "1";
        Nagroda = "give {NICK} 264 1";
        Discord_Color = "BLACK";
        Discord_ToDelete = 8;
        Taken_Title = "Gracz odebral nagrode!";
        Taken_Description = "Drugi raz jej nie dostaniesz!";
        ToTake_Title = "Juz mozesz odebrac nagrode!";
        ToTake_Description = "Jesli nie odebrales, odbierz ja /odbierz!";
        Ready_Title = "Nagroda do odebrania!";
        Ready_Description = "Odbierz ja przez /odbierz!";
        NotFound_Title = "Gracza nie znaleziono w bazie danych!";
        NotFound_Description = "Musisz wejsc do gry, aby zastac w niej zapisanym!";
        Minecraft_Error = "§cWystapil problem, wpisz ponownie komende!";
        Minecraft_Taken = "§cOdebrales juz nagrode!";
        Minecraft_Take = "§aGratuluje, odebrales nagrode, sprawdz eq!";
        Minecraft_NoReward = "§cMusisz odebrac nagrode na discord!";
        Minecraft_BC = "§cGracz {NICK} odebral nagrode z DC{N}NASZ DC: dc.nomenhc.eu";

    }

    public static DiscordConfig getInstance() {
        if (DiscordConfig.instance == null) {
            DiscordConfig.instance = fromDefaults();
        }
        return DiscordConfig.instance;
    }

    public static void init(File filePath){
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public static void load(final File file) {
        DiscordConfig.instance = fromFile(file);
        if (DiscordConfig.instance == null) {
            DiscordConfig.instance = fromDefaults();
        }
    }

    public static void load(final String file) {
        load(new File(file));
    }

    private static DiscordConfig fromDefaults() {
        final DiscordConfig config = new DiscordConfig();
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

    private static DiscordConfig fromFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, DiscordConfig.class);
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

