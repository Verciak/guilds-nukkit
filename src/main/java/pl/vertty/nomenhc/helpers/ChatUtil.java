package pl.vertty.nomenhc.helpers;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.ProjectileHitEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ProjectileItem;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");



    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static String getDate(long time) {
        return dateFormat.format(new Date(time));
    }

    public static String getTime(long time) {
        return timeFormat.format(new Date(time));
    }

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for (int i = 0; i < m.groupCount(); i++) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        continue;
                    if (m.group(1) != null && !m.group(1).isEmpty())
                        years = Integer.parseInt(m.group(1));
                    if (m.group(2) != null && !m.group(2).isEmpty())
                        months = Integer.parseInt(m.group(2));
                    if (m.group(3) != null && !m.group(3).isEmpty())
                        weeks = Integer.parseInt(m.group(3));
                    if (m.group(4) != null && !m.group(4).isEmpty())
                        days = Integer.parseInt(m.group(4));
                    if (m.group(5) != null && !m.group(5).isEmpty())
                        hours = Integer.parseInt(m.group(5));
                    if (m.group(6) != null && !m.group(6).isEmpty())
                        minutes = Integer.parseInt(m.group(6));
                    if (m.group(7) == null)
                        break;
                    if (m.group(7).isEmpty())
                        break;
                    seconds = Integer.parseInt(m.group(7));
                    break;
                }
            }
            if (!found)
                return -1L;
            Calendar c = new GregorianCalendar();
            if (years > 0)
                c.add(1, years * (future ? 1 : -1));
            if (months > 0)
                c.add(2, months * (future ? 1 : -1));
            if (weeks > 0)
                c.add(3, weeks * (future ? 1 : -1));
            if (days > 0)
                c.add(5, days * (future ? 1 : -1));
            if (hours > 0)
                c.add(11, hours * (future ? 1 : -1));
            if (minutes > 0)
                c.add(12, minutes * (future ? 1 : -1));
            if (seconds > 0)
                c.add(13, seconds * (future ? 1 : -1));
            Calendar max = new GregorianCalendar();
            max.add(1, 10);
            if (c.after(max))
                return max.getTimeInMillis();
            return c.getTimeInMillis();
        } catch (Exception e) {
            return -1L;
        }
    }

    public static Player getDamager(final EntityDamageByEntityEvent e) {
        final Entity damager = e.getDamager();
        if (damager instanceof Player) {
            return (Player)damager;
        }
        return null;
    }

    public static Boolean sendActionBar(final Player p, final String m) {
        p.sendActionBar(fixColor(m));
        return false;
    }

    public static void sendTitleRestart(final Player p, final String title, final String subttitle) {
        sendTitle(p, title, subttitle, 1, 20, 1);
    }

    public static void sendTitle(final Player p, final String title, final String subttitle) {
        sendTitle(p, title, subttitle, 20, 80, 20);
    }

    public static void sendTitle(final Player player, String title, String subtitle, final int fadeIn, final int stay, final int fadeOut) {
        if (title == null) {
            title = "";
        }
        if (subtitle == null) {
            subtitle = "";
        }
        player.sendTitle(fixColor(title), fixColor(subtitle), fadeIn, stay, fadeOut);
    }

    public static String fixColor(final String s) {
        if (s == null) {
            return "";
        }

        return TextFormat.colorize('&', s).replace(">>", "»").replace("<<", "«").replace("*", "\u25cf").replace("{O}", "\u2022");
    }

    public static Collection<String> fixColor(final Collection<String> collection) {
        final Collection<String> local = new ArrayList<String>();
        for (final String s : collection) {
            local.add(fixColor(s));
        }
        return local;
    }

    public static List<String> fColor(final List<String> strings) {
        final List<String> colors = new ArrayList<String>();
        for (final String s : strings) {
            colors.add(fixColor(s));
        }
        return colors;
    }

    public static int floor(final double value) {
        final int i = (int)value;
        return (value < i) ? (i - 1) : i;
    }

    public static double round(final double value, final int decimals) {
        final double p = Math.pow(10.0, decimals);
        return Math.round(value * p) / p;
    }

    public static String[] fixColor(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = fixColor(array[i]);
        }
        return array;
    }

    public static boolean sendMessage(final CommandSender sender, final String message, final String permission) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, message);
        }
        return permission != null && permission != "" && sender.hasPermission(permission) && sendMessage(sender, message);
    }

    public static boolean sendMessage(final CommandSender sender, final String message) {
        if (sender instanceof Player) {
            if (message != null || message != "") {
                sender.sendMessage(fixColor(message));
            }
        }
        else {
            sender.sendMessage(TextFormat.colorize(fixColor(message)));
        }
        return true;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> collection, final String message) {
        for (final CommandSender cs : collection) {
            sendMessage(cs, message);
        }
        return true;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> collection, final String message, final String permission) {
        for (final CommandSender cs : collection) {
            sendMessage(cs, message, permission);
        }
        return true;
    }

    public static boolean containsIgnoreCase(final String[] array, final String element) {
        for (final String s : array) {
            if (s.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlphaNumeric(final String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }

    public static boolean isFloat(final String string) {
        return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
    }

    public static boolean isInteger(final String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }

}
