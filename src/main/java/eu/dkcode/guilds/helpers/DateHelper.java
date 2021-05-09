package eu.dkcode.guilds.helpers;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 05.05.2021
 * @Class: DateHelper
 **/

public class DateHelper {

    public static String format(Long time){
        if(time <= System.currentTimeMillis()) return "Czas minal!";

        long d = Math.abs(time - System.currentTimeMillis())/1000;
        int days = Math.toIntExact(d/86400);
        d -= days * 86400L;

        int hours = Math.toIntExact(d/3600) % 24;
        d -= hours * 3600;

        int minutes = Math.toIntExact(d/60) % 60;
        d -= minutes * 60;

        int seconds = Math.toIntExact(d % 60);
        d -= seconds % 60;

        int ms = Math.toIntExact(d * 1000);
        if(days == 0 && minutes == 0 && hours == 0 && seconds == 0 && ms > 0)
            return "< 1 sek";
        if(days == 0 && minutes == 0 && hours == 0 && seconds == 0 && ms == 0)
            return "Czas minal!";

        return (days > 0 ? days + " dni" : "") + (hours > 0 ? hours + " godz" : "") +
                (minutes > 0 ? minutes + " min" : "") + (seconds > 0 ? seconds + " sek " : "") + (minutes<1&&hours<1&&days<1 ? (ms > 0 ? ms + " ms " : "") : "");
    }


}
