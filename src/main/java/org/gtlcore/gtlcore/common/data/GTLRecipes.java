package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.data.recipe.*;
import org.gtlcore.gtlcore.data.recipe.chemistry.MixerRecipes;
import org.gtlcore.gtlcore.data.recipe.processing.Lanthanidetreatment;
import org.gtlcore.gtlcore.data.recipe.processing.StoneDustProcess;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.HashSet;
import java.util.function.Consumer;

public class GTLRecipes {

    public static final HashSet<String> DISASSEMBLY_RECORD = new HashSet<>();

    public static void recipeAddition(Consumer<FinishedRecipe> provider) {
        GCyMRecipes.init(provider);
        FuelRecipes.init(provider);
        MachineRecipe.init(provider);
        Misc.init(provider);
        ElementCopying.init(provider);
        StoneDustProcess.init(provider);
        Lanthanidetreatment.init(provider);
        CircuitRecipes.init(provider);
        MixerRecipes.init(provider);
    }
}
