package org.gtlcore.gtlcore.config;

import com.gregtechceu.gtceu.config.ConfigHolder;

public class GTConfigModify {

    public static void init() {
        ConfigHolder.INSTANCE.compat.energy.platformToEuRatio = 64;
        ConfigHolder.INSTANCE.compat.energy.euToPlatformRatio = 48;
        ConfigHolder.INSTANCE.compat.ae2.meHatchEnergyUsage = 1920;
    }
}
