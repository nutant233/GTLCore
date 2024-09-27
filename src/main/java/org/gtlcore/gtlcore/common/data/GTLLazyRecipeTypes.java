package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class GTLLazyRecipeTypes {

    public static void init() {}

    public static final GTRecipeType WOOD_DISTILLATION_RECIPES = register("wood_distillation_recipes", MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(3, 3, 1, 16)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.DRILL_TOOL);
}
