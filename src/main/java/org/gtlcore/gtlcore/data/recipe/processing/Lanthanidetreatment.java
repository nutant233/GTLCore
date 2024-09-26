package org.gtlcore.gtlcore.data.recipe.processing;

import org.gtlcore.gtlcore.common.data.GTLItems;

import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.GTLMaterials.*;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DISSOLUTION_TREATMENT;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DIGESTION_TREATMENT;



public class Lanthanidetreatment {

    public static void init(Consumer<FinishedRecipe> provider) {
        DIGESTION_TREATMENT.recipeBuilder("monazite_rare_earth_turbid_liquid_output")
                .inputItems(dust, Monazite, 2)
                .inputFluids(NitricAcid.getFluid(700))
                .outputItems(dust, SiliconDioxide, 1)
                .outputFluids(MonaziteRareEarthTurbidLiquid.getFluid(400))
                .duration(20 * 20)
                .EUt(1920)
                .save(provider);

        DISSOLUTION_TREATMENT.recipeBuilder("diluted_monazite_slurry_output")
                .inputItems(dust, Saltpeter, 9)
                .inputFluids(MonaziteRareEarthTurbidLiquid.getFluid(9000))
                .inputFluids(Water.getFluid(90000))
                .outputItems(dust, HafniumOxideZirconiaMixedPowder, 4)
                .outputItems(dust, ThoritePowder, 9)
                .outputItems(dust, Monazite, 2)
                .outputFluids(DilutedMonaziteSlurry.getFluid(100000))
                .duration(20 * 405)
                .EUt(405)
                .save(provider);


    }
}
