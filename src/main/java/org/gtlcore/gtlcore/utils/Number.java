package org.gtlcore.gtlcore.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.text.DecimalFormat;

public class Number {

    private static final String[] UNITS = {"", "K", "M", "B", "T", "P", "E"};
    private static final Style NUMBER = Style.EMPTY;
    private static final Style UNIT = Style.EMPTY.withColor(0x0080FF);

    public static String formatLong(long number) {
        DecimalFormat df = new DecimalFormat("#.##");
        double temp = number;
        int unitIndex = 0;

        while (temp >= 1000 && unitIndex < UNITS.length - 1) {
            temp /= 1000;
            unitIndex++;
        }

        String formattedNumber = df.format(temp);

        if (unitIndex >= UNITS.length) {
            return String.format("%.2e", number);
        }

        return formattedNumber + UNITS[unitIndex];
    }

    public static Component numberText(long number) {
        String text = formatLong(number);
        if (text.matches(".*[a-zA-Z]$")) {
            return Component.literal(text.substring(0, text.length() - 1)).withStyle(NUMBER)
                    .append(Component.literal(text.substring(text.length() - 1)).withStyle(UNIT));
        } else if (text.contains("e+")) {
            String[] split = text.split("e\\+");
            return Component.literal(split[0]).withStyle(NUMBER)
                    .append(Component.literal("e+").withStyle(UNIT))
                    .append(Component.literal(split[1]).withStyle(NUMBER));
        }
        return Component.literal(text).withStyle(NUMBER);
    }
}
