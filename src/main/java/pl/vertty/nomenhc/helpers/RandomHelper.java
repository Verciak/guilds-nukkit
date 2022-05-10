package pl.vertty.nomenhc.helpers;

import org.apache.commons.lang3.Validate;

import java.util.Random;


public class RandomHelper {

    public static int getRandomInt(int min, int max){
        Validate.isTrue(max > min, "Max has to be bigger than min");
        return new Random().nextInt(max - min + 1) + min;
    }

    public static double getRandomDouble(double min, double max){
        Validate.isTrue(max > min, "Max has to be bigger than min");
        return new Random().nextDouble() * (max - min) + min;
    }

    public static boolean getChance(double chance){
        return chance >= 100.0 || chance >= getRandomDouble(0.0, 100.0);
    }

    public static String getRandomString(int length) {
        final String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        final StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            code.append(string.charAt((int)(string.length() * Math.random())));
        return code.toString();
    }

}
