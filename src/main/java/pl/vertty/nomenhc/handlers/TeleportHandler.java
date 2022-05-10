package pl.vertty.nomenhc.handlers;

import com.google.common.collect.Sets;
import pl.vertty.nomenhc.objects.Teleport;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

public class TeleportHandler {

    @Getter
    private static final Set<Teleport> teleports = Sets.newConcurrentHashSet();

    protected void add(Teleport guild){
        teleports.add(guild);
    }

    public static void remove(UUID uuid){
        teleports.remove(teleports.stream().filter(tp -> tp.getUuid() == uuid).findFirst().orElse(null));
    }

    public static Teleport get(UUID uuid){
        return teleports.stream().filter(teleport -> teleport.getUuid().equals(uuid)).findFirst().orElse(null);
    }

}
