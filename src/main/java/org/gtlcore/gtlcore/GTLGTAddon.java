package org.gtlcore.gtlcore;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.lowdragmc.lowdraglib.Platform;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtlcore.gtlcore.api.registries.GTLRegistration;
import org.gtlcore.gtlcore.common.data.GTLBedrockFluids;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.data.recipe.*;

import java.util.function.Consumer;

import static org.gtlcore.gtlcore.common.data.GTLRecipes.DISASSEMBLY_RECORD;

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
    public void initializeAddon() {
        GTLItems.init();
        GTLBlocks.init();
    }

    @Override
    public void registerTagPrefixes() {
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        GCyMRecipes.init(provider);
        FuelRecipes.init(provider);
        MachineRecipe.init(provider);
        Misc.init(provider);
        ElementCopying.init(provider);
    }

    @Override
    public void removeRecipes(Consumer<ResourceLocation> consumer) {
        DISASSEMBLY_RECORD.clear();
        consumer.accept(new ResourceLocation("gtceu:assembly_line/dynamo_hatch_uhv"));
        consumer.accept(new ResourceLocation("gtceu:assembly_line/energy_hatch_uhv"));
        consumer.accept(new ResourceLocation("gtceu:research_station/1_x_gtceu_uv_energy_input_hatch"));
        consumer.accept(new ResourceLocation("gtceu:research_station/1_x_gtceu_uv_energy_output_hatch"));
    }

    @Override
    public void registerFluidVeins() {
        if (!Platform.isDevEnv()) {
            GTLBedrockFluids.init();
        }
    }
}
