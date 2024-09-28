package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.EMITTER_LuV;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static org.gtlcore.gtlcore.common.data.GTLLazyRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.machines.GCyMMachines.LARGE_DISTILLERY;
import static org.gtlcore.gtlcore.common.data.machines.LazyMachine.WOOD_DISTILLATION;

public class LazyRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        WOOD_DISTILLATION_RECIPES.recipeBuilder("wood_distillation_recipes")
                .inputItems(ItemTags.LOGS, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(dust, DarkAsh, 8)
                .outputFluids(Water.getFluid(800))
                .outputFluids(Carbon.getFluid(490))
                .outputFluids(Methanol.getFluid(480))
                .outputFluids(Benzene.getFluid(350))
                .outputFluids(CarbonMonoxide.getFluid(340))
                .outputFluids(Creosote.getFluid(300))
                .outputFluids(Dimethylbenzene.getFluid(240))
                .outputFluids(AceticAcid.getFluid(160))
                .outputFluids(Methane.getFluid(130))
                .outputFluids(Acetone.getFluid(80))
                .outputFluids(Phenol.getFluid(75))
                .outputFluids(Toluene.getFluid(75))
                .outputFluids(Ethylene.getFluid(20))
                .outputFluids(Hydrogen.getFluid(20))
                .outputFluids(MethylAcetate.getFluid(16))
                .outputFluids(Ethanol.getFluid(16))
                .duration(200).EUt(VA[IV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("wood_distillation")
                .inputItems(DISTILLATION_TOWER, 2)
                .inputItems(LARGE_DISTILLERY, 4)
                .inputItems(CustomTags.LuV_CIRCUITS, 4)
                .inputItems(EMITTER_LuV, 4)
                .inputItems(pipeHugeFluid, StainlessSteel, 8)
                .inputItems(GTItems.ELECTRIC_PUMP_IV, 8)
                .inputItems(WatertightSteel, 16)
                .inputItems(plateDouble, StainlessSteel, 32)
                .inputFluids(SolderingAlloy.getFluid(1296))
                .outputItems(WOOD_DISTILLATION)
                .duration(400).EUt(VA[IV])
                .save(provider);
    }
}
