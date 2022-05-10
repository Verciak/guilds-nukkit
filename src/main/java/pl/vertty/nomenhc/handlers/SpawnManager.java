package pl.vertty.nomenhc.handlers;


import cn.nukkit.Player;
import cn.nukkit.level.Location;
import pl.vertty.nomenhc.objects.configs.Config;

public class SpawnManager {

    static Location spawn = new Location(Config.getInstance().spawnx, 0, Config.getInstance().spawnz);

    public static Location getLocation() {
        return spawn;
    }

    public static boolean isNonPvpArea(final Location loc) {
        final Location l2 = loc.getLocation().getLevel().getSpawnLocation().getLocation();
        final int distancex = Math.abs(loc.getFloorX() - l2.getFloorX());
        final int distancez = Math.abs(loc.getFloorZ() - l2.getFloorZ());
        return distancex <= Config.getInstance().spawndistance && distancez <= Config.getInstance().spawndistance;
    }

    public static void knockLinePvP(final Player p) {
        final double x = p.getFloorX() - p.getLevel().getSafeSpawn().getFloorX();
        final double z = p.getFloorZ() - p.getLevel().getSafeSpawn().getFloorZ();
        p.knockBack(p, 0.0, x, z, 0.2);
    }
}
