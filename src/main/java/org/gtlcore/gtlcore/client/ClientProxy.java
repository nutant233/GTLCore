package org.gtlcore.gtlcore.client;

import org.gtlcore.gtlcore.common.CommonProxy;
import org.gtlcore.gtlcore.common.data.GTLEntityTypes;

import com.gregtechceu.gtceu.client.renderer.entity.GTExplosiveRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();
        init();
    }

    public static void init() {
        CraftingUnitModelProvider.initCraftingUnitModels();
        KeyBind.init();
    }

    @SubscribeEvent
    public void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(GTLEntityTypes.NUKE_BOMB.get(), GTExplosiveRenderer::new);
    }
}
