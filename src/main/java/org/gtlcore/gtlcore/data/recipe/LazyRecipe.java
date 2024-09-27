package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.DISTILLATION_TOWER;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static org.gtlcore.gtlcore.common.data.GTLLazyRecipeTypes.*;


public class LazyRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        WOOD_DISTILLATION_RECIPES.recipeBuilder("wood_distillation_recipes")
                .inputItems(ItemTags.LOGS, 160)
                .inputFluids(Nitrogen.getFluid(10000))
                .outputItems(dust, DarkAsh, 160)
                .outputFluids(Water.getFluid(8000))
                .outputFluids(Carbon.getFluid(4900))
                .outputFluids(Methanol.getFluid(4800))
                .outputFluids(Benzene.getFluid(3500))
                .outputFluids(CarbonMonoxide.getFluid(3400))
                .outputFluids(Creosote.getFluid(3000))
                .outputFluids(Dimethylbenzene.getFluid(2400))
                .outputFluids(AceticAcid.getFluid(1600))
                .outputFluids(Methane.getFluid(1300))
                .outputFluids(Acetone.getFluid(800))
                .outputFluids(Phenol.getFluid(750))
                .outputFluids(Toluene.getFluid(750))
                .outputFluids(Ethylene.getFluid(200))
                .outputFluids(Hydrogen.getFluid(200))
                .outputFluids(MethylAcetate.getFluid(160))
                .outputFluids(Ethanol.getFluid(160))
                .duration(1000).EUt(VA[MV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("wood_distillation")
                .inputItems(DISTILLATION_TOWER, 1)
                .inputItems(CustomTags.IV_CIRCUITS, 4)
                .inputItems(GTItems.ELECTRIC_PUMP_IV, 4)
                .outputItems(WOOD_DISTILLATION)
                .duration(400).EUt(VA[EV])
                .save(provider);
    }
}
