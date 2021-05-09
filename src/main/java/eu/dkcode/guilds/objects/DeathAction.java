package eu.dkcode.guilds.objects;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 09.05.2021
 * @Class: Death
 **/

@AllArgsConstructor
public class DeathAction {

    private final UUID killerUUID, playerUUID;
    private final String killerName, playerName;
    private final int pointsGained, pointsLost;
    private final Date date;

}
