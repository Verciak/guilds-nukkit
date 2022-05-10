package pl.vertty.nomenhc.helpers;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.handlers.BanHandler;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Config;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;

@NoArgsConstructor
public class DatabaseHelper {

    public static MongoCollection guildCollection;
    public static MongoCollection accountCollection;
    public static MongoCollection bansCollecion;


    @SneakyThrows
    public boolean connect(){
        final MongoClient mongoClient = new MongoClient(new MongoClientURI(Config.getInstance().mongoUri));
        final MongoDatabase mongoDatabase = mongoClient.getDatabase(Config.getInstance().mongoDatabase);
        guildCollection = mongoDatabase.getCollection("guilds");
        accountCollection = mongoDatabase.getCollection("accounts");
        bansCollecion = mongoDatabase.getCollection("bans");

        return true;
    }

    public void load(){
        final Gson gson = new Gson();
        guildCollection.find().forEach((Block<? super Document>) (Document document) -> {
            Guild guild = gson.fromJson(document.toJson(), Guild.class);
            GuildHandler.getGuilds().add(guild);
        });

        accountCollection.find().forEach((Block<? super Document>) (Document document) -> {
            User account = gson.fromJson(document.toJson(), User.class);
            UserManager.getAccounts().add(account);
        });

        bansCollecion.find().forEach((Block<? super Document>) document -> {
            try {
                Ban b = gson.fromJson(document.toJson(), Ban.class);
                BanHandler.bans.put(b.getName(), b);
            }  catch (Exception e) {
                System.out.println("[MongoDb] Failed to load ban: " + e.getMessage().length());
            }
        });
        System.out.println("[MongoDb] Loaded " + BanHandler.bans.size() + " bans!");
    }

    public static void synchronize(){
        GuildHandler.getGuilds().forEach(Guild::synchronize);
        UserManager.getAccounts().forEach(User::synchronize);
    }

}
