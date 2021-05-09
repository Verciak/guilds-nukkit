package eu.dkcode.guilds.helpers;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.dkcode.guilds.handlers.AccountHandler;
import eu.dkcode.guilds.handlers.GuildHandler;
import eu.dkcode.guilds.objects.Account;
import eu.dkcode.guilds.objects.Guild;
import eu.dkcode.guilds.objects.configs.Config;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: DatabaseHelper
 **/

@NoArgsConstructor
public class DatabaseHelper {

    public static MongoCollection guildCollection;
    public static MongoCollection accountCollection;


    @SneakyThrows
    public boolean connect(){
        final MongoClient mongoClient = new MongoClient(new MongoClientURI(Config.getInstance().mongoUri));
        final MongoDatabase mongoDatabase = mongoClient.getDatabase(Config.getInstance().mongoDatabase);
        guildCollection = mongoDatabase.getCollection("guilds");
        accountCollection = mongoDatabase.getCollection("accounts");

        return true;
    }

    public void load(){
        final Gson gson = new Gson();
        guildCollection.find().forEach((Block<? super Document>) (Document document) -> {
            Guild guild = gson.fromJson(document.toJson(), Guild.class);
            GuildHandler.getGuilds().add(guild);
        });

        accountCollection.find().forEach((Block<? super Document>) (Document document) -> {
            Account account = gson.fromJson(document.toJson(), Account.class);
            AccountHandler.getAccounts().add(account);
        });
    }

    public static void synchronize(){
        GuildHandler.getGuilds().forEach(Guild::synchronize);
        AccountHandler.getAccounts().forEach(Account::synchronize);
    }

}
