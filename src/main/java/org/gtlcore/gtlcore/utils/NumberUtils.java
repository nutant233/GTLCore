package org.gtlcore.gtlcore.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import com.google.common.math.LongMath;

import java.text.DecimalFormat;

public class NumberUtils {

    private static final String[] UNITS = { "", "K", "M", "G", "T", "P", "E", "B" };

    public static String formatLong(long number) {
        DecimalFormat df = new DecimalFormat("#.##");
        double temp = number;
        int unitIndex = 0;
        while (temp >= 1000 && unitIndex < UNITS.length - 1) {
            temp /= 1000;
            unitIndex++;
        }
        return df.format(temp) + UNITS[unitIndex];
    }

    public static MutableComponent numberText(long number) {
        return Component.literal(formatLong(number));
    }

    public static int getFakeVoltageTier(long voltage) {
        long a = voltage;
        int b = 0;
        while (a / 4L >= 8L) {
            b++;
            a /= 4L;
        }
        return b;
    }

    public static long getVoltageFromFakeTier(int tier) {
        return LongMath.pow(4L, tier + 1) * 2;
    }
}
