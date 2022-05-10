package pl.vertty.nomenhc.objects;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
public class DeathAction {

    private final UUID killerUUID, playerUUID;
    private final String killerName, playerName;
    private final int pointsGained, pointsLost;
    private final Date date;

}
