package org.gtlcore.gtlcore.common.machine.trait;

import org.gtlcore.gtlcore.common.machine.multiblock.electric.WorkableElectricMultipleRecipesMachine;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MultipleRecipesLogic extends RecipeLogic {

    public MultipleRecipesLogic(WorkableElectricMultipleRecipesMachine machine) {
        super(machine);
    }

    @Override
    public WorkableElectricMultipleRecipesMachine getMachine() {
        return (WorkableElectricMultipleRecipesMachine) super.getMachine();
    }

    @Override
    public void findAndHandleRecipe() {
        lastRecipe = null;
        var match = getRecipe();
        if (match != null) {
            if (match.matchRecipe(this.machine).isSuccess()) {
                setupRecipe(match);
            }
        }
    }

    @Nullable
    private GTRecipe getRecipe() {
        if (!machine.hasProxies()) return null;
        GTRecipe match = LookupRecipe();
        if (match == null) return null;
        GTRecipe recipe = buildEmptyRecipe();
        recipe.outputs.put(ItemRecipeCapability.CAP, new ArrayList<>());
        recipe.outputs.put(FluidRecipeCapability.CAP, new ArrayList<>());
        long totalEu = 0;
        int parallel = getMachine().getMaxParallel();
        for (int i = 0; i < 64; i++) {
            match = parallelRecipe(match, parallel);
            GTRecipe input = buildEmptyRecipe();
            input.inputs.putAll(match.inputs);
            if (input.matchRecipe(machine).isSuccess() && input.handleRecipeIO(IO.IN, machine, getChanceCaches())) {
                totalEu += match.duration * RecipeHelper.getInputEUt(match);
                List<Content> item = match.outputs.get(ItemRecipeCapability.CAP);
                if (item != null) {
                    recipe.outputs.get(ItemRecipeCapability.CAP).addAll(item);
                }
                List<Content> fluid = match.outputs.get(FluidRecipeCapability.CAP);
                if (fluid != null) {
                    recipe.outputs.get(FluidRecipeCapability.CAP).addAll(fluid);
                }
            }
            match = LookupRecipe();
            if (match == null) break;
        }
        if (recipe.outputs.get(ItemRecipeCapability.CAP).equals(new ArrayList<>()) && recipe.outputs.get(FluidRecipeCapability.CAP).equals(new ArrayList<>())) return null;
        long maxEUt = getMachine().getOverclockVoltage();
        double d = (double) totalEu / maxEUt;
        long eut = d > 20 ? maxEUt : (long) (maxEUt * d / 20);
        recipe.tickInputs.put(EURecipeCapability.CAP, List.of(new Content(eut, ChanceLogic.getMaxChancedValue(), ChanceLogic.getMaxChancedValue(), 0, null, null)));
        recipe.duration = (int) Math.max(d, 20);
        return recipe;
    }

    private GTRecipe LookupRecipe() {
        return machine.getRecipeType().getLookup().findRecipe(machine);
    }

    private GTRecipe buildEmptyRecipe() {
        return GTRecipeBuilder.ofRaw().buildRawRecipe();
    }

    private GTRecipe parallelRecipe(GTRecipe recipe, int max) {
        int maxMultipliers = Integer.MAX_VALUE;
        for (RecipeCapability<?> cap : recipe.inputs.keySet()) {
            if (cap.doMatchInRecipe()) {
                int currentMultiplier = cap.getMaxParallelRatio(machine, recipe, max);
                if (currentMultiplier < maxMultipliers) {
                    maxMultipliers = currentMultiplier;
                }
            }
        }
        if (maxMultipliers > 0) {
            recipe = recipe.copy(ContentModifier.multiplier(maxMultipliers), false);
        }
        return recipe;
    }

    @Override
    public void onRecipeFinish() {
        machine.afterWorking();
        if (lastRecipe != null) {
            lastRecipe.postWorking(this.machine);
            lastRecipe.handleRecipeIO(IO.OUT, this.machine, this.chanceCaches);
        }
        var match = getRecipe();
        if (match != null) {
            if (match.matchRecipe(this.machine).isSuccess()) {
                setupRecipe(match);
                return;
            }
        }
        setStatus(Status.IDLE);
        progress = 0;
        duration = 0;
    }
}
