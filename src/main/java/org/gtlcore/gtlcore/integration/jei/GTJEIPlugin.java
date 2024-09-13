package org.gtlcore.gtlcore.integration.jei;

import com.lowdragmc.lowdraglib.LDLib;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class GTJEIPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return GTLCore.id("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        if (LDLib.isReiLoaded() || LDLib.isEmiLoaded()) return;
        registration.addRecipeCatalyst(GTLMachines.ADVANCED_MULTI_SMELTER.asStack(), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(GTLMachines.DIMENSIONALLY_TRANSCENDENT_STEAM_OVEN.asStack(), RecipeTypes.SMELTING);
    }
}
