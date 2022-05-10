package pl.vertty.nomenhc.helpers;

import cn.nukkit.level.Location;
import pl.vertty.nomenhc.objects.configs.Config;


public class LocationHelper {

    public static boolean isBlockLocation(Location location1, Location location2){
        return location1.getFloorX() == location2.getFloorX() && location1.getFloorY() == location2.getFloorY() && location1.getFloorZ() == location2.getFloorZ();
    }

    public static double getDistanceMin(Location location1, Location location2){
        final double xDiff = Math.abs(location1.getFloorX() - location2.getFloorX()),
                zDiff = Math.abs(location1.getFloorZ() - location2.getFloorZ());
        return Math.min(xDiff,zDiff);
    }

    public static double getDistanceMax(Location location1, Location location2){
        final double xDiff = Math.abs(location1.getFloorX() - location2.getFloorX()),
                zDiff = Math.abs(location1.getFloorZ() - location2.getFloorZ());
        return Math.max(xDiff,zDiff);
    }

    public static boolean getBorderDistance(final Location loc) {
        return Math.abs(Config.getInstance().worldBorder - loc.getFloorX()) >= Config.getInstance().guildBorderDistance && Math.abs(Config.getInstance().worldBorder - loc.getFloorZ()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getFloorX()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getFloorZ()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getFloorX()) >= Config.getInstance().guildBorderDistance && Math.abs(Config.getInstance().worldBorder - loc.getFloorZ()) >= Config.getInstance().guildBorderDistance && Math.abs(Config.getInstance().worldBorder - loc.getFloorX()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getFloorZ()) >= Config.getInstance().guildBorderDistance;
    }

}
