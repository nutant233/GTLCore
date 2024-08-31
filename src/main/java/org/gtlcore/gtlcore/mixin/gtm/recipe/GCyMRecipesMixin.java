package org.gtlcore.gtlcore.mixin.gtm.recipe;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.misc.GCyMRecipes;
import net.minecraft.data.recipes.FinishedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GCyMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTItems.ELECTRIC_MOTOR_IV;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

@Mixin(GCyMRecipes.class)
public class GCyMRecipesMixin {

    private GCyMRecipesMixin() {}

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    private static void registerMultiblockControllerRecipes(Consumer<FinishedRecipe> provider) {}

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    private static void registerPartsRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, "crushing_wheels", CRUSHING_WHEELS.asStack(2), "TTT", "UCU",
                "UMU", 'T', new UnificationEntry(gearSmall, TungstenCarbide), 'U', ChemicalHelper.get(gear, Ultimet),
                'C', CASING_SECURE_MACERATION.asStack(), 'M', ELECTRIC_MOTOR_IV.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "slicing_blades", SLICING_BLADES.asStack(2), "PPP", "UCU", "UMU",
                'P', new UnificationEntry(plate, TungstenCarbide), 'U', ChemicalHelper.get(gear, Ultimet), 'C',
                CASING_SHOCK_PROOF.asStack(), 'M', ELECTRIC_MOTOR_IV.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "electrolytic_cell", ELECTROLYTIC_CELL.asStack(2), "WWW", "WCW",
                "ZKZ", 'W', new UnificationEntry(wireGtDouble, Platinum), 'Z', CustomTags.IV_CIRCUITS, 'C',
                CASING_NONCONDUCTING.asStack(), 'K', ChemicalHelper.get(cableGtSingle, Tungsten));
        VanillaRecipeHelper.addShapedRecipe(provider, "heat_vent", HEAT_VENT.asStack(2), "PDP", "RLR", "PDP", 'P',
                new UnificationEntry(plate, TantalumCarbide), 'D',
                ChemicalHelper.get(plateDouble, MolybdenumDisilicide), 'R', ChemicalHelper.get(rotor, Titanium), 'L',
                ChemicalHelper.get(rodLong, MolybdenumDisilicide));
    }
}
