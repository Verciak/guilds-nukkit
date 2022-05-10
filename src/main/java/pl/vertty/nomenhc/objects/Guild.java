package pl.vertty.nomenhc.objects;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Location;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import pl.vertty.nomenhc.helpers.GuildHelper;
import pl.vertty.nomenhc.objects.configs.Config;
import lombok.Data;
import org.bson.Document;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;

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
    private Integer homeX,homeY,homeZ;

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

        membersInvites = Sets.newConcurrentHashSet();
        allies = Sets.newConcurrentHashSet();
        alliesInvites = Sets.newConcurrentHashSet();

        guildPvp = false;
        allyPvp = false;

        guildSize = Config.getInstance().guildDefaultSize;

        centerX = owner.getLocation().getFloorX();
        centerY = Config.getInstance().guildCenterY;
        centerZ = owner.getLocation().getFloorZ();


        homeX = owner.getLocation().getFloorX();
        homeY = owner.getLocation().getFloorY();
        homeZ = owner.getLocation().getFloorZ();

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
        Server.getInstance().getOnlinePlayers().values().forEach(target -> target.sendMessage(colored("&cGildia &8[&4"+tag+"&8] &czostala usunieta! &8(&c"+reason+"&8)")));
    }

    public boolean isOnCuboid(Location location){
        return Math.abs(location.getFloorX() - centerX) <= guildSize && Math.abs(location.getFloorZ() - centerZ) <= guildSize;
    }

    public Location getCenterLocation(){
        return new Location(centerX,centerY,centerZ);
    }

    public Location getHomeLocation(){
        return new Location(homeX,homeY,homeZ);
    }

    public void statIncrement(int points, int kills, int deaths){
        this.points = this.points + points;
        this.kills = this.kills + kills;
        this.deaths = this.deaths + deaths;
    }

}
