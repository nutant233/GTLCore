package org.gtlcore.gtlcore.data.recipe;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLItems;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.GTLMaterials.AbsoluteEthanol;
import static org.gtlcore.gtlcore.common.data.GTLMaterials.PiranhaSolution;

public class CircuitRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        AUTOCLAVE_RECIPES.recipeBuilder(GTLCore.id("sterilized_petri_dish"))
                .inputItems(GTItems.PETRI_DISH)
                .inputFluids(AbsoluteEthanol.getFluid(100))
                .outputItems(GTLItems.STERILIZED_PETRI_DISH)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .duration(25).EUt(7680).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("electricaly_wired_petri_dish"))
                .inputItems(GTLItems.STERILIZED_PETRI_DISH)
                .inputItems(TagPrefix.wireFine, GTMaterials.Titanium)
                .inputFluids(GTMaterials.Polyethylene.getFluid(1296))
                .outputItems(GTLItems.ELECTRICALY_WIRED_PETRI_DISH)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .duration(100).EUt(7680).save(provider);

        CHEMICAL_BATH_RECIPES.recipeBuilder(GTLCore.id("petri_dish"))
                .inputItems(GTLItems.CONTAMINATED_PETRI_DISH)
                .outputItems(GTItems.PETRI_DISH)
                .inputFluids(PiranhaSolution.getFluid(100))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(25).EUt(30).save(provider);
    }
}
