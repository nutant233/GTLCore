package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.common.machine.multiblock.electric.StorageMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.steam.LargeSteamParallelMultiblockMachine;

import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GTLRecipeModifiers {

    public static final RecipeModifier GCYM_REDUCTION = (machine, recipe, params, result) -> GTLRecipeModifiers
            .reduction(machine, recipe, 0.8, 0.6);

    public static final RecipeModifier LARGE_STEAM_OC = (machine, recipe, params, result) -> LargeSteamParallelMultiblockMachine.recipeModifier(machine, recipe, 0.5);

    public static final RecipeModifier STEAM_OC = (machine, recipe, params, result) -> LargeSteamParallelMultiblockMachine.recipeModifier(machine, recipe, 1);

    public static final RecipeModifier COIL_PARALLEL = (machine, recipe, params, result) -> GTRecipeModifiers.accurateParallel(machine, recipe, Math.min(2147483647, (int) Math.pow(2, ((double) ((CoilWorkableElectricMultiblockMachine) machine).getCoilType().getCoilTemperature() / 900))), false).getFirst();

    public static GTRecipe chemicalPlantOverclock(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                                  @NotNull OCResult result) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) > coilMachine.getTier()) {
                return null;
            }
            var re = RecipeHelper.applyOverclock(
                    new OverclockingLogic((p, r, maxVoltage) -> {
                        OverclockingLogic.NON_PERFECT_OVERCLOCK.getLogic()
                                .runOverclockingLogic(params, result, maxVoltage);
                    }), recipe, coilMachine.getOverclockVoltage(), params, result);

            if (coilMachine.getCoilTier() > 0) {
                result.setEut(Math.max(1, (long) (result.getEut() * (1.0 - coilMachine.getCoilTier() * 0.05))));
                result.setDuration((int) Math.max(1, (result.getDuration() * (1.0 - coilMachine.getCoilTier() * 0.05))));
            }
            return re;
        }
        return null;
    }

    public static GTRecipe processingPlantOverclock(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                                    @NotNull OCResult result) {
        if (machine instanceof WorkableElectricMultiblockMachine workableElectricMultiblockMachine) {
            GTRecipe recipe1 = reduction(machine, recipe, 0.9, 0.6);
            if (recipe1 != null) {
                return RecipeHelper.applyOverclock(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK,
                        GTRecipeModifiers.accurateParallel(machine, recipe1, 4 * (workableElectricMultiblockMachine).getTier() - 1, false).getFirst(),
                        workableElectricMultiblockMachine.getOverclockVoltage(), params, result);
            }
        }
        return null;
    }

    public static GTRecipe nanoForgeOverclock(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                              @NotNull OCResult result, int tier) {
        if (machine instanceof StorageMachine storageMachine) {
            if (tier == 1) {
                if (recipe.data.getInt("nano_forge_tier") > 1 && !Objects.equals(storageMachine.getMachineStorageItem().kjs$getId(), "gtceu:carbon_nanoswarm")) {
                    return null;
                }
            } else if (tier == 2) {
                if (recipe.data.getInt("nano_forge_tier") > 2 && !Objects.equals(storageMachine.getMachineStorageItem().kjs$getId(), "gtceu:neutronium_nanoswarm")) {
                    return null;
                }
            } else if (tier == 3) {
                if (!Objects.equals(storageMachine.getMachineStorageItem().kjs$getId(), "gtceu:draconium_nanoswarm")) {
                    return null;
                }
            }
            GTRecipe recipe1 = GTRecipeModifiers.accurateParallel(machine, recipe, (int) (storageMachine.getMachineStorageItem().getCount() * Math.pow(2, tier - recipe.data.getInt("nano_forge_tier"))), false).getFirst();
            return RecipeHelper.applyOverclock(new OverclockingLogic(1 / Math.pow(2, 1 + tier - recipe.data.getInt("nano_forge_tier")), 4, false), recipe1, storageMachine.getOverclockVoltage(), params, result);
        }
        return null;
    }

    public static GTRecipe reduction(MetaMachine machine, @NotNull GTRecipe recipe,
                                     double reductionEUt, double reductionDuration) {
        if (machine instanceof IOverclockMachine overclockMachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) > overclockMachine.getMaxOverclockTier()) {
                return null;
            }
            GTRecipe recipe1 = recipe.copy();
            if (reductionEUt != 1) {
                recipe1.tickInputs.put(EURecipeCapability.CAP,
                        List.of(new Content((long) Math.max(1, RecipeHelper.getInputEUt(recipe) * reductionEUt),
                                ChanceLogic.getMaxChancedValue(), ChanceLogic.getMaxChancedValue(),
                                0, null, null)));
            }
            if (reductionDuration != 1) {
                recipe1.duration = (int) Math.max(1, recipe.duration * reductionDuration);
            }
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

    public static GTRecipe standardOverclocking(WorkableElectricMultiblockMachine machine, @NotNull GTRecipe recipe) {
        double resultDuration = recipe.duration;
        double resultVoltage = RecipeHelper.getOutputEUt(recipe);
        long maxVoltage = machine.getOverclockVoltage();

        for (int numberOfOCs = 16; numberOfOCs > 0; numberOfOCs--) {
            double potentialVoltage = resultVoltage * 4;

            if (potentialVoltage > maxVoltage) break;

            double potentialDuration = resultDuration / 4;

            if (potentialDuration < 1) break;

            resultDuration = potentialDuration;

            resultVoltage = potentialVoltage;
        }
        GTRecipe recipe1 = recipe.copy();
        recipe1.duration = (int) resultDuration;
        recipe1.tickOutputs.put(EURecipeCapability.CAP,
                List.of(new Content((long) resultVoltage, ChanceLogic.getMaxChancedValue(), ChanceLogic.getMaxChancedValue(), 0, null, null)));
        return recipe1;
    }
}
