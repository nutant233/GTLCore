package org.gtlcore.gtlcore;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gtlcore.gtlcore.client.ClientProxy;
import org.gtlcore.gtlcore.common.CommonProxy;

@Mod(GTLCore.MOD_ID)
public class GTLCore {
    public static final String MOD_ID = "gtlcore";
    public static final String NAME = "GTL Core";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID,name);
    }

    public GTLCore() {
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

        // Most other events are fired on Forge's bus.
        // If we want to use annotations to register event listeners,
        // we need to register our object like this!
        MinecraftForge.EVENT_BUS.register(this);
    }
}
