package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.AUTOCLAVE_RECIPES;
import static org.gtlcore.gtlcore.common.data.GTLMaterials.WaterAgarMix;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DEHYDRATOR_RECIPES;

public class Misc {

    public static void init(Consumer<FinishedRecipe> provider) {
        AUTOCLAVE_RECIPES.recipeBuilder("water_agar_mix").EUt(VA[HV]).duration(600)
                .inputItems(dust, Gelatin)
                .inputFluids(DistilledWater.getFluid(1000))
                .outputFluids(WaterAgarMix.getFluid(1000))
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .save(provider);

        DEHYDRATOR_RECIPES.recipeBuilder("agar")
                .inputFluids(WaterAgarMix.getFluid(1000))
                .outputItems(dust, Agar, 1)
                .duration(420).EUt(VA[MV])
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .save(provider);
    }
}
