package pl.vertty.nomenhc.handlers;

import cn.nukkit.Player;
import pl.vertty.nomenhc.objects.Combat;

import java.util.concurrent.ConcurrentHashMap;

public class CombatManager
{
    private static final ConcurrentHashMap<Player, Combat> combats;

    static {
        combats = new ConcurrentHashMap<Player, Combat>();
    }

    public static Combat getCombat(final Player p) {
        return CombatManager.combats.get(p);
    }

    public static void createCombat(final Player p) {
        final Combat combat = new Combat(p);
        CombatManager.combats.put(p, combat);
    }

    public static void removeCombat(final Player p) {
        CombatManager.combats.remove(p);
    }

    public static ConcurrentHashMap<Player, Combat> getCombats() {
        return CombatManager.combats;
    }
}
