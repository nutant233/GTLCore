package org.gtlcore.gtlcore.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import org.gtlcore.gtlcore.common.data.GTLMaterials;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.SEMI_FLUID_GENERATOR_FUELS;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.SUPERCRITICAL_STEAM_TURBINE_FUELS;

public class FuelRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        SUPERCRITICAL_STEAM_TURBINE_FUELS.recipeBuilder("supercritical_steam")
                .inputFluids(GTLMaterials.SupercriticalSteam.getFluid(1280))
                .outputFluids(DistilledWater.getFluid(8))
                .duration(30)
                .EUt(-V[MV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("seed_oil")
                .inputFluids(SeedOil.getFluid(32))
                .duration(8)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("fish_oil")
                .inputFluids(FishOil.getFluid(32))
                .duration(8)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("biomass")
                .inputFluids(Biomass.getFluid(8))
                .duration(8)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("oil")
                .inputFluids(Oil.getFluid(4))
                .duration(10)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("oil_light")
                .inputFluids(OilLight.getFluid(4))
                .duration(10)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("creosote")
                .inputFluids(Creosote.getFluid(4))
                .duration(12)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("oil_heavy")
                .inputFluids(OilHeavy.getFluid(4))
                .duration(15)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("oil_medium")
                .inputFluids(RawOil.getFluid(4))
                .duration(15)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("coal_tar")
                .inputFluids(CoalTar.getFluid(8))
                .duration(8)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("sulfuric_heavy_fuel")
                .inputFluids(SulfuricHeavyFuel.getFluid(2))
                .duration(10)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("glycerol")
                .inputFluids(Glycerol.getFluid(2))
                .duration(41)
                .EUt(-V[LV])
                .save(provider);

        SEMI_FLUID_GENERATOR_FUELS.recipeBuilder("heavy_fuel")
                .inputFluids(HeavyFuel.getFluid(2))
                .duration(45)
                .EUt(-V[LV])
                .save(provider);
    }
}
