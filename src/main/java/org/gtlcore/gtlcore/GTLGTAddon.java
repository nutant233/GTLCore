package org.gtlcore.gtlcore;

import org.gtlcore.gtlcore.api.data.tag.GTLTagPrefix;
import org.gtlcore.gtlcore.api.registries.GTLRegistration;
import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.config.GTConfigModify;
import org.gtlcore.gtlcore.data.recipe.*;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import com.lowdragmc.lowdraglib.Platform;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@GTAddon
public class GTLGTAddon implements IGTAddon {

    @Override
    public String addonModId() {
        return GTLCore.MOD_ID;
    }

    @Override
    public GTRegistrate getRegistrate() {
        return GTLRegistration.REGISTRATE;
    }

    @Override
    public boolean requiresHighTier() {
        return true;
    }

    @Override
    public void initializeAddon() {}

    @Override
    public void registerSounds() {
        GTLSoundEntries.init();
    }

    @Override
    public void registerCovers() {
        GTLCovers.init();
    }

    @Override
    public void registerElements() {
        GTConfigModify.init();
        GTLElements.init();
    }

    @Override
    public void registerTagPrefixes() {
        GTLTagPrefix.init();
    }

    @Override
    public void removeRecipes(Consumer<ResourceLocation> consumer) {
        RemoveRecipe.init(consumer);
    }

    @Override
    public void registerFluidVeins() {
        if (!Platform.isDevEnv()) {
            GTLBedrockFluids.init();
        }
    }
}
