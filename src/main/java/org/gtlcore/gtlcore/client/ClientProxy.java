package org.gtlcore.gtlcore.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.gtlcore.gtlcore.common.CommonProxy;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();
        init();
    }

    public static void init() {
        CraftingUnitModelProvider.initCraftingUnitModels();
    }
}
