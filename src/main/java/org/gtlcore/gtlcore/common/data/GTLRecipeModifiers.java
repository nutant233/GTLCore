package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.data.medicalcondition.MedicalCondition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.capability.EnvironmentalHazardSavedData;
import com.gregtechceu.gtceu.common.data.GTMedicalConditions;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GTLRecipeModifiers {

    public static final RecipeModifier GCYM_REDUCTION = (machine, recipe) -> GTLRecipeModifiers
            .reduction(machine, recipe, 0.8, 0.6);

    public static GTRecipe chemicalPlantOverclock(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) / recipe.parallels > coilMachine.getTier()) {
                return null;
            }
            double reduction = 1 - coilMachine.getCoilTier() * 0.05;
            GTRecipe recipe1 = reduction(machine, recipe, reduction, reduction);
            return RecipeHelper.applyOverclock(OverclockingLogic.PERFECT_OVERCLOCK,
                    recipe1, coilMachine.getOverclockVoltage());
        }
        return null;
    }

    public static GTRecipe reduction(MetaMachine machine, @NotNull GTRecipe recipe,
                                     double reductionEUt, double reductionDuration) {
        if (machine instanceof IOverclockMachine overclockMachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) / recipe.parallels > overclockMachine.getMaxOverclockTier()) {
                return null;
            }
            GTRecipe recipe1 = recipe.copy();
            recipe1.duration = (int) Math.max(1, recipe.duration * reductionDuration);
            recipe1.tickInputs.put(EURecipeCapability.CAP,
                    List.of(new Content((long) Math.max(1, RecipeHelper.getInputEUt(recipe) * reductionEUt),
                            ChanceLogic.getMaxChancedValue(), ChanceLogic.getMaxChancedValue(),
                            0, null, null)));
            return recipe1;
        }
        return recipe;
    }

    public static int getHatchParallel(MetaMachine machine) {
        if (machine instanceof IMultiController controller && controller.isFormed()) {
            Optional<IParallelHatch> optional = controller.getParts().stream().filter(IParallelHatch.class::isInstance)
                    .map(IParallelHatch.class::cast).findAny();
            if (optional.isPresent()) {
                IParallelHatch hatch = optional.get();
                return hatch.getCurrentParallel();
            }
        }
        return 1;
    }
}
