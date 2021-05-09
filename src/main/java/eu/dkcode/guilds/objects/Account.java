package eu.dkcode.guilds.objects;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import eu.dkcode.guilds.handlers.AccountHandler;
import eu.dkcode.guilds.helpers.DatabaseHelper;
import lombok.Data;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: Account
 **/

@Data
public class Account extends AccountHandler {

    private final UUID uuid;
    private final String name;

    private int points,kills,deaths;

    private final Set<DeathAction> deathActions;

    public Account(Player player){
        this.uuid = player.getUniqueId();
        this.name = player.getName();

        this.points = 1000;
        this.kills = 0;
        this.deaths = 0;

        deathActions = Sets.newConcurrentHashSet();
        add(this);
        insert();
    }


    private void insert(){
        DatabaseHelper.accountCollection.insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        DatabaseHelper.accountCollection.findOneAndUpdate(new Document("uuid", uuid.toString()), new Document("$set", Document.parse(new Gson().toJson(this))));
    }

    public void delete() {
        DatabaseHelper.accountCollection.findOneAndDelete(new Document("uuid", uuid.toString()));
    }

    public void statsIncrement(int points, int kills, int deaths) {
        this.points = this.points + points;
        this.kills = this.kills + kills;
        this.deaths = this.deaths + deaths;
    }
}
