package pl.vertty.nomenhc.objects;

import com.google.gson.Gson;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import org.bson.Document;

public class Ban {
    private String name;

    private String uuid;

    private String ip;

    private String reason;

    private String admin;

    private String exire;
    private String deviceid;
    private String IdentityPublicKey;
    private String ClientId;


    public Ban(String uuid, String name, String ip, String reason, String admin, long expire, String deviceid, String IdentityPublicKey, String ClientId) {
        this.name = name;
        this.uuid = uuid;
        this.ip = ip;
        this.reason = reason;
        this.admin = admin;
        this.exire = String.valueOf(expire);
        this.deviceid = deviceid;
        this.IdentityPublicKey = IdentityPublicKey;
        this.ClientId = ClientId;
        insert();
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public String getIdentityPublicKey() {
        return this.IdentityPublicKey;
    }

    public String getClientId() {
        return this.ClientId;
    }

    private void insert() {
        DatabaseHelper.bansCollecion.insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void delete() {
        DatabaseHelper.bansCollecion.findOneAndDelete(new Document("name", this.name));
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public String getIp() {
        return this.ip;
    }

    public String getReason() {
        return this.reason;
    }

    public String getAdmin() {
        return this.admin;
    }

    public boolean isExpired() {
        return (Long.parseLong(this.exire) != 0L && Long.parseLong(this.exire) < System.currentTimeMillis());
    }

    public long getExire() {
        return Long.parseLong(this.exire);
    }
}
