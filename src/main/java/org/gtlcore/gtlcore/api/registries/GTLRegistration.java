package org.gtlcore.gtlcore.api.registries;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import org.gtlcore.gtlcore.GTLCore;

public class GTLRegistration {

    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GTLCore.MOD_ID);

    static {
        GTLRegistration.REGISTRATE.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    }

    private GTLRegistration() {/**/}

}