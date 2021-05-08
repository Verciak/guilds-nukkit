package eu.dkcode.guilds.objects;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import eu.dkcode.guilds.handlers.GuildHandler;
import eu.dkcode.guilds.helpers.DatabaseHelper;
import eu.dkcode.guilds.helpers.GuildHelper;
import eu.dkcode.guilds.objects.configs.Config;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: Guild
 **/

@Data
public class Guild extends GuildHandler {

    private final UUID uuid;
    private final String tag;
    private final String name;

    private String owner;
    private Set<String> coOwner;
    private Set<String> masters;

    private Integer points;
    private Integer kills;
    private Integer deaths;

    private Integer lives;

    private final Date createDate;
    private Date expireDate;
    private Date nextConquerDate;
    private Date tntProtectionExpireDate;

    private Set<String> members;
    private Set<UUID> allies;

    private Set<String> membersInvites;
    private Set<UUID> alliesInvites;

    private Boolean guildPvp;
    private Boolean allyPvp;

    private Integer guildSize;
    private final Integer centerX,centerY,centerZ;
    private final Integer homeX,homeY,homeZ;

    public Guild(String tag, String name, Player owner){
        uuid = UUID.randomUUID();
        this.tag = tag;
        this.name = name;

        this.owner = owner.getName();
        coOwner = Sets.newConcurrentHashSet();
        masters = Sets.newConcurrentHashSet();

        points = Config.getInstance().guildDefaultPoints;
        kills = 0;
        deaths = 0;
        lives = Config.getInstance().guildDefaultLives;

        createDate = new Date(System.currentTimeMillis());
        expireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2));
        nextConquerDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        tntProtectionExpireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        members = Sets.newConcurrentHashSet();
        members.add(this.owner);
        allies = Sets.newConcurrentHashSet();

        guildPvp = false;
        allyPvp = false;

        guildSize = Config.getInstance().guildDefaultSize;

        centerX = owner.getLocation().getBlockX();
        centerY = 30;
        centerZ = owner.getLocation().getBlockZ();


        homeX = owner.getLocation().getBlockX();
        homeY = owner.getLocation().getBlockY();
        homeZ = owner.getLocation().getBlockZ();

        add(this);
        insert();
    }

    private void insert(){
        DatabaseHelper.guildCollection.insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        DatabaseHelper.guildCollection.findOneAndUpdate(new Document("uuid", uuid.toString()), new Document("$set", Document.parse(new Gson().toJson(this))));
    }

    public void delete(String reason) {
        DatabaseHelper.guildCollection.findOneAndDelete(new Document("uuid", uuid.toString()));
        remove(this);
        GuildHelper.removeRoom(this);
        Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(colored("&cGildia &8[&4"+tag+"&8] &czostala usunieta! &8(&c"+reason+"&8)")));
    }

    public boolean isOnCuboid(Location location){
        if(!location.getWorld().getName().equals("world")) return false;
        return Math.abs(location.getBlockX() - centerX) <= guildSize && Math.abs(location.getBlockZ() - centerZ) <= guildSize;
    }

    public Location getCenterLocation(){
        return new Location(Bukkit.getWorld("world"),centerX,centerY,centerZ);
    }

    public Location getHomeLocation(){
        return new Location(Bukkit.getWorld("world"),homeX,homeY,homeZ);
    }


}
