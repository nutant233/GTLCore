package org.gtlcore.gtlcore.common.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import org.gtlcore.gtlcore.common.data.GTLMaterials;
import org.gtlcore.gtlcore.data.recipe.GenerateDisassembly;
import org.gtlcore.gtlcore.utils.Registries;

import static com.gregtechceu.gtceu.api.GTValues.EV;
import static com.gregtechceu.gtceu.api.GTValues.V;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.get;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.HEAT_EXCHANGER_RECIPES;

public class RecipeModify {

    public static void init() {
        GTRecipeTypes.ASSEMBLER_RECIPES.onRecipeBuild(GenerateDisassembly::generateDisassembly);
        GTRecipeTypes.ASSEMBLER_RECIPES.onRecipeBuild(GenerateDisassembly::generateDisassembly);
        GTRecipeTypes.PLASMA_GENERATOR_FUELS.onRecipeBuild((recipeBuilder, provider) -> {
            long eu = recipeBuilder.duration * V[EV];
            HEAT_EXCHANGER_RECIPES.recipeBuilder(recipeBuilder.id)
                    .inputFluids(FluidRecipeCapability.CAP.of(recipeBuilder.input
                            .get(FluidRecipeCapability.CAP).get(0).getContent()))
                    .inputFluids(GTMaterials.DistilledWater.getFluid(eu / 160))
                    .outputFluids(FluidRecipeCapability.CAP.of(recipeBuilder.output
                            .get(FluidRecipeCapability.CAP).get(0).getContent()))
                    .outputFluids(GTLMaterials.SupercriticalSteam.getFluid(eu))
                    .addData("eu", eu)
                    .duration(200)
                    .save(provider);
        });

        GTRecipeTypes.LASER_ENGRAVER_RECIPES.onRecipeBuild((recipeBuilder, provider) -> {
            GTRecipeBuilder recipe = get("dimensional_focus_engraving_array").copyFrom(recipeBuilder)
                    .duration((int) (recipeBuilder.duration * 0.2))
                    .EUt(recipeBuilder.EUt() * 4);
            double value = Math.log10(recipeBuilder.EUt()) / Math.log10(4);
            if (value > 10) {
                recipe.inputFluids(Registries.getFluidStack("gtceu:euv_photoresist", (long) (value / 2)));
            } else {
                recipe.inputFluids(Registries.getFluidStack("gtceu:photoresist", (long) value));
            }
            recipe.save(provider);
        });
    }
}
